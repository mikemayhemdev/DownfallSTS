package awakenedOne.powers;

import awakenedOne.cards.tokens.Feather;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.PoisonPower;

public class SheerTerrorPower extends AbstractAwakenedPower {
    // intellij stuff buff
    public static final String NAME = SheerTerrorPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);

    public SheerTerrorPower(AbstractCreature owner, int amount) {
        super(NAME, PowerType.DEBUFF, false, owner, null, amount);

        if (this.amount <= 0) {
            this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this.POWER_ID));
        }

        updateDescription();

    }


    public void atStartOfTurn() {
        if (this.amount <= 0) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        } else {
            flash();
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, this, 1));
        }
    }

    public void updateDescription() {
        if (this.amount == 0) {
            this.description = DESCRIPTIONS[0];
        }

        if (this.amount != 0) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2];
        }

    }

}