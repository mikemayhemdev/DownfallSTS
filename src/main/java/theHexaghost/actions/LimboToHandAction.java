package theHexaghost.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class LimboToHandAction extends AbstractGameAction
{
    private AbstractCard card;

    public LimboToHandAction(AbstractCard card)
    {
        actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.card = card;
        duration = Settings.ACTION_DUR_FAST;
    }

    public void update()
    {
        if (duration == Settings.ACTION_DUR_FAST) {
            if (AbstractDungeon.player.limbo.contains(card) &&
                    AbstractDungeon.player.hand.size() < 10) {
                AbstractDungeon.player.hand.addToHand(card);
                card.unhover();
                card.setAngle(0.0F, true);
                card.lighten(false);
                card.drawScale = 0.12F;
                card.targetDrawScale = 0.75F;
                card.applyPowers();
                AbstractDungeon.player.limbo.removeCard(card);
            } else {
                AbstractDungeon.player.limbo.moveToDiscardPile(card);
            }
            AbstractDungeon.player.hand.refreshHandLayout();
            AbstractDungeon.player.hand.glowCheck();
        }
        tickDuration();
        isDone = true;
    }
}