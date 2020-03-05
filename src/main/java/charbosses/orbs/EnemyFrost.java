package charbosses.orbs;

import charbosses.bosses.AbstractCharBoss;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.combat.FrostOrbActivateEffect;
import com.megacrit.cardcrawl.vfx.combat.FrostOrbPassiveEffect;
import com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect;

public class EnemyFrost extends AbstractEnemyOrb {
    public static final String ORB_ID = "Frost";
    private static final OrbStrings orbString;

    static {
        orbString = CardCrawlGame.languagePack.getOrbString("Frost");
    }

    private boolean hFlip1 = MathUtils.randomBoolean();
    private boolean hFlip2 = MathUtils.randomBoolean();
    private float vfxTimer = 1.0F;
    private float vfxIntervalMin = 0.15F;
    private float vfxIntervalMax = 0.8F;

    public EnemyFrost() {
        this.ID = "Frost";
        this.name = orbString.NAME;
        this.baseEvokeAmount = 5;
        this.evokeAmount = this.baseEvokeAmount;
        this.basePassiveAmount = 2;
        this.passiveAmount = this.basePassiveAmount;
        this.updateDescription();
        this.channelAnimTimer = 0.5F;
    }

    public void updateDescription() {
        this.applyFocus();
        this.description = orbString.DESCRIPTION[0] + this.passiveAmount + orbString.DESCRIPTION[1] + this.evokeAmount + orbString.DESCRIPTION[2];
    }

    public void onEvoke() {
        AbstractDungeon.actionManager.addToTop(new GainBlockAction(AbstractCharBoss.boss, AbstractCharBoss.boss, this.evokeAmount));
    }

    public void updateAnimation() {
        super.updateAnimation();
        this.angle += Gdx.graphics.getDeltaTime() * 180.0F;
        this.vfxTimer -= Gdx.graphics.getDeltaTime();
        if (this.vfxTimer < 0.0F) {
            AbstractDungeon.effectList.add(new FrostOrbPassiveEffect(this.cX, this.cY));
            if (MathUtils.randomBoolean()) {
                AbstractDungeon.effectList.add(new FrostOrbPassiveEffect(this.cX, this.cY));
            }

            this.vfxTimer = MathUtils.random(this.vfxIntervalMin, this.vfxIntervalMax);
        }
    }

    public void onEndOfTurn() {
        float speedTime = 0.6F / (float) AbstractCharBoss.boss.orbs.size();
        if (Settings.FAST_MODE) {
            speedTime = 0.0F;
        }

        AbstractDungeon.actionManager.addToTop(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.FROST), speedTime));
        AbstractDungeon.actionManager.addToTop(new GainBlockAction(AbstractCharBoss.boss, AbstractCharBoss.boss, this.passiveAmount, true));
    }

    public void triggerEvokeAnimation() {
        CardCrawlGame.sound.play("ORB_FROST_EVOKE", 0.1F);
        AbstractDungeon.effectsQueue.add(new FrostOrbActivateEffect(this.cX, this.cY));
    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.c);
        sb.draw(ImageMaster.FROST_ORB_RIGHT, this.cX - 48.0F + this.bobEffect.y / 4.0F, this.cY - 48.0F + this.bobEffect.y / 4.0F, 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, this.hFlip1, false);
        sb.draw(ImageMaster.FROST_ORB_LEFT, this.cX - 48.0F + this.bobEffect.y / 4.0F, this.cY - 48.0F - this.bobEffect.y / 4.0F, 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, this.hFlip1, false);
        sb.draw(ImageMaster.FROST_ORB_MIDDLE, this.cX - 48.0F - this.bobEffect.y / 4.0F, this.cY - 48.0F + this.bobEffect.y / 2.0F, 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, this.hFlip2, false);
        this.renderText(sb);
        this.hb.render(sb);
    }

    public void playChannelSFX() {
        CardCrawlGame.sound.play("ORB_FROST_CHANNEL", 0.1F);
    }

    public AbstractOrb makeCopy() {
        return new EnemyFrost();
    }
}