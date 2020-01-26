package slimebound.actions;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import slimebound.SlimeboundMod;
import slimebound.powers.SlimedPower;


public class DoublePoisonSlimedWeakAction extends com.megacrit.cardcrawl.actions.AbstractGameAction {
    private float startingDuration;
    private boolean upgraded;

    public DoublePoisonSlimedWeakAction(AbstractCreature target, AbstractCreature source,boolean upgraded) {
        this.target = target;
        this.source = source;
        this.upgraded=upgraded;
        this.startingDuration = com.megacrit.cardcrawl.core.Settings.ACTION_DUR_FAST;
        this.actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.DEBUFF;
        this.attackEffect = com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.FIRE;
        this.duration = this.startingDuration;
    }

    public void update() {
        if ((this.duration == this.startingDuration) &&
                (this.target != null) && (this.target.hasPower(SlimedPower.POWER_ID))) {
            com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager.addToTop(new com.megacrit.cardcrawl.actions.common.ApplyPowerAction(this.target, this.source, new SlimedPower(this.target, this.source,


                    this.target.getPower(SlimedPower.POWER_ID).amount + SlimeboundMod.getAcidTongueBonus(this.source)),
                    this.target.getPower(SlimedPower.POWER_ID).amount + SlimeboundMod.getAcidTongueBonus(this.source)));
        }
        if (upgraded) {
            if ((this.duration == this.startingDuration) &&
                    (this.target != null) && (this.target.hasPower("Poison"))) {
                com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager.addToTop(new com.megacrit.cardcrawl.actions.common.ApplyPowerAction(this.target, this.source, new PoisonPower(this.target, this.source,


                        this.target.getPower("Poison").amount),
                        this.target.getPower("Poison").amount));
            }
            /*
            if ((this.duration == this.startingDuration) &&
                    (this.target != null) && (this.target.hasPower("Weakened"))) {
                com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager.addToTop(new com.megacrit.cardcrawl.actions.common.ApplyPowerAction(this.target, this.source, new WeakPower(this.target,


                        this.target.getPower("Weakened").amount, false),
                        this.target.getPower("Weakened").amount));
            }
            */
        }



        tickDuration();
    }
}

