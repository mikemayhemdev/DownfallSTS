package awakenedOne.powers;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class RisingChantPower extends AbstractAwakenedPower {
    // intellij stuff buff
    public static final String NAME = RisingChantPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);

    public RisingChantPower(int amount) {
        super(NAME, PowerType.BUFF, false, AbstractDungeon.player, null, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public void onSpecificTrigger() {
        applyToSelf(new StrengthPower(AbstractDungeon.player, amount));
        flash();
    }

}