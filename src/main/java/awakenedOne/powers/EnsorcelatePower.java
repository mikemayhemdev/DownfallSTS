package awakenedOne.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class EnsorcelatePower extends AbstractAwakenedPower {
    // intellij stuff buff
    public static final String NAME = EnsorcelatePower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);

    public EnsorcelatePower(int amount) {
        super(NAME, PowerType.BUFF, false, AbstractDungeon.player, null, amount);
    }


    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.POWER && !card.purgeOnUse && this.amount > 0) {
            this.flash();
            --this.amount;
            if (this.amount == 0) {
                this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this));
            }
        }

    }

    public void updateDescription() {
        if (amount == 1) {
            description = DESCRIPTIONS[0];
        } else {
            description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        }
    }

}