package automaton.powers;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ReturnPower extends AbstractAutomatonPower {
    public static final String NAME = "Return";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public ReturnPower(int amount) {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, amount);
    }

    @Override
    public void atStartOfTurn() {
        addToBot(new DrawCardAction(1));
        addToBot(new GainEnergyAction(1));
        addToBot(new ReducePowerAction(owner, owner, this, 1));
    }
}
