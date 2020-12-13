package champ.stances;

import champ.ChampChar;
import champ.ChampMod;
import champ.actions.FatigueHpLossAction;
import champ.powers.ResolvePower;
import champ.util.OnTechniqueSubscriber;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.IntenseZoomEffect;
import slimebound.SlimeboundMod;

public abstract class AbstractChampStance extends AbstractStance {

    private static long sfxId = -1L;
    public String STANCE_ID = "guardianmod:AbstractMode";

    public AbstractChampStance() {
        this.ID = STANCE_ID;
        this.name = ChampChar.characterStrings.TEXT[3];
        this.updateDescription();// 23
    }

    public abstract String getKeywordString();

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

    public int fatigue(int begone) {

        int y = AbstractDungeon.player.currentHealth;
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                int x = Math.min(begone, AbstractDungeon.player.currentHealth - 1);
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ResolvePower(x), x));
                AbstractDungeon.actionManager.addToTop(new FatigueHpLossAction(AbstractDungeon.player, AbstractDungeon.player, x));
            }
        });

        /*atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                if (y - AbstractDungeon.player.currentHealth > 0) {
                    att(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ResolvePower(y - AbstractDungeon.player.currentHealth), y - AbstractDungeon.player.currentHealth));
                }
            }
        });
        */ //This unused method makes it so the player only gains Resolve equal to lost HP. Fixes some breakable things, but also unfun.

        return Math.min(begone, AbstractDungeon.player.currentHealth - 1);
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

        AbstractDungeon.actionManager.addToTop(new VFXAction(AbstractDungeon.player, new IntenseZoomEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, false), 0.05F, true));
        AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.DARK_GRAY, true));

        if (AbstractDungeon.player instanceof ChampChar) {
            SlimeboundMod.logger.info("Switchin stances to: " + this.ID);
            //((ChampChar) AbstractDungeon.player).switchStanceVisual(this.ID);
        }

    }

    @Override
    public void onExitStance() {
        stopIdleSfx();
        /*if (AbstractDungeon.player.stance instanceof NeutralStance) {
            if (AbstractDungeon.player instanceof ChampChar) {
                ((ChampChar) AbstractDungeon.player).switchStanceVisual(NeutralStance.STANCE_ID);
            }
        }
        */
    }

    public void stopIdleSfx() {
        /*
             if (sfxId != -1L) {
                   CardCrawlGame.sound.stop(GuardianMod.makeID("STANCE_LOOP_Defensive_Mode"), sfxId);
                   sfxId = -1L;
             }
             */
    }

    public abstract void updateDescription();

    public void techique() {
        technique();
        for (AbstractPower q : AbstractDungeon.player.powers) {
            if (q instanceof OnTechniqueSubscriber) {
                ((OnTechniqueSubscriber) q).onTechnique();
            }
        }
        for (AbstractCard r : AbstractDungeon.player.hand.group) {
            if (r instanceof OnTechniqueSubscriber) {
                ((OnTechniqueSubscriber) r).onTechnique();
            }
        }
        ChampMod.techniquesThisTurn++;
    }

    public abstract void technique();

    public void fisher() {
        finisher();
    }

    public abstract void finisher();
}
