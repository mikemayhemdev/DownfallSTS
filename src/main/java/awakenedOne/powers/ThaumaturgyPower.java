package awakenedOne.powers;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ThaumaturgyPower extends AbstractAwakenedPower {
    // intellij stuff buff
    public static final String NAME = ThaumaturgyPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);

    public ThaumaturgyPower(int amount) {
        super(NAME, PowerType.BUFF, false, AbstractDungeon.player, null, amount);
    }


    @Override
    public void onAfterCardPlayed(AbstractCard card) {
        if (card.type == AbstractCard.CardType.POWER) {
            this.flash();
            this.addToTop(new GainBlockAction(this.owner, this.amount));
        }
    }

    public void updateDescription() {
    this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

}