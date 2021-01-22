package charbosses.ui;

import charbosses.bosses.AbstractCharBoss;
import charbosses.vfx.EnemyRefreshEnergyEffect;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.TutorialStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.panels.AbstractPanel;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.RefreshEnergyEffect;
import downfall.downfallMod;

public class EnemyEnergyPanel extends AbstractPanel {
    public static final String[] MSG;
    public static final String[] LABEL;
    public static final float FONT_POP_SCALE = 2.0f;
    public static final float ENERGY_VFX_TIME = 2.0f;
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(downfallMod.makeID("EnergyPanelTip"));
    private static final int RAW_W = 256;
    private static final Color ENERGY_TEXT_COLOR;
    private static final float VFX_ROTATE_SPEED = -30.0f;
    public static float fontScale;
    public static int totalCount;
    public static float energyVfxTimer;
    private BitmapFont energyNumFont;

    static {
        MSG = EnemyEnergyPanel.uiStrings.TEXT;
        LABEL = EnemyEnergyPanel.uiStrings.EXTRA_TEXT;
        ENERGY_TEXT_COLOR = new Color(1.0f, 1.0f, 0.86f, 1.0f);
        EnemyEnergyPanel.fontScale = 1.0f;
        EnemyEnergyPanel.totalCount = 0;
        EnemyEnergyPanel.energyVfxTimer = 0.0f;
    }

    private Hitbox tipHitbox;
    private Texture gainEnergyImg;
    private float energyVfxAngle;
    private float energyVfxScale;
    private Color energyVfxColor;
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
                this.energyNumFont = FontHelper.energyNumFontBlue;
                break;
            case IRONCLAD:
                this.gainEnergyImg = ImageMaster.RED_ORB_FLASH_VFX;
                this.energyNumFont = FontHelper.energyNumFontRed;
                break;
            case THE_SILENT:
                this.gainEnergyImg = ImageMaster.GREEN_ORB_FLASH_VFX;
                this.energyNumFont = FontHelper.energyNumFontGreen;
                break;
            case WATCHER:
                this.gainEnergyImg = ImageMaster.PURPLE_ORB_FLASH_VFX;
                this.energyNumFont = FontHelper.energyNumFontPurple;
                break;
            default:
                this.gainEnergyImg = ImageMaster.RED_ORB_FLASH_VFX;
                this.energyNumFont = FontHelper.energyNumFontRed;
                break;
        }

        /*
        if (this.owner.chosenClass == ((AbstractPlayer.PlayerClass) CharBossCrowbot.Enums.Crowbot)) {
            this.gainEnergyImg = ImageMaster.BLUE_ORB_FLASH_VFX;
            this.energyNumFont = FontHelper.energyNumFontBlue;
        }*/


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
        AbstractDungeon.effectsQueue.add(new EnemyRefreshEnergyEffect());
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

    public int getCurrentEnergy() {
        if (AbstractDungeon.player == null) {
            return 0;
        }
        return EnemyEnergyPanel.totalCount;
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
            } else if (InputHelper.scrolledUp && EnemyEnergyPanel.totalCount > 0) {
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
        final String energyMsg = EnemyEnergyPanel.totalCount + "/" + this.owner.energy.energy;
        AbstractDungeon.player.getEnergyNumFont().getData().setScale(EnemyEnergyPanel.fontScale);
        FontHelper.renderFontCentered(sb, energyNumFont, energyMsg, this.current_x, this.current_y, EnemyEnergyPanel.ENERGY_TEXT_COLOR);
        this.tipHitbox.render(sb);
        if (this.tipHitbox.hovered && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.isScreenUp) {
            TipHelper.renderGenericTip(1550.0F * Settings.scale, 750.0F * Settings.scale, EnemyEnergyPanel.LABEL[0], EnemyEnergyPanel.MSG[0]);
        }
    }

    private void renderOrb(final SpriteBatch sb) {
        if (EnemyEnergyPanel.totalCount == 0) {
            this.owner.energyOrb.renderOrb(sb, false, this.current_x, this.current_y);
        } else {
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
}
