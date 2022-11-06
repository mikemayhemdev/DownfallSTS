package collector.actions;

import collector.CollectorCollection;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static collector.util.Wiz.att;

public class DrawCardFromCollectionAction extends AbstractGameAction {
    public DrawCardFromCollectionAction() {
        this.actionType = ActionType.SPECIAL;
    }

    @Override
    public void update() {
        if (!CollectorCollection.combatCollection.isEmpty()) {
            AbstractCard tar = CollectorCollection.combatCollection.getTopCard();
            CollectorCollection.combatCollection.removeCard(tar);
            AbstractDungeon.player.drawPile.addToTop(tar);
            att(new DrawCardAction(1));
        }
        this.isDone = true;
    }
}