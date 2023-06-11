package collector.powers;

import collector.util.Wiz;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class CracklePower extends AbstractCollectorPower {
    public static final String NAME = "Crackle";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public CracklePower() {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, 1);
    }

    @Override
    public void atStartOfTurnPostDraw() {
        flash();
        Wiz.applyToSelf(new ReservePower(amount));
    }
}