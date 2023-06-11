package collector.powers;

import collector.cards.Ember;
import collector.util.Wiz;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static collector.util.Wiz.makeInHand;

public class SoulforgePower extends AbstractCollectorPower {
    public static final String NAME = "Crackle";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public SoulforgePower() {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, 1);
    }

    @Override
    public void atStartOfTurnPostDraw() {
        flash();
        makeInHand(new Ember(), amount);
    }
}