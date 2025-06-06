package awakenedOne.powers;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class RisingChantPower extends AbstractAwakenedPower {
    // intellij stuff buff
    public static final String NAME = RisingChantPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);

    public RisingChantPower(int amount) {
        super(NAME, PowerType.BUFF, false, AbstractDungeon.player, null, amount);
    }

    public void updateDescription() {
        if (this.amount == 0) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        }

        if (this.amount != 0) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2];
        }
    }

}