//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package charbosses.actions.unique;

import charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;

import java.util.Iterator;

public class EnemyExhaustNonAttacksAction extends AbstractGameAction {

    public EnemyExhaustNonAttacksAction() {
        this.setValues(AbstractCharBoss.boss, AbstractCharBoss.boss);
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    public void update() {
        Iterator var2 = AbstractCharBoss.boss.hand.group.iterator();

        AbstractCard c;
        while(var2.hasNext()) {
            c = (AbstractCard) var2.next();
            if (c.type != CardType.ATTACK) {
                this.addToTop(new ExhaustSpecificCardAction(c, AbstractCharBoss.boss.hand));
            }
        }

        this.isDone = true;
    }
}
