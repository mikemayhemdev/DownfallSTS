package collector.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class NextTurnReservePower extends AbstractCollectorPower {
    public static final String NAME = "NextTurnReserve";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = true;

    public NextTurnReservePower(int amount) {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, amount);
    }


    public void atStartOfTurn() {
        this.flash();
        addToBot(new ApplyPowerAction(owner, owner, new ReservePower(amount), amount));
        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
    }

}