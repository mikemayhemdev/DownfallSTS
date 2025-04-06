package theHexaghost.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Iterator;

public class DrawEtherealAction extends AbstractGameAction{

    public AbstractPlayer p;
    public int cards_to_draw;

    public DrawEtherealAction(int number) {
        this.cards_to_draw = number;
        this.p = AbstractDungeon.player;
        this.setValues(this.p, this.p);
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_MED) {
            if (this.p.drawPile.isEmpty()) {
                this.isDone = true;
                return;
            }
            int counter = 0;
            CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            Iterator<AbstractCard> var2 = this.p.drawPile.group.iterator();

            AbstractCard card;
            while ( var2.hasNext() && (counter < this.cards_to_draw) ){
                card = var2.next();
                if (card.isEthereal) {
                    tmp.addToRandomSpot(card);
                    counter++;
                }
            }


            if (tmp.size() == 0) {
                this.isDone = true;
                return;
            }

            for (int i = 0; i < counter; ++i) {
                if (!tmp.isEmpty()) {
                    tmp.shuffle();
                    card = tmp.getBottomCard();
                    tmp.removeCard(card);
                    if (this.p.hand.size() == BaseMod.MAX_HAND_SIZE) {
                        this.p.createHandIsFullDialog();
                    } else {
                        p.drawPile.group.remove(card);
                        p.drawPile.addToTop(card);
                        this.addToBot(new DrawCardAction(1));
                    }
                }
            }
            this.isDone = true;
        }
        this.tickDuration();
    }
}
