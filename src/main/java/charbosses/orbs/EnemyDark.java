package charbosses.orbs;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.vfx.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.powers.*;
import com.badlogic.gdx.*;
import com.megacrit.cardcrawl.vfx.combat.*;

import charbosses.bosses.AbstractCharBoss;

import com.badlogic.gdx.graphics.g2d.*;
import com.megacrit.cardcrawl.helpers.*;
import com.badlogic.gdx.graphics.*;

public class EnemyDark extends AbstractEnemyOrb
{
    public static final String ORB_ID = "Dark";
    private static final OrbStrings orbString;
    public static final String[] DESC;
    private static final float ORB_BORDER_SCALE = 1.2f;
    private float vfxTimer;
    private static final float VFX_INTERVAL_TIME = 0.25f;
    
    public EnemyDark() {
        this.vfxTimer = 0.5f;
        this.ID = "Dark";
        this.img = ImageMaster.ORB_DARK;
        this.name = EnemyDark.orbString.NAME;
        this.baseEvokeAmount = 6;
        this.evokeAmount = this.baseEvokeAmount;
        this.basePassiveAmount = 6;
        this.passiveAmount = this.basePassiveAmount;
        this.updateDescription();
        this.channelAnimTimer = 0.5f;
    }
    
    @Override
    public void updateDescription() {
        this.applyFocus();
        this.description = EnemyDark.DESC[0] + this.passiveAmount + EnemyDark.DESC[1] + this.evokeAmount + EnemyDark.DESC[2];
    }
    
    @Override
    public void onEvoke() {
        AbstractDungeon.actionManager.addToTop(new DarkOrbEvokeAction(new DamageInfo(AbstractCharBoss.boss, this.evokeAmount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
    }
    
    @Override
    public void onEndOfTurn() {
        float speedTime = 0.6f / AbstractCharBoss.boss.orbs.size();
        if (Settings.FAST_MODE) {
            speedTime = 0.0f;
        }
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.DARK), speedTime));
        this.evokeAmount += this.passiveAmount;
        this.updateDescription();
    }
    
    @Override
    public void triggerEvokeAnimation() {
        CardCrawlGame.sound.play("ORB_DARK_EVOKE", 0.1f);
        AbstractDungeon.effectsQueue.add(new DarkOrbActivateEffect(this.cX, this.cY));
    }
    
    @Override
    public void applyFocus() {
        final AbstractPower power = AbstractCharBoss.boss.getPower("Focus");
        if (power != null) {
            this.passiveAmount = Math.max(0, this.basePassiveAmount + power.amount);
        }
        else {
            this.passiveAmount = this.basePassiveAmount;
        }
    }
    
    @Override
    public void updateAnimation() {
        super.updateAnimation();
        this.angle += Gdx.graphics.getDeltaTime() * 120.0f;
        this.vfxTimer -= Gdx.graphics.getDeltaTime();
        if (this.vfxTimer < 0.0f) {
            AbstractDungeon.effectList.add(new DarkOrbPassiveEffect(this.cX, this.cY));
            this.vfxTimer = 0.25f;
        }
    }
    
    @Override
    public void render(final SpriteBatch sb) {
        sb.setColor(this.c);
        sb.draw(this.img, this.cX - 48.0f, this.cY - 48.0f + this.bobEffect.y, 48.0f, 48.0f, 96.0f, 96.0f, this.scale, this.scale, this.angle, 0, 0, 96, 96, false, false);
        this.shineColor.a = this.c.a / 3.0f;
        sb.setColor(this.shineColor);
        sb.setBlendFunction(770, 1);
        sb.draw(this.img, this.cX - 48.0f, this.cY - 48.0f + this.bobEffect.y, 48.0f, 48.0f, 96.0f, 96.0f, this.scale * 1.2f, this.scale * 1.2f, this.angle / 1.2f, 0, 0, 96, 96, false, false);
        sb.draw(this.img, this.cX - 48.0f, this.cY - 48.0f + this.bobEffect.y, 48.0f, 48.0f, 96.0f, 96.0f, this.scale * 1.5f, this.scale * 1.5f, this.angle / 1.4f, 0, 0, 96, 96, false, false);
        sb.setBlendFunction(770, 771);
        this.renderText(sb);
        this.hb.render(sb);
    }
    
    @Override
    protected void renderText(final SpriteBatch sb) {
        FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.evokeAmount), this.cX + EnemyDark.NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0f + EnemyDark.NUM_Y_OFFSET - 4.0f * Settings.scale, new Color(0.2f, 1.0f, 1.0f, this.c.a), this.fontScale);
        FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.passiveAmount), this.cX + EnemyDark.NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0f + EnemyDark.NUM_Y_OFFSET + 20.0f * Settings.scale, this.c, this.fontScale);
    }
    
    @Override
    public void playChannelSFX() {
        CardCrawlGame.sound.play("ORB_DARK_CHANNEL", 0.1f);
    }
    
    @Override
    public AbstractOrb makeCopy() {
        return new EnemyDark();
    }
    
    static {
        orbString = CardCrawlGame.languagePack.getOrbString("Dark");
        DESC = EnemyDark.orbString.DESCRIPTION;
    }
}
