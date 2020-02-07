package charbosses.actions.utility;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.dungeons.*;

import charbosses.bosses.AbstractCharBoss;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.actions.common.*;
import java.util.*;

public class EnemyExhaustAllEtherealAction extends AbstractGameAction
{
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
