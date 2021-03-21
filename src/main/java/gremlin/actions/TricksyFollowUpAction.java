package gremlin.actions;

import com.megacrit.cardcrawl.actions.defect.ScrapeAction;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.*;

public class TricksyFollowUpAction extends AbstractGameAction
{
    public TricksyFollowUpAction() {
        this.duration = Settings.ACTION_DUR_FASTER;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FASTER) {
            for (final AbstractCard c : ScrapeAction.scrapedCards) {
                if (c.type != AbstractCard.CardType.ATTACK) {
                    AbstractDungeon.player.hand.moveToDiscardPile(c);
                    c.triggerOnManualDiscard();
                    GameActionManager.incrementDiscard(false);
                }
            }
            ScrapeAction.scrapedCards.clear();
        }
        this.tickDuration();
    }
}
