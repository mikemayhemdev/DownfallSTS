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
    private int cardsDoubledThisTurn = 0;


    public PrimacyPower(int amount) {
        super(NAME, PowerType.BUFF, false, AbstractDungeon.player, null, amount);
    }


    public void atStartOfTurn() {
        this.cardsDoubledThisTurn = 0;
        updateDescription();
    }

    @Override
    public boolean onReceivePower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (cardsDoubledThisTurn < this.amount) {
            if ((power instanceof StrengthPower) && (power.amount > 0)) {
                flash();
                this.cardsDoubledThisTurn += 1;
                atb(new DrawCardAction(AbstractDungeon.player, 1));
                updateDescription();
            }
        }
        return true;
    }


    public void updateDescription() {


        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0];
        } else {
            this.description = (DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2]);
        }

        if (cardsDoubledThisTurn >= 3) {
            this.description += DESCRIPTIONS[6];
        } else if ((this.amount - cardsDoubledThisTurn) > 1) {
            this.description += DESCRIPTIONS[3] + (this.amount - cardsDoubledThisTurn) + DESCRIPTIONS[5];
        } else {
            this.description += DESCRIPTIONS[3] + (this.amount - cardsDoubledThisTurn) + DESCRIPTIONS[4];
        }
    }
}