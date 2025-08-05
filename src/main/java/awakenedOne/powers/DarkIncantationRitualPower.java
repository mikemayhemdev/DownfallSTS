package awakenedOne.powers;

import awakenedOne.actions.ConjureAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static awakenedOne.util.Wiz.atb;

public class DarkIncantationRitualPower extends AbstractAwakenedPower {
    // intellij stuff buff
    public static final String NAME = DarkIncantationRitualPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);

    public DarkIncantationRitualPower(int amount) {
        super(NAME, PowerType.BUFF, false, AbstractDungeon.player, null, amount);
    }

    @Override
    public void atStartOfTurn() {
        flash();
        this.addToBot(new ApplyPowerAction(this.owner, this.owner, new DoubleSpellPower(this.amount), this.amount));
    }

    public void updateDescription() {
        if (amount == 1) {
            description = DESCRIPTIONS[0];
        } else {
            description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        }
    }
}