package automaton.powers;

import automaton.actions.RepeatCardAction;
import automaton.cards.Peashooter;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static automaton.AutomatonMod.makeID;

public class AutoTurretPower extends AbstractAutomatonPower {
    public static final String NAME = "AutoTurret";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public AutoTurretPower(int amount) {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, amount);
    }

    @Override
    public void atStartOfTurnPostDraw() {
        for (int i = 0; i < amount; i++) {
            addToBot(new RepeatCardAction(new Peashooter()));
        }
    }
}
