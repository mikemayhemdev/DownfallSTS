package charbosses.actions.unique;

import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.blue.EnClaw;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Iterator;

public class EnemyGashAction extends AbstractGameAction {
    private AbstractCard card;
    private AbstractCharBoss p;

    public EnemyGashAction(AbstractCharBoss player, AbstractBossCard card, int amount) {
        this.card = card;// 12
        this.amount = amount;// 13
        this.p = player;
    }// 14

    public void update() {
        AbstractCard var10000 = this.card;// 18
        var10000.baseDamage += this.amount;
        this.card.applyPowers();// 19
        Iterator var1 = p.discardPile.group.iterator();// 21

        AbstractCard c;
        while (var1.hasNext()) {
            c = (AbstractCard) var1.next();
            if (c instanceof EnClaw) {// 22
                c.baseDamage += this.amount;// 23
                c.applyPowers();// 24
            }
        }

        var1 = AbstractCharBoss.boss.drawPile.group.iterator();// 28

        while (var1.hasNext()) {
            c = (AbstractCard) var1.next();
            if (c instanceof EnClaw) {// 29
                c.baseDamage += this.amount;// 30
                c.applyPowers();// 31
            }
        }

        var1 = AbstractCharBoss.boss.hand.group.iterator();// 35

        while (var1.hasNext()) {
            c = (AbstractCard) var1.next();
            if (c instanceof EnClaw) {// 36
                c.baseDamage += this.amount;// 37
                c.applyPowers();// 38
            }
        }

        this.isDone = true;// 42
    }// 43
}
