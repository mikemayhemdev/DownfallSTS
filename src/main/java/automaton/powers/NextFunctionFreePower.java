package automaton.powers;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class NextFunctionFreePower extends AbstractAutomatonPower {
    public static final String NAME = "NextFunctionFreePower";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;


    public NextFunctionFreePower(int count) {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, -1);
        updateDescription();
        amount = count;
    }


    @Override
    public void updateDescription() {
        if (amount > 1) {
            description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[1];
        } else {
            description = DESCRIPTIONS[0];
        }

    }
}
