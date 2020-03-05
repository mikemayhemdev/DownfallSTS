package charbosses.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import java.util.Iterator;
import java.util.UUID;

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
