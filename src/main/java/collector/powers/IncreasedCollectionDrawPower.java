package collector.powers;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class IncreasedCollectionDrawPower extends AbstractCollectorPower {
    public static final String NAME = "IncreasedCollectionDraw";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public IncreasedCollectionDrawPower() {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, 1);
    }


}