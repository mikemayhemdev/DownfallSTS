package awakenedOne.powers;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class AboveItAllPower extends AbstractAwakenedPower implements OnAwakenPower {
    // intellij stuff buff
    public static final String NAME = AboveItAllPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);

    public AboveItAllPower(int amount) {
        super(NAME, PowerType.BUFF, false, AbstractDungeon.player, null, amount);
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public void onAwaken(int gained) {
        flash();
        addToBot(new GainBlockAction(owner, amount));
    }
}