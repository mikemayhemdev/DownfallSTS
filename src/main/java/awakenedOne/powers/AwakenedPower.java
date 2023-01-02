package awakenedOne.powers;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class AwakenedPower extends AbstractAwakenedPower {
    // intellij stuff buff
    public static final String NAME = AwakenedPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);

    public AwakenedPower(int amount) {
        super(NAME, PowerType.BUFF, false, AbstractDungeon.player, null, amount);
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}