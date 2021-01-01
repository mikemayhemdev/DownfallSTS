package timeEater.powers;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import timeEater.ClockHelper;

public class LunchBreakPower extends AbstractTimePower implements OnTickPower {
    public static final String NAME = "LunchBreak";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public LunchBreakPower(int amount) {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, amount);
    }

    @Override
    public void onTick() {
        if (ClockHelper.clock == 6) {
            flash();
            atb(new GainBlockAction(owner, amount));
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
