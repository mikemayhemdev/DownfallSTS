package awakenedOne.powers;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BigSpenderPower extends AbstractAwakenedPower {
    // intellij stuff buff
    public static final String NAME = BigSpenderPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);

    public BigSpenderPower(int amount) {
        super(NAME, PowerType.BUFF, false, AbstractDungeon.player, null, amount);
    }

    int activationsThisTurn = 0;

    @Override
    public void atStartOfTurn() {
        activationsThisTurn = 0;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.costForTurn > 1) {
            if (activationsThisTurn < amount) {
                flash();
                addToBot(new GainEnergyAction(1));
                activationsThisTurn += 1;
            }
        }
    }

    public void updateDescription() {
        if (amount == 1) {
            description = DESCRIPTIONS[0] + DESCRIPTIONS[3] + activationsThisTurn + DESCRIPTIONS[4];
        } else {
            description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2] + DESCRIPTIONS[3] + activationsThisTurn + DESCRIPTIONS[4];
        }
    }

}