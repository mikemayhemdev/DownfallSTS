package charbosses.ui;

import com.megacrit.cardcrawl.localization.*;
import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.vfx.*;

import charbosses.bosses.AbstractCharBoss;

import com.megacrit.cardcrawl.unlock.*;
import com.megacrit.cardcrawl.helpers.input.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.ui.panels.AbstractPanel;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.core.*;

public class EnemyEnergyPanel extends AbstractPanel
{
    private static final TutorialStrings tutorialStrings;
    public static final String[] MSG;
    public static final String[] LABEL;
    private static final int RAW_W = 256;
    private static final Color ENERGY_TEXT_COLOR;
    public static float fontScale;
    public static final float FONT_POP_SCALE = 2.0f;
    public static int totalCount;
    private Hitbox tipHitbox;
    private Texture gainEnergyImg;
    private float energyVfxAngle;
    private float energyVfxScale;
    private Color energyVfxColor;
    public static float energyVfxTimer;
    public static final float ENERGY_VFX_TIME = 2.0f;
    private static final float VFX_ROTATE_SPEED = -30.0f;
    private AbstractCharBoss owner;
    
    public EnemyEnergyPanel(AbstractCharBoss owner) {
        super(Settings.WIDTH - 198.0f * Settings.scale, Settings.HEIGHT - 190.0f * Settings.scale, -480.0f * Settings.scale, 200.0f * Settings.scale, 12.0f * Settings.scale, -12.0f * Settings.scale, null, false);
        this.tipHitbox = new Hitbox(0.0f, 0.0f, 120.0f * Settings.scale, 120.0f * Settings.scale);
        this.energyVfxAngle = 0.0f;
        this.energyVfxScale = Settings.scale;
        this.energyVfxColor = Color.WHITE.cpy();
        this.owner = owner;
        switch (this.owner.chosenClass) {
		case DEFECT:
			this.gainEnergyImg = ImageMaster.BLUE_ORB_FLASH_VFX;
			break;
		case IRONCLAD:
			this.gainEnergyImg = ImageMaster.RED_ORB_FLASH_VFX;
			break;
		case THE_SILENT:
			this.gainEnergyImg = ImageMaster.GREEN_ORB_FLASH_VFX;
			break;
		case WATCHER:
			this.gainEnergyImg = ImageMaster.PURPLE_ORB_FLASH_VFX;
			break;
		default:
			this.gainEnergyImg = ImageMaster.RED_ORB_FLASH_VFX;
			break;
        }
    }
    
    public static void setEnergy(final int energy) {
        EnemyEnergyPanel.totalCount = energy;
        AbstractDungeon.effectsQueue.add(new RefreshEnergyEffect());
        EnemyEnergyPanel.energyVfxTimer = 2.0f;
        EnemyEnergyPanel.fontScale = 2.0f;
    }
    
    public static void addEnergy(final int e) {
        EnemyEnergyPanel.totalCount += e;
        if (EnemyEnergyPanel.totalCount >= 9) {
            UnlockTracker.unlockAchievement("ADRENALINE");
        }
        if (EnemyEnergyPanel.totalCount > 999) {
            EnemyEnergyPanel.totalCount = 999;
        }
        AbstractDungeon.effectsQueue.add(new RefreshEnergyEffect());
        EnemyEnergyPanel.fontScale = 2.0f;
        EnemyEnergyPanel.energyVfxTimer = 2.0f;
    }
    
    public static void useEnergy(final int e) {
        EnemyEnergyPanel.totalCount -= e;
        if (EnemyEnergyPanel.totalCount < 0) {
            EnemyEnergyPanel.totalCount = 0;
        }
        if (e != 0) {
            EnemyEnergyPanel.fontScale = 2.0f;
        }
    }
    
    public void update() {
        this.owner.energyOrb.updateOrb(EnemyEnergyPanel.totalCount);
        this.updateVfx();
        if (EnemyEnergyPanel.fontScale != 1.0f) {
            EnemyEnergyPanel.fontScale = MathHelper.scaleLerpSnap(EnemyEnergyPanel.fontScale, 1.0f);
        }
        this.tipHitbox.update();
        if (this.tipHitbox.hovered && !AbstractDungeon.isScreenUp) {
            AbstractDungeon.overlayMenu.hoveredTip = true;
        }
        if (Settings.isDebug) {
            if (InputHelper.scrolledDown) {
                addEnergy(1);
            }
            else if (InputHelper.scrolledUp && EnemyEnergyPanel.totalCount > 0) {
                useEnergy(1);
            }
        }
    }
    
    private void updateVfx() {
        if (EnemyEnergyPanel.energyVfxTimer != 0.0f) {
            this.energyVfxColor.a = Interpolation.exp10In.apply(0.5f, 0.0f, 1.0f - EnemyEnergyPanel.energyVfxTimer / 2.0f);
            this.energyVfxAngle += Gdx.graphics.getDeltaTime() * -30.0f;
            this.energyVfxScale = Settings.scale * Interpolation.exp10In.apply(1.0f, 0.1f, 1.0f - EnemyEnergyPanel.energyVfxTimer / 2.0f);
            EnemyEnergyPanel.energyVfxTimer -= Gdx.graphics.getDeltaTime();
            if (EnemyEnergyPanel.energyVfxTimer < 0.0f) {
                EnemyEnergyPanel.energyVfxTimer = 0.0f;
                this.energyVfxColor.a = 0.0f;
            }
        }
    }
    
    @Override
    public void render(final SpriteBatch sb) {
        this.tipHitbox.move(this.current_x, this.current_y);
        this.renderOrb(sb);
        this.renderVfx(sb);
        final String energyMsg = EnemyEnergyPanel.totalCount + "/" + AbstractDungeon.player.energy.energy;
        AbstractDungeon.player.getEnergyNumFont().getData().setScale(EnemyEnergyPanel.fontScale);
        FontHelper.renderFontCentered(sb, AbstractDungeon.player.getEnergyNumFont(), energyMsg, this.current_x, this.current_y, EnemyEnergyPanel.ENERGY_TEXT_COLOR);
        this.tipHitbox.render(sb);
        if (this.tipHitbox.hovered && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.isScreenUp) {
            TipHelper.renderGenericTip(50.0f * Settings.scale, 380.0f * Settings.scale, EnemyEnergyPanel.LABEL[0], EnemyEnergyPanel.MSG[0]);
        }
    }
    
    private void renderOrb(final SpriteBatch sb) {
        if (EnemyEnergyPanel.totalCount == 0) {
            this.owner.energyOrb.renderOrb(sb, false, this.current_x, this.current_y);
        }
        else {
        	this.owner.energyOrb.renderOrb(sb, true, this.current_x, this.current_y);
        }
    }
    
    private void renderVfx(final SpriteBatch sb) {
        if (EnemyEnergyPanel.energyVfxTimer != 0.0f) {
            sb.setBlendFunction(770, 1);
            sb.setColor(this.energyVfxColor);
            sb.draw(this.gainEnergyImg, this.current_x - 128.0f, this.current_y - 128.0f, 128.0f, 128.0f, 256.0f, 256.0f, this.energyVfxScale, this.energyVfxScale, -this.energyVfxAngle + 50.0f, 0, 0, 256, 256, true, false);
            sb.draw(this.gainEnergyImg, this.current_x - 128.0f, this.current_y - 128.0f, 128.0f, 128.0f, 256.0f, 256.0f, this.energyVfxScale, this.energyVfxScale, this.energyVfxAngle, 0, 0, 256, 256, false, false);
            sb.setBlendFunction(770, 771);
        }
    }
    
    public static int getCurrentEnergy() {
        if (AbstractDungeon.player == null) {
            return 0;
        }
        return EnemyEnergyPanel.totalCount;
    }
    
    static {
        tutorialStrings = CardCrawlGame.languagePack.getTutorialString("Energy Panel Tip");
        MSG = EnemyEnergyPanel.tutorialStrings.TEXT;
        LABEL = EnemyEnergyPanel.tutorialStrings.LABEL;
        ENERGY_TEXT_COLOR = new Color(1.0f, 1.0f, 0.86f, 1.0f);
        EnemyEnergyPanel.fontScale = 1.0f;
        EnemyEnergyPanel.totalCount = 0;
        EnemyEnergyPanel.energyVfxTimer = 0.0f;
    }
}
