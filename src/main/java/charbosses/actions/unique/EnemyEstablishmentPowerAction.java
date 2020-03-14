package charbosses.actions.unique;

import charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.Iterator;

public class EnemyEstablishmentPowerAction extends AbstractGameAction {
    private int discountAmount;

    public EnemyEstablishmentPowerAction(int discountAmount) {
        this.discountAmount = discountAmount;
    }

    public void update() {
        Iterator var1 = AbstractCharBoss.boss.hand.group.iterator();

        while(true) {
            AbstractCard c;
            do {
                if (!var1.hasNext()) {
                    this.isDone = true;
                    return;
                }

                c = (AbstractCard)var1.next();
            } while(!c.selfRetain && !c.retain);

            c.modifyCostForCombat(-this.discountAmount);
        }
    }
}
