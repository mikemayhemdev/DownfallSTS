//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//
package awakenedOne.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class EntanglePowersPower extends AbstractAwakenedPower {
    public static final String NAME = EntanglePowersPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);

    public EntanglePowersPower(int amount) {
        super(NAME, PowerType.DEBUFF, true, AbstractDungeon.player, null, amount);
    }

    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0];
        } else {
            this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        }

    }

    public void atEndOfTurn(boolean isPlayer) {
        --this.amount;
        updateDescription();
        if (this.amount == 0) {
            this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, ID));
        }
    }
}
