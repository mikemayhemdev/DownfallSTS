package awakenedOne.powers;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class FeathersinksPower extends AbstractAwakenedPower {
    // intellij stuff buff
    public static final String NAME = FeathersinksPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);

    public FeathersinksPower(int amount) {
        super(NAME, PowerType.BUFF, false, AbstractDungeon.player, null, amount);
    }

    @Override
    public void updateDescription() {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}