//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package champ.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class OpenerReduceCostAction extends AbstractGameAction {
    private AbstractCard card = null;

    public OpenerReduceCostAction() {

    }

    public void update() {
        if (AbstractDungeon.player.hand.size() > 0) {
            ArrayList<AbstractCard> validCards = new ArrayList<>();
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (c.costForTurn > 0) {
                    validCards.add(c);
                }
            }
            if (validCards.size() > 0) {
                card = validCards.get(AbstractDungeon.cardRng.random(0, validCards.size() - 1));
            }
        }
        if (card != null) {
            card.costForTurn = card.costForTurn - 1;
            card.isCostModifiedForTurn = true;
            card.superFlash();
        }
        this.isDone = true;
    }
}
