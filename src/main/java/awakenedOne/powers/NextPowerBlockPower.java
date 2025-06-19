package awakenedOne.powers;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class NextPowerBlockPower extends AbstractAwakenedPower {
    // intellij stuff buff
    public static final String NAME = NextPowerBlockPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);

    public NextPowerBlockPower(int amount) {
        super(NAME, PowerType.BUFF, false, AbstractDungeon.player, null, amount);
    }

    @Override
    public void onAfterCardPlayed(AbstractCard card) {
        if (card.type == AbstractCard.CardType.POWER) {
            this.flash();
            this.addToBot(new DrawCardAction(amount));
            addToTop(new RemoveSpecificPowerAction(owner, owner, this));
        }
    }

    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        } else {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2];
        }
    }

}