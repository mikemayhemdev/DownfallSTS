package charbosses.actions.utility;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.dungeons.*;

import charbosses.bosses.AbstractCharBoss;

import com.megacrit.cardcrawl.core.*;

public class EnemyShowCardAction extends AbstractGameAction
{
    private AbstractCard card;
    private static final float PURGE_DURATION = 0.2f;
    
    public EnemyShowCardAction(final AbstractCard card) {
        this.card = null;
        this.setValues(AbstractCharBoss.boss, null, 1);
        this.card = card;
        this.duration = 0.2f;
        this.actionType = ActionType.SPECIAL;
    }
    
    @Override
    public void update() {
        if (this.duration == 0.2f) {
            if (AbstractCharBoss.boss.limbo.contains(this.card)) {
            	AbstractCharBoss.boss.limbo.removeCard(this.card);
            }
            AbstractCharBoss.boss.cardInUse = null;
        }
        this.tickDuration();
    }
}
