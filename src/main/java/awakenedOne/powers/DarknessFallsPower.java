package awakenedOne.powers;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class DarknessFallsPower extends AbstractAwakenedPower implements OnLoseEnergyPower {
    // intellij stuff buff

    public static final String NAME = DarknessFallsPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);

    // intellij stuff buff

    public DarknessFallsPower(final AbstractCreature owner, int amount) {
        super(NAME, PowerType.BUFF, false, owner, null, amount);
        updateDescription();
    }

    @Override
    public void LoseEnergyAction(int gained) {
        this.flash();
        addToBot(new GainBlockAction(owner, gained * amount));
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}