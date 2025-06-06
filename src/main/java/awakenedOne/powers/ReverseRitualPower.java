//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//
package awakenedOne.powers;

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnReceivePowerPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static awakenedOne.util.Wiz.atb;

public class ReverseRitualPower extends AbstractAwakenedPower {
    // intellij stuff buff
    public static final String NAME = PrimacyPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);


    public ReverseRitualPower(int amount) {
        super(NAME, PowerType.DEBUFF, false, AbstractDungeon.player, null, amount);
    }


    public void updateDescription() {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            this.flash();
            this.addToBot(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, this.amount), this.amount));
        }
    }

}
