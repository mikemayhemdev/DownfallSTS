package automaton.powers;

import automaton.MechaHelper;
import automaton.actions.FireFromPileAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static automaton.AutomatonMod.makeID;

public class BarragePower extends AbstractAutomatonPower {
    public static final String NAME = "Barrage";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public BarragePower(int amount) {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, amount);
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            flash();
            addToBot(new FireFromPileAction(MechaHelper.blasters, 1));
        }
    }
}
