package awakenedOne.powers;

import awakenedOne.cards.tokens.spells.AbstractSpellCard;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static awakenedOne.util.Wiz.atb;

public class BrainshockPower extends AbstractAwakenedPower {
    // intellij stuff buff
    public static final String NAME = BrainshockPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);

    public BrainshockPower(int amount) {
        super(NAME, PowerType.BUFF, false, AbstractDungeon.player, null, amount);
    }

    @Override
    public void onAfterCardPlayed(AbstractCard card) {
        if (card instanceof AbstractSpellCard) {
            this.flash();

            atb(new DrawCardAction(AbstractDungeon.player, amount));
            atb(new GainEnergyAction(amount));
            addToTop(new RemoveSpecificPowerAction(owner, owner, this));
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        addToTop(new RemoveSpecificPowerAction(owner, owner, this));
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[1];
    }

}