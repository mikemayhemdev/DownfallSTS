package hermit.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Iterator;

// DEPRECATED

public class ManifestAction extends AbstractGameAction {
    private AbstractPlayer p;
    private CardType typeToCheck;

    public ManifestAction(int amount) {
        this.p = AbstractDungeon.player;
        this.setValues(this.p, this.p, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;
        this.typeToCheck = CardType.CURSE;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_MED) {
            if (this.p.drawPile.isEmpty()) {
                this.isDone = true;
                return;
            }
            int counter = 0;
            CardGroup tmp = new CardGroup(CardGroupType.UNSPECIFIED);
            Iterator var2 = this.p.drawPile.group.iterator();

            AbstractCard card;
            while(var2.hasNext() && counter < amount) {
                card = (AbstractCard)var2.next();
                if (card.type == this.typeToCheck) {
                    tmp.addToRandomSpot(card);
                    counter++;
                }
            }

            if (tmp.size() == 0) {
                this.isDone = true;
                return;
            }


            for(int i = 0; i < counter; ++i) {
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
