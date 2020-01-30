package charbosses.actions.common;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.dungeons.*;

import charbosses.bosses.AbstractCharBoss;

import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.cards.*;
import java.util.*;

public class EnemyRestoreRetainedCardsAction extends AbstractGameAction
{
    private CardGroup group;
    private AbstractCharBoss boss;
    
    public EnemyRestoreRetainedCardsAction(AbstractCharBoss boss, final CardGroup group) {
        this.setValues(boss, this.source, -1);
        this.group = group;
        this.boss = boss;
    }
    
    @Override
    public void update() {
        this.isDone = true;
        final Iterator<AbstractCard> c = this.group.group.iterator();
        while (c.hasNext()) {
            final AbstractCard e = c.next();
            if (e.retain || e.selfRetain) {
                e.onRetained();
                this.boss.hand.addToTop(e);
                e.retain = false;
                c.remove();
            }
        }
        this.boss.hand.refreshHandLayout();
    }
}
