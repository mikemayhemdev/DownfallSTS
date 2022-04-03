package timeeater.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import timeeater.suspend.SuspendHelper;

public class SuspendDiscardTopAction extends AbstractGameAction {

    public SuspendDiscardTopAction() {
    }

    @Override
    public void update() {
        isDone = true;
        if (!AbstractDungeon.player.discardPile.isEmpty()) {
            AbstractCard q = AbstractDungeon.player.discardPile.getTopCard();
            AbstractDungeon.player.discardPile.removeCard(q);
            SuspendHelper.suspend(q);
        }
    }
}
