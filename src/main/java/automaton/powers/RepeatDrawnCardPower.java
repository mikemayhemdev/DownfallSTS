package automaton.powers;

import automaton.actions.RepeatCardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static automaton.AutomatonMod.makeID;

public class RepeatDrawnCardPower extends AbstractAutomatonPower {
    public static final String NAME = "RepeatDrawn";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public RepeatDrawnCardPower(int amount) {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, amount);
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        flash();
        addToTop(new RemoveSpecificPowerAction(owner, owner, this));
        addToTop(new RepeatCardAction(card));
    }
}
