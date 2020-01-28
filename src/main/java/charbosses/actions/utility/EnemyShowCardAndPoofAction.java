package charbosses.actions.utility;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.vfx.cardManip.*;

import charbosses.bosses.AbstractCharBoss;

public class EnemyShowCardAndPoofAction extends AbstractGameAction
{
    private AbstractCard card;
    private static final float PURGE_DURATION = 0.2f;
    
    public EnemyShowCardAndPoofAction(final AbstractCard card) {
        this.card = null;
        this.setValues(AbstractCharBoss.boss, null, 1);
        this.card = card;
        this.duration = 0.2f;
        this.actionType = ActionType.SPECIAL;
    }
    
    @Override
    public void update() {
        if (this.duration == 0.2f) {
            AbstractDungeon.effectList.add(new ExhaustCardEffect(this.card));
            if (AbstractCharBoss.boss.limbo.contains(this.card)) {
            	AbstractCharBoss.boss.limbo.removeCard(this.card);
            }
            AbstractCharBoss.boss.cardInUse = null;
        }
        this.tickDuration();
    }
}
