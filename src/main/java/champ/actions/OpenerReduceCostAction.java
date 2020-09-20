//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package champ.actions;

import champ.util.OnReducedByOpenerSubscriber;
import com.badlogic.gdx.graphics.Color;
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
            Color c = new Color(1.0F, 0.8F, 0.2F, 0.0F);
            if (card instanceof OnReducedByOpenerSubscriber) c = ((OnReducedByOpenerSubscriber) card).onReducedByOpener(c);
            card.superFlash(c);
        }
        this.isDone = true;
    }
}
