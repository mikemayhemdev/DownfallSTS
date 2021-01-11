//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package charbosses.actions.unique;

import charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.ArrayList;
import java.util.Iterator;

public class EnemyBlockPerNonAttackAction extends AbstractGameAction {
    private int blockPerCard;

    public EnemyBlockPerNonAttackAction(int blockAmount) {
        this.blockPerCard = blockAmount;
        this.setValues(AbstractCharBoss.boss, AbstractCharBoss.boss);
        this.actionType = ActionType.BLOCK;
    }

    public void update() {
        ArrayList<AbstractCard> cardsToExhaust = new ArrayList<>();
        Iterator var2 = AbstractCharBoss.boss.hand.group.iterator();

        AbstractCard c;
        while(var2.hasNext()) {
            c = (AbstractCard)var2.next();
            if (c.type != CardType.ATTACK) {
                cardsToExhaust.add(c);
            }
        }

        var2 = cardsToExhaust.iterator();

        while(var2.hasNext()) {
            c = (AbstractCard)var2.next();
            this.addToTop(new GainBlockAction(AbstractCharBoss.boss, AbstractCharBoss.boss, this.blockPerCard));
        }

        var2 = cardsToExhaust.iterator();

        while(var2.hasNext()) {
            c = (AbstractCard)var2.next();
            this.addToTop(new ExhaustSpecificCardAction(c, AbstractCharBoss.boss.hand));
        }

        this.isDone = true;
    }
}
