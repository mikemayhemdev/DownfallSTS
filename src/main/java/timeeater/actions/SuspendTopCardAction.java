package timeeater.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import timeeater.suspend.SuspendHelper;

import static timeeater.util.Wiz.att;

public class SuspendTopCardAction extends AbstractGameAction {

    public SuspendTopCardAction() {
    }

    @Override
    public void update() {
        isDone = true;
        if (AbstractDungeon.player.drawPile.size() + AbstractDungeon.player.discardPile.size() == 0) {
            return;
        } else if (AbstractDungeon.player.drawPile.isEmpty()) {
            att(new SuspendTopCardAction());
            att(new EmptyDeckShuffleAction());
            return;
        }
        else {
            if (!AbstractDungeon.player.drawPile.isEmpty()) {
                AbstractCard q = AbstractDungeon.player.drawPile.getTopCard();
                AbstractDungeon.player.drawPile.removeCard(q);
                SuspendHelper.suspend(q);
            }
        }
    }
}
