package awakenedOne.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnReceivePowerPower;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class MercyPower extends AbstractAwakenedPower implements CloneablePowerInterface, OnReceivePowerPower {
    // intellij stuff buff
    public static final String NAME = MercyPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);

    public MercyPower(int amount) {
        super(NAME, PowerType.BUFF, false, AbstractDungeon.player, null, amount);
    }


    @Override
    public boolean onReceivePower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power.ID == "awakened:HemorrhageDebuff") {
            this.flash();
            this.addToTop(new GainBlockAction(AbstractDungeon.player, this.amount)); }
        return true;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new MercyPower(amount);
    }

}