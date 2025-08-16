package awakenedOne.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class CursedStrength extends AbstractAwakenedPower implements OnLoseEnergyPower {
    // intellij stuff buff

    public static final String NAME = CursedStrength.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);

    // intellij stuff buff

    public CursedStrength(final AbstractCreature owner, int amount) {
        super(NAME, PowerType.BUFF, false, owner, null, amount);
        updateDescription();
    }

    @Override
    public void LoseEnergyAction(int gained) {
        this.flash();
        applyToSelf(new StrengthPower(AbstractDungeon.player, amount));
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}