package awakenedOne.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.RitualPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class ReverseRitualPower extends AbstractAwakenedPower {
    // intellij stuff buff
    public static final String NAME = ReverseRitualPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);


    public ReverseRitualPower(int amount) {
        super(NAME, PowerType.BUFF, false, AbstractDungeon.player, null, amount);
        updateDescription();
        this.canGoNegative = false;
        priority = -99;
    }


    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public void atStartOfTurn() {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new RitualPower(this.owner, this.amount, true), this.amount));
    }

}
