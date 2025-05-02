package awakenedOne.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ThoughtshieldPower extends AbstractAwakenedPower {
    // intellij stuff buff
    public static final String NAME = ThoughtshieldPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);

    public ThoughtshieldPower() {
        super(NAME, PowerType.BUFF, true, AbstractDungeon.player, null, -1);
        canGoNegative = false;
    }


    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
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