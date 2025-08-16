package awakenedOne.powers;

import awakenedOne.actions.ConjureAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static awakenedOne.util.Wiz.atb;

public class ConjureNextPower extends AbstractAwakenedPower {
    // intellij stuff buff
    public static final String NAME = ConjureNextPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);

    public ConjureNextPower(int amount) {
        super(NAME, PowerType.BUFF, true, AbstractDungeon.player, null, amount);
        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        flash();
        for (int i = 0; i < amount; i++) {
            atb(new ConjureAction(false));
        }
        this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, ID));
    }

    public void updateDescription() {
        if (amount == 1) {
            description = DESCRIPTIONS[0];
        } else {
            description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        }
    }

}