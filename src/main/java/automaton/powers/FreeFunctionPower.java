package automaton.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class FreeFunctionPower extends AbstractAutomatonPower implements OnCompilePower {
    public static final String NAME = "FreeFunction";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public FreeFunctionPower(int amount) {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, amount);
    }

    @Override
    public void receiveCompile(AbstractCard function, boolean forGameplay) {
        if (function.cost > 0) {
            function.cost = 0;
            function.costForTurn = 0;
        }
        if (forGameplay) {
            flash();
            addToBot(new ReducePowerAction(owner, owner, this, 1));
        }
    }
}
