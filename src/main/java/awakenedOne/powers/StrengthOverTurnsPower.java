package awakenedOne.powers;

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static awakenedOne.util.Wiz.atb;

public class StrengthOverTurnsPower extends AbstractAwakenedPower implements NonStackablePower {
    public static final String NAME = "StrengthOverTurns";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = true;
    private int duration;

    public StrengthOverTurnsPower(int duration, int strength) {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, strength);
        this.duration = duration;
        updateDescription();
    }

    @Override
    public void atStartOfTurnPostDraw() {
        flash();
        applyToSelf(new StrengthPower(owner, amount));
        duration--;
        if (duration == 0) {
            atb(new RemoveSpecificPowerAction(owner, owner, this));
        }
        updateDescription();
    }

    @Override
    public void updateDescription() {
        if (duration == 1) {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        } else {
            description = DESCRIPTIONS[2] + duration + DESCRIPTIONS[3] + amount + DESCRIPTIONS[1];
        }
    }
}
