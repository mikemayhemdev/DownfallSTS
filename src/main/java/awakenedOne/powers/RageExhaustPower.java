package awakenedOne.powers;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;

public class RageExhaustPower extends AbstractAwakenedPower {
    // intellij stuff buff
    public static final String NAME = RageExhaustPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);

    public RageExhaustPower(AbstractCreature owner, int amount) {
        super(NAME, PowerType.BUFF, false, owner, null, amount);
    }

    @Override
    public void onExhaust(AbstractCard card) {
        this.flash();
        this.addToBot(new GainBlockAction(this.owner, this.amount, Settings.FAST_MODE));
    }

    @Override
    public void atStartOfTurn() {
        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
    }

    public void updateDescription() {
        description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

}