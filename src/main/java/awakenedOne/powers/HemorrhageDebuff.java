package awakenedOne.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class HemorrhageDebuff extends AbstractAwakenedPower {
    // intellij stuff buff
    public static final String NAME = HemorrhageDebuff.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);

    public HemorrhageDebuff(int amount) {
        super(NAME, PowerType.DEBUFF, false, AbstractDungeon.player, null, amount);
    }

    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            this.flash();
            applyToSelf(new BleedDebuff(amount));
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
