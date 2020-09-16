package champ.stances;

import champ.ChampChar;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.stances.NeutralStance;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.IntenseZoomEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceAuraEffect;
import slimebound.SlimeboundMod;

public class AbstractChampStance extends AbstractStance {

    public String STANCE_ID = "guardianmod:AbstractMode";
    private static long sfxId = -1L;

    public AbstractChampStance() {
        this.ID = STANCE_ID;
        this.name = ChampChar.characterStrings.TEXT[3];
        this.updateDescription();// 23
    }

    @Override
    public void updateAnimation() {
        /*
        if (!(AbstractDungeon.player instanceof ChampChar)) {
            if (!Settings.DISABLE_EFFECTS) {

                this.particleTimer -= Gdx.graphics.getDeltaTime();
                if (this.particleTimer < 0.0F) {
                    this.particleTimer = 0.04F;
                    AbstractDungeon.effectsQueue.add(new DefensiveModeStanceParticleEffect());
                }
            }


            this.particleTimer2 -= Gdx.graphics.getDeltaTime();
            if (this.particleTimer2 < 0.0F) {
                this.particleTimer2 = MathUtils.random(0.45F, 0.55F);
                AbstractDungeon.effectsQueue.add(new StanceAuraEffect("DefensiveMode"));
            }
        }
        */
    }



    @Override
    public void onEnterStance() {
        /*
             if (sfxId != -1L) {
                  stopIdleSfx();
             }
             */

        CardCrawlGame.sound.play("GUARDIAN_ROLL_UP");

        /*
        if (!(AbstractDungeon.player instanceof ChampChar)) {
//             CardCrawlGame.sound.play("STANCE_ENTER_CALM");
            sfxId = CardCrawlGame.sound.playAndLoop(GuardianMod.makeID("STANCE_LOOP_Defensive_Mode"));
        }
        */

        AbstractDungeon.actionManager.addToTop(new VFXAction(AbstractDungeon.player, new IntenseZoomEffect(AbstractDungeon.player.hb.cX,AbstractDungeon.player.hb.cY, false), 0.05F, true));
        AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.DARK_GRAY, true));

        if (AbstractDungeon.player instanceof ChampChar) {
            SlimeboundMod.logger.info("Switchin stances to: " + this.ID);
            ((ChampChar) AbstractDungeon.player).switchStanceVisual(this.ID);
        }

    }

    @Override
    public void onExitStance() {
        stopIdleSfx();
        if (AbstractDungeon.player.stance instanceof NeutralStance){
            if (AbstractDungeon.player instanceof ChampChar) {
                ((ChampChar) AbstractDungeon.player).switchStanceVisual("Neutral");
            }
        }
    }

       public void stopIdleSfx() {
        /*
             if (sfxId != -1L) {
                   CardCrawlGame.sound.stop(GuardianMod.makeID("STANCE_LOOP_Defensive_Mode"), sfxId);
                   sfxId = -1L;
             }
             */
           }

    @Override
    public void updateDescription() {
       this.description = ChampChar.characterStrings.TEXT[6];
    }
}
