package awakenedOne.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.EnergizedBluePower;

import static awakenedOne.util.Wiz.att;

public class VoidRefundPower extends AbstractAwakenedPower implements OnLoseEnergyPower {
    // intellij stuff buff

    public static final String NAME = VoidRefundPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);


    public VoidRefundPower(final AbstractCreature owner, int amount) {
        super(NAME, PowerType.BUFF, false, owner, null, amount);
        updateDescription();
    }


    @Override
    public void LoseEnergyAction(int gained) {
        this.flash();

        if (!AbstractDungeon.actionManager.turnHasEnded) {
            att(new GainEnergyAction(this.amount));
        }

        if (AbstractDungeon.actionManager.turnHasEnded) {
            att(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new EnergizedBluePower(AbstractDungeon.player, this.amount), this.amount));
        }

        // atb(new DrawCardAction(AbstractDungeon.player, this.amount));
        addToTop(new RemoveSpecificPowerAction(owner, owner, this));
    }

    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0];
        }

        if (this.amount != 1) {
            this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        }
    }
}