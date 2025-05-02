package awakenedOne.powers;

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnReceivePowerPower;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static awakenedOne.util.Wiz.atb;

public class PrimacyPower extends AbstractAwakenedPower implements OnReceivePowerPower {
    // intellij stuff buff
    public static final String NAME = PrimacyPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);

    public PrimacyPower(int amount) {
        super(NAME, PowerType.BUFF, false, AbstractDungeon.player, null, amount);
    }


    @Override
    public boolean onReceivePower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if ((power instanceof StrengthPower) && (power.amount > 0)) {
            atb(new DrawCardAction(AbstractDungeon.player, this.amount));
        }
        return true;
    }


    @Override
    public void updateDescription() {
        if (amount == 1) {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        } else {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2];
        }
    }
}