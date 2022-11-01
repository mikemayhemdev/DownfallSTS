package charbosses.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class EnemyReduceCostAction extends AbstractGameAction {

    private AbstractCard card = null;

    public EnemyReduceCostAction(AbstractCard card) {
        this.card = card;
    }


    public void update() {

        this.card.modifyCostForCombat(-1);
        this.isDone = true;
    }
}
