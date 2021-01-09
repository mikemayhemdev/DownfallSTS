package automaton.powers;

import automaton.actions.AddToFuncAction;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DefaultPower extends AbstractAutomatonPower implements NonStackablePower, AfterOutputFunctionPower {
    public static final String NAME = "Default";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    private AbstractCard stored;

    public DefaultPower(AbstractCard mine, int amount) {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, amount);
        canGoNegative = false;
        stored = mine;
        updateDescription();
    }

    @Override
    public void receiveAfterOutputFunction() {
        flash();
        addToBot(new AddToFuncAction(stored.makeStatEquivalentCopy(), null));
        addToBot(new ReducePowerAction(owner, owner, this, 1));
    }

    @Override
    public void updateDescription() {
        if (stored != null) {
            description = DESCRIPTIONS[0] + stored.name + (amount == 1 ? DESCRIPTIONS[1] : DESCRIPTIONS[2] + amount + DESCRIPTIONS[3]);
        }
        else {
            description = "";
        }

    }
}
