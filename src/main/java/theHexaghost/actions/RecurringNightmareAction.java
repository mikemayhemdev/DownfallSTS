package theHexaghost.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class RecurringNightmareAction extends AbstractGameAction {
    private AbstractPlayer p;
    private ArrayList<AbstractCard> cannotExhume = new ArrayList<>();

    public RecurringNightmareAction() {
        this.p = AbstractDungeon.player;// 27
        this.setValues(this.p, AbstractDungeon.player, this.amount);// 28
        this.actionType = ActionType.CARD_MANIPULATION;// 29
        this.duration = Settings.ACTION_DUR_FAST;// 30
    }// 31

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {// 35
            if (AbstractDungeon.player.hand.size() == 10) {// 37
                AbstractDungeon.player.createHandIsFullDialog();// 38
                this.isDone = true;// 39
            } else if (this.p.exhaustPile.isEmpty()) {// 44
                this.isDone = true;// 45
            } else {
                for (AbstractCard c : AbstractDungeon.player.exhaustPile.group)
                    if (!c.isEthereal) {// 47
                        this.cannotExhume.add(c);// 48
                    }
            }

            if (p.exhaustPile.size() == cannotExhume.size()) {
                isDone = true;
                return;
            }

            if (this.p.exhaustPile.size() - cannotExhume.size() == 1) {// 48
                AbstractCard c = this.p.exhaustPile.getTopCard();// 56
                c.unfadeOut();// 57
                this.p.hand.addToHand(c);// 58
                this.p.exhaustPile.removeCard(c);// 63
                c.unhover();// 67
                c.fadingOut = false;// 68
                this.isDone = true;// 69
            } else {

                p.exhaustPile.group.removeAll(cannotExhume);
                AbstractDungeon.gridSelectScreen.open(this.p.exhaustPile, 1, "Choose an Ethereal card to return to your hand.", false);// 96
                this.tickDuration();// 97
            }
        }
        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {// 102
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {// 103 112
                this.p.hand.addToHand(c);// 104
                c.unfadeOut();// 57
                this.p.exhaustPile.removeCard(c);// 63
                c.unhover();// 67
                c.fadingOut = false;// 68
            }

            AbstractDungeon.gridSelectScreen.selectedCards.clear();// 114
            this.p.hand.refreshHandLayout();// 115
            this.p.exhaustPile.group.addAll(cannotExhume);// 118
        }

        this.tickDuration();// 128
    }
}// 40 46 53 70 93 98 129
