package automaton.powers;

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class VerifyPower extends AbstractAutomatonPower implements AfterOutputFunctionPower {
    public static final String NAME = "Verify";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    private boolean activated;

    public VerifyPower(int amount) {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, amount);
    }

    @Override
    public void atStartOfTurn() {
        activated = false;
    }

    @Override
    public void receiveAfterOutputFunction() {
        if (!activated) {
            flash();
            addToBot(new GainEnergyAction(amount));
            activated = true;
        }
    }
}
