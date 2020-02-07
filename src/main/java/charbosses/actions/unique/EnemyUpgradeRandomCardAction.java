package charbosses.actions.unique;

import com.megacrit.cardcrawl.actions.*;

import charbosses.bosses.AbstractCharBoss;

import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.cards.*;

public class EnemyUpgradeRandomCardAction extends AbstractGameAction
{
    private AbstractCharBoss p;
    
    public EnemyUpgradeRandomCardAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.p = AbstractCharBoss.boss;
        this.duration = Settings.ACTION_DUR_FAST;
    }
    
    @Override
    public void update() {
        if (this.duration != Settings.ACTION_DUR_FAST) {
            this.tickDuration();
            return;
        }
        if (this.p.hand.group.size() <= 0) {
            this.isDone = true;
            return;
        }
        final CardGroup upgradeable = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (final AbstractCard c : this.p.hand.group) {
            if (c.canUpgrade() && c.type != AbstractCard.CardType.STATUS) {
                upgradeable.addToTop(c);
            }
        }
        if (upgradeable.size() > 0) {
            upgradeable.shuffle();
            upgradeable.group.get(0).upgrade();
            upgradeable.group.get(0).superFlash();
            upgradeable.group.get(0).applyPowers();
        }
        this.isDone = true;
    }
}
