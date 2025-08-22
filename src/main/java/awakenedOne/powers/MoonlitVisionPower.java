package awakenedOne.powers;

import awakenedOne.AwakenedOneMod;
import awakenedOne.cards.tokens.spells.AbstractSpellCard;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static awakenedOne.util.Wiz.atb;

public class MoonlitVisionPower extends AbstractTwoAmountAwakenedPower {
    // intellij stuff buff
    public static final String NAME = MoonlitVisionPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);

    private  int activationsThisTurn;

    public MoonlitVisionPower() {
        super(NAME, PowerType.BUFF, false, AbstractDungeon.player, null, 1);
        amount2 = 1 - AwakenedOneMod.spellsThisTurn;
        if (amount2 < 0) amount2 = 0;
        updateDescription();
    }

    @Override
    public void onAfterCardPlayed(AbstractCard usedCard) {
        if (usedCard instanceof AbstractSpellCard) {
            if (amount2 > 0) {
                atb(new GainEnergyAction(1));
                amount2--;
                activationsThisTurn++;
                flash();
                updateDescription();
            }
        }
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        amount2 += stackAmount;
        updateDescription();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        amount2 = amount;
        activationsThisTurn =0;
        updateDescription();
    }

    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + Math.min(amount2, AwakenedOneMod.spellsThisTurn - this.activationsThisTurn) + DESCRIPTIONS[2];
    }

}