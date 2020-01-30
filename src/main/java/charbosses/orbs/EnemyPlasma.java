package charbosses.orbs;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.badlogic.gdx.math.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.animations.*;
import com.badlogic.gdx.*;
import com.megacrit.cardcrawl.vfx.combat.*;

import charbosses.actions.common.EnemyGainEnergyAction;

import com.badlogic.gdx.graphics.g2d.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.helpers.*;
import com.badlogic.gdx.graphics.*;

public class EnemyPlasma extends AbstractEnemyOrb
{
    public static final String ORB_ID = "Plasma";
    private static final OrbStrings orbString;
    public static final String[] DESC;
    private float vfxTimer;
    private float vfxIntervalMin;
    private float vfxIntervalMax;
    private static final float ORB_WAVY_DIST = 0.04f;
    private static final float PI_4 = 12.566371f;
    
    public EnemyPlasma() {
        this.vfxTimer = 1.0f;
        this.vfxIntervalMin = 0.1f;
        this.vfxIntervalMax = 0.4f;
        this.ID = "Plasma";
        this.img = ImageMaster.ORB_PLASMA;
        this.name = EnemyPlasma.orbString.NAME;
        this.baseEvokeAmount = 2;
        this.evokeAmount = this.baseEvokeAmount;
        this.basePassiveAmount = 1;
        this.passiveAmount = this.basePassiveAmount;
        this.updateDescription();
        this.angle = MathUtils.random(360.0f);
        this.channelAnimTimer = 0.5f;
    }
    
    @Override
    public void updateDescription() {
        this.applyFocus();
        this.description = EnemyPlasma.DESC[0] + this.evokeAmount + EnemyPlasma.DESC[1];
    }
    
    @Override
    public void onEvoke() {
        AbstractDungeon.actionManager.addToTop(new EnemyGainEnergyAction(this.evokeAmount));
    }
    
    @Override
    public void onStartOfTurn() {
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.PLASMA), 0.1f));
        AbstractDungeon.actionManager.addToBottom(new EnemyGainEnergyAction(this.passiveAmount));
    }
    
    @Override
    public void triggerEvokeAnimation() {
        CardCrawlGame.sound.play("ORB_PLASMA_EVOKE", 0.1f);
        AbstractDungeon.effectsQueue.add(new PlasmaOrbActivateEffect(this.cX, this.cY));
    }
    
    @Override
    public void updateAnimation() {
        super.updateAnimation();
        this.angle += Gdx.graphics.getDeltaTime() * 45.0f;
        this.vfxTimer -= Gdx.graphics.getDeltaTime();
        if (this.vfxTimer < 0.0f) {
            AbstractDungeon.effectList.add(new PlasmaOrbPassiveEffect(this.cX, this.cY));
            this.vfxTimer = MathUtils.random(this.vfxIntervalMin, this.vfxIntervalMax);
        }
    }
    
    @Override
    public void render(final SpriteBatch sb) {
        this.shineColor.a = this.c.a / 2.0f;
        sb.setColor(this.shineColor);
        sb.draw(this.img, this.cX - 48.0f, this.cY - 48.0f + this.bobEffect.y, 48.0f, 48.0f, 96.0f, 96.0f, this.scale + MathUtils.sin(this.angle / 12.566371f) * 0.04f * Settings.scale, this.scale, this.angle, 0, 0, 96, 96, false, false);
        sb.setBlendFunction(770, 1);
        sb.draw(this.img, this.cX - 48.0f, this.cY - 48.0f + this.bobEffect.y, 48.0f, 48.0f, 96.0f, 96.0f, this.scale, this.scale + MathUtils.sin(this.angle / 12.566371f) * 0.04f * Settings.scale, -this.angle, 0, 0, 96, 96, false, false);
        sb.setBlendFunction(770, 771);
        this.renderText(sb);
        this.hb.render(sb);
    }
    
    @Override
    protected void renderText(final SpriteBatch sb) {
        if (this.showEvokeValue) {
            FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.evokeAmount), this.cX + EnemyPlasma.NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0f + EnemyPlasma.NUM_Y_OFFSET - 4.0f * Settings.scale, new Color(0.2f, 1.0f, 1.0f, this.c.a), this.fontScale);
        }
    }
    
    @Override
    public void playChannelSFX() {
        CardCrawlGame.sound.play("ORB_PLASMA_CHANNEL", 0.1f);
    }
    
    @Override
    public AbstractOrb makeCopy() {
        return new EnemyPlasma();
    }
    
    static {
        orbString = CardCrawlGame.languagePack.getOrbString("Plasma");
        DESC = EnemyPlasma.orbString.DESCRIPTION;
    }
}
