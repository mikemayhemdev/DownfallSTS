package charbosses.stances;

import charbosses.bosses.AbstractCharBoss;
import charbosses.vfx.EnemyStanceAuraEffect;
import charbosses.vfx.EnemyStanceChangeParticleGenerator;
import charbosses.vfx.EnemyWrathParticleEffect;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;

public class EnWrathStance extends AbstractEnemyStance {
    public static final String STANCE_ID = "Wrath";
    private static final StanceStrings stanceString;
    private static long sfxId;

    public EnWrathStance() {
        this.ID = "Wrath";
        this.name = stanceString.NAME;
        this.updateDescription();
    }

    public float atDamageGive(float damage, DamageType type) {
        return type == DamageType.NORMAL ? damage * 1.5F : damage;
    }

    public void updateAnimation() {
        if (AbstractCharBoss.boss != null) {
            if (!Settings.DISABLE_EFFECTS) {
                this.particleTimer -= Gdx.graphics.getDeltaTime();
                if (this.particleTimer < 0.0F) {
                    this.particleTimer = 0.05F;
                    AbstractDungeon.effectsQueue.add(new EnemyWrathParticleEffect());
                }
            }

            this.particleTimer2 -= Gdx.graphics.getDeltaTime();
            if (this.particleTimer2 < 0.0F) {
                this.particleTimer2 = MathUtils.random(0.3F, 0.4F);
                AbstractDungeon.effectsQueue.add(new EnemyStanceAuraEffect("Wrath"));
            }
        }

    }

    public void updateDescription() {
        this.description = stanceString.DESCRIPTION[0];
    }

    public void onEnterStance() {
        if (sfxId != -1L) {
            this.stopIdleSfx();
        }

        CardCrawlGame.sound.play("STANCE_ENTER_WRATH");
        sfxId = CardCrawlGame.sound.playAndLoop("STANCE_LOOP_WRATH");
        AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.SCARLET, true));
        AbstractDungeon.effectsQueue.add(new EnemyStanceChangeParticleGenerator(AbstractCharBoss.boss.hb.cX, AbstractCharBoss.boss.hb.cY, "Wrath"));
    }

    public void onExitStance() {
        this.stopIdleSfx();
    }

    public void stopIdleSfx() {
        if (sfxId != -1L) {
            CardCrawlGame.sound.stop("STANCE_LOOP_WRATH", sfxId);
            sfxId = -1L;
        }

    }

    static {
        stanceString = CardCrawlGame.languagePack.getStanceString("Wrath");
        sfxId = -1L;
    }
}