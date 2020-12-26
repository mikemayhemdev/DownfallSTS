package automaton.powers;

import automaton.FunctionHelper;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.unique.AddCardToDeckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class HardcodePower extends AbstractAutomatonPower implements OnOutputFunctionPower {
    public static final String NAME = "Hardcode";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public HardcodePower(int amount) {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, amount);
    }

    @Override
    public boolean receiveOutputFunction() {
        flash();
        AbstractCard function2 = FunctionHelper.makeFunction(true);
        addToBot(new AddCardToDeckAction(function2));
        addToBot(new ReducePowerAction(owner, owner, this, 1));
        return false;
    }
}
