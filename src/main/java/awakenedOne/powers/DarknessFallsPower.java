package awakenedOne.powers;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class DarknessFallsPower extends AbstractAwakenedPower implements OnLoseEnergyPower {
    // intellij stuff buff

    public static final String NAME = DarknessFallsPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);


    public DarknessFallsPower(int amount)  {
        super(NAME, PowerType.BUFF, false, AbstractDungeon.player, null, amount);
    }

    @Override
    public void LoseEnergyAction(int gained) {
        this.flash();
        addToBot(new GainBlockAction(owner, gained*amount*2));
        applyToSelf(new StrengthPower(AbstractDungeon.player, gained*amount));
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount*2 + DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
    }
}