package awakenedOne.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;

import static awakenedOne.util.Wiz.atb;
import static awakenedOne.util.Wiz.att;

public class FourthDimensionPower extends AbstractAwakenedPower {
    // intellij stuff buff
    public static final String NAME = FourthDimensionPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);

    private final AbstractCard card;

    public FourthDimensionPower(int amount, AbstractCard held) {
        super(NAME, PowerType.BUFF, false, AbstractDungeon.player, null, amount);
        card = held;
        canGoNegative = false;
        updateDescription();
    }

    @Override
    public void atStartOfTurnPostDraw() {
        flash();
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                AbstractCard q = card.makeStatEquivalentCopy();

                q.freeToPlayOnce = true;
                q.exhaust = true;

                att(new NewQueueCardAction(q, true, true, true));
            }
        });
        atb(new ReducePowerAction(owner, owner, this, 1));
    }

    @Override
    public void updateDescription() {
        if (card != null)
            description = DESCRIPTIONS[0] + amount + (amount == 1 ? DESCRIPTIONS[1] : DESCRIPTIONS[2]) + DESCRIPTIONS[3] + FontHelper.colorString(card.name, "y") + DESCRIPTIONS[4];
        else
            description = "";
    }
}