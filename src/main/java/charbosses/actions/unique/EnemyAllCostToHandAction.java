package charbosses.actions.unique;

import charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.Iterator;

public class EnemyAllCostToHandAction extends AbstractGameAction {
    private AbstractCharBoss p;
    private int costTarget;

    public EnemyAllCostToHandAction(AbstractCharBoss p, int costToTarget) {
        this.p = p;
        this.actionType = ActionType.CARD_MANIPULATION;// 16
        this.costTarget = costToTarget;// 17
    }// 18

    public void update() {
        if (this.p.discardPile.size() > 0) {// 22
            Iterator var1 = this.p.discardPile.group.iterator();

            label21:
            while(true) {
                AbstractCard card;
                do {
                    if (!var1.hasNext()) {// 23
                        break label21;
                    }

                    card = (AbstractCard)var1.next();
                } while(card.cost != this.costTarget && !card.freeToPlayOnce);// 24

                this.addToBot(new EnemyDiscardToHandAction(p, card));// 25
            }
        }

        this.tickDuration();// 29
        this.isDone = true;// 30
    }// 31
}
