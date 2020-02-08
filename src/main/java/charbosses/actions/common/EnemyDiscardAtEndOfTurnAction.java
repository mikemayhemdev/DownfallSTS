package charbosses.actions.common;

import charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class EnemyDiscardAtEndOfTurnAction extends AbstractGameAction {
    private static final float DURATION;

    static {
        DURATION = Settings.ACTION_DUR_XFAST;
    }

    private AbstractCharBoss boss;

    public EnemyDiscardAtEndOfTurnAction(AbstractCharBoss boss) {
        this.duration = EnemyDiscardAtEndOfTurnAction.DURATION;
        this.boss = boss;
    }

    public EnemyDiscardAtEndOfTurnAction() {
        this(AbstractCharBoss.boss);
    }

    @Override
    public void update() {
        if (this.duration == EnemyDiscardAtEndOfTurnAction.DURATION) {
            final Iterator<AbstractCard> c = this.boss.hand.group.iterator();
            while (c.hasNext()) {
                final AbstractCard e = c.next();
                if (e.retain || e.selfRetain) {
                    this.boss.limbo.addToTop(e);
                    c.remove();
                }
            }
            this.addToTop(new EnemyRestoreRetainedCardsAction(this.boss, this.boss.limbo));
            if (!this.boss.hasRelic("Runic Pyramid") && !this.boss.hasPower("Equilibrium")) {
                for (int tempSize = this.boss.hand.size(), i = 0; i < tempSize; ++i) {
                    this.addToTop(new EnemyDiscardAction(this.boss, null, this.boss.hand.size(), true));
                }
            }
            final ArrayList<AbstractCard> cards = (ArrayList<AbstractCard>) this.boss.hand.group.clone();
            Collections.shuffle(cards);
            for (final AbstractCard c2 : cards) {
                c2.triggerOnEndOfPlayerTurn();
            }
            this.isDone = true;
        }
    }
}