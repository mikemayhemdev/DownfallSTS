package awakenedOne.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class IntensifyDebuffPower extends AbstractAwakenedPower {
    // intellij stuff buff
    public static final String NAME = IntensifyDebuffPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);

    public IntensifyDebuffPower(int amount) {
        super(NAME, PowerType.DEBUFF, true, AbstractDungeon.player, null, amount);
    }

    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            --this.amount;
            updateDescription();
            if (this.amount == 0) {
                this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, ID));
            }
        }
    }

    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0];
        } else {
            this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        }

    }

}