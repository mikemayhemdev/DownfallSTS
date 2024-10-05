package theHexaghost.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Iterator;

public class AllEtherealToHandAction extends AbstractGameAction {

    private AbstractPlayer p;

    public AllEtherealToHandAction() {
        this.p = AbstractDungeon.player;
        this.setValues(this.p, AbstractDungeon.player, this.amount);
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
    }

    public void update() {
        if (this.p.discardPile.size() > 0) {
            Iterator var1 = this.p.discardPile.group.iterator();

            label21:
            while(true) {
                AbstractCard card;
                do {
                    if (!var1.hasNext()) {
                        break label21;
                    }

                    card = (AbstractCard)var1.next();
                } while(!card.isEthereal);

                this.addToBot(new DiscardToHandAction(card));
            }
        }

        this.tickDuration();
        this.isDone = true;
    }
}
