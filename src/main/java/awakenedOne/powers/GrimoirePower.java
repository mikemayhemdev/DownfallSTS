package awakenedOne.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class GrimoirePower extends AbstractAwakenedPower {

    public static final String NAME = GrimoirePower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);

    public GrimoirePower(int amount) {
        super(NAME, PowerType.BUFF, true, AbstractDungeon.player, null, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];

    }

}