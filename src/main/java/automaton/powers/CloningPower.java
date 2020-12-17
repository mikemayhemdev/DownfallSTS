package automaton.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class CloningPower extends AbstractAutomatonPower {
    public static final String NAME = "Cloning";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public CloningPower(int amount) {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, amount);
    }

    @Override
    public void onSpecificTrigger() {
        flash();
        addToBot(new ReducePowerAction(owner, owner, this, 1));
    }
}
