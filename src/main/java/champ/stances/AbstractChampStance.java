package champ.stances;

import champ.ChampChar;
import champ.ChampMod;
import champ.actions.FatigueHpLossAction;
import champ.cards.AbstractChampCard;
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

    }

    @Override
    public void onEnterStance() {

       // CardCrawlGame.sound.play("GUARDIAN_ROLL_UP");

        //AbstractDungeon.actionManager.addToTop(new VFXAction(AbstractDungeon.player, new IntenseZoomEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, false), 0.05F, true));
        //AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.DARK_GRAY, true));
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
