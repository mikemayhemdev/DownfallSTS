package awakenedOne.powers;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class RisingChantPower extends AbstractTwoAmountAwakenedPower {
    // intellij stuff buff
    public static final String NAME = RisingChantPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);

    public RisingChantPower(int amount) {
        super(NAME, PowerType.BUFF, false, AbstractDungeon.player, null, amount);
        amount2 = amount;
        updateDescription();
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        amount2 += stackAmount;
        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();
        amount2 = amount;
        updateDescription();
    }

    public void updateDescription() {

        //The first +N+ Chant effects activated this turn are triggered twice. (+Y+ Effects remaining this turn.)
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.amount2 + DESCRIPTIONS[2];
    }

    @Override
    public void onSpecificTrigger() {
        super.onSpecificTrigger();
        flash();
        amount2--;
        updateDescription();
    }
}