package charbosses.actions.unique;

import charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class EnemyDiscardToHandAction extends AbstractGameAction {
    private AbstractCard card;
    private AbstractCharBoss p;

    public EnemyDiscardToHandAction(AbstractCharBoss p, AbstractCard card) {
        this.p = p;
        this.actionType = ActionType.CARD_MANIPULATION;// 12
        this.card = card;// 13
        this.duration = Settings.ACTION_DUR_FAST;// 14
    }// 15

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {// 19
            if (p.hand.size() < 10) {// 20 21
                p.hand.addToHand(this.card);// 22
                this.card.unhover();// 23
                this.card.setAngle(0.0F, true);// 24
                this.card.lighten(false);// 25
                this.card.drawScale = 0.12F;// 26
                this.card.targetDrawScale = 0.75F;// 27
                this.card.applyPowers();// 28
                //p.discardPile.removeCard(this.card);// 29
            }

            p.hand.refreshHandLayout();// 32
            p.hand.glowCheck();// 33
        }

        this.tickDuration();// 36
        this.isDone = true;// 37
    }// 38
}
