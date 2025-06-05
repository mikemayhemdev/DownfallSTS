package awakenedOne.powers;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class IntoNothingPower extends AbstractAwakenedPower {
    // intellij stuff buff
    public static final String NAME = IntoNothingPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);
    private int cardsDoubledThisTurn = 0;

    public IntoNothingPower(int amount) {
        super(NAME, PowerType.BUFF, false, AbstractDungeon.player, null, amount);
    }

    public void atStartOfTurn() {
        this.cardsDoubledThisTurn = 0;
        updateDescription();
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (!card.purgeOnUse && (card.costForTurn == 0 || card.freeToPlayOnce) && this.cardsDoubledThisTurn < this.amount) {
            this.cardsDoubledThisTurn += 1;
            flash();
            AbstractMonster m = null;

            if (action.target != null) {
                m = (AbstractMonster) action.target;
            }

            AbstractCard tmp = card.makeSameInstanceOf();
            AbstractDungeon.player.limbo.addToBottom(tmp);
            tmp.current_x = card.current_x;
            tmp.current_y = card.current_y;
            tmp.target_x = (Settings.WIDTH / 2.0F - 300.0F * Settings.scale);
            tmp.target_y = (Settings.HEIGHT / 2.0F);
            tmp.freeToPlayOnce = true;

            if (m != null) {
                tmp.calculateCardDamage(m);
            }

            tmp.purgeOnUse = true;
            tmp.applyPowers();
            AbstractDungeon.actionManager.cardQueue.add(new com.megacrit.cardcrawl.cards.CardQueueItem(tmp, m, card.energyOnUse));
            updateDescription();
        }
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        updateDescription();
    }

    public void updateDescription() {


        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0];
        } else {
            this.description = (DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2]);
        }

        if (cardsDoubledThisTurn == this.amount) {
            this.description += DESCRIPTIONS[6];
        } else if ((this.amount - cardsDoubledThisTurn) > 1) {
            this.description += DESCRIPTIONS[3] + (this.amount - cardsDoubledThisTurn) + DESCRIPTIONS[5];
        } else {
            this.description += DESCRIPTIONS[3] + (this.amount - cardsDoubledThisTurn) + DESCRIPTIONS[4];
        }
    }

}