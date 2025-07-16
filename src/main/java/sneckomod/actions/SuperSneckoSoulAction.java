package sneckomod.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import sneckomod.actions.MuddleAction;

public class SuperSneckoSoulAction extends AbstractGameAction {

    public SuperSneckoSoulAction() {
    }

    @Override
    public void update() {
        if (AbstractDungeon.player.hand.size() < BaseMod.MAX_HAND_SIZE) {
            addToBot(new DrawCardAction(1, new AbstractGameAction() {
                @Override
                public void update() {
                    if (AbstractDungeon.player.hand.size() > 0) {
                        AbstractCard drawnCard = AbstractDungeon.player.hand.getTopCard();
                        addToTop(new MuddleAction(drawnCard));
                    }
                    isDone = true;
                }
            }));
        }
        isDone = true;
    }
}