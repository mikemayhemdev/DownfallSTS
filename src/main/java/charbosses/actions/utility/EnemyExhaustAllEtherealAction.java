package charbosses.actions.utility;

import charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class EnemyExhaustAllEtherealAction extends AbstractGameAction {
    public EnemyExhaustAllEtherealAction() {
        this.actionType = ActionType.WAIT;
    }

    @Override
    public void update() {
        for (final AbstractCard c : AbstractCharBoss.boss.hand.group) {
            if (c.isEthereal) {
                this.addToTop(new ExhaustSpecificCardAction(c, AbstractCharBoss.boss.hand));
            }
        }
        this.isDone = true;
    }
}
