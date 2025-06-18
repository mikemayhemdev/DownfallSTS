package awakenedOne.powers;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DarkIncantationRitualPower extends AbstractAwakenedPower {
    // intellij stuff buff
    public static final String NAME = DarkIncantationRitualPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);

    public DarkIncantationRitualPower(int amount) {
        super(NAME, PowerType.BUFF, true, AbstractDungeon.player, null, amount);
    }

    @Override
    public void onSpecificTrigger() {
        flash();
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}