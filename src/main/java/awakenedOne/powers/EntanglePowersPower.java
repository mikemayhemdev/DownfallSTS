package awakenedOne.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class EntanglePowersPower extends AbstractAwakenedPower {
    public static final String NAME = EntanglePowersPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);

    public EntanglePowersPower(int amount) {
        super(NAME, PowerType.DEBUFF, false, AbstractDungeon.player, null, amount);
        updateDescription();
    }

    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0];
        } else {
            this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        }

    }

    @Override
    public void onAfterCardPlayed(AbstractCard card) {
        if (card.type == AbstractCard.CardType.POWER) {
            this.flash();
            this.addToBot(new LoseEnergyAction(1));
            //this.addToBot(new ApplyPowerAction(this.owner, this.owner, new Drained(this.owner,this.owner, 1), 1));
            this.amount--;
            updateDescription();
            if (this.amount == 0) {
                this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, ID));
            }
        }
    }
}
