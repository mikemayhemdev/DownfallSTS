package collector.powers;

import collector.util.Wiz;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class AlwaysMorePower extends AbstractCollectorPower {
    public static final String NAME = "AlwaysMore";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public AlwaysMorePower() {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, 1);
    }


}