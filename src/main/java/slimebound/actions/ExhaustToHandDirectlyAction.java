package slimebound.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ExhaustToHandDirectlyAction extends AbstractGameAction {
    private AbstractCard card;

    public ExhaustToHandDirectlyAction(AbstractCard card) {
        this.actionType = ActionType.CARD_MANIPULATION;// 12
        this.card = card;// 13
        this.duration = Settings.ACTION_DUR_FAST;// 14
    }// 15

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {// 19
            if ((AbstractDungeon.player.discardPile.contains(this.card)) && AbstractDungeon.player.hand.size() < 10) {// 20 21
                AbstractDungeon.player.hand.addToHand(this.card);// 22
                this.card.unhover();// 23
                this.card.setAngle(0.0F, true);// 24
                this.card.lighten(false);// 25
                this.card.drawScale = 0.12F;// 26
                this.card.targetDrawScale = 0.75F;// 27
                this.card.applyPowers();// 28
                AbstractDungeon.player.discardPile.removeCard(this.card);
            }

            AbstractDungeon.player.hand.refreshHandLayout();// 32
            AbstractDungeon.player.hand.glowCheck();// 33
        }

        this.tickDuration();// 36
        this.isDone = true;// 37
    }// 38
}
