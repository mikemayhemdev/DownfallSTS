package awakenedOne.powers;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ShroudOfMiasmaPower extends AbstractAwakenedPower {
    // intellij stuff buff
    public static final String NAME = ShroudOfMiasmaPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);

    public ShroudOfMiasmaPower(int amount) {
        super(NAME, PowerType.BUFF, false, AbstractDungeon.player, null, amount);
    }

    @Override
    public void onSpecificTrigger() {
        flash();
        addToTop(new GainBlockAction(owner, amount, true));
    }


    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}