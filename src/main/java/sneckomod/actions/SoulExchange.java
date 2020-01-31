//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package sneckomod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class SoulExchange extends AbstractGameAction {
    private AbstractPlayer p;

    public SoulExchange() {
        this.actionType = ActionType.CARD_MANIPULATION;// 22
        this.p = AbstractDungeon.player;// 23
        this.duration = Settings.ACTION_DUR_FAST;// 24
    }// 26

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {// 30

            if (this.p.hand.group.size() > 1) {// 74
                AbstractDungeon.handCardSelectScreen.open("to Exhaust for Nope.", 1, false, false);// 75
                this.tickDuration();// 76
                return;// 77
            }

            if (this.p.hand.group.size() == 1) {// 78
                AbstractCard c = p.hand.getTopCard();
                p.hand.moveToExhaustPile(c);
                AbstractCard card = AbstractDungeon.returnTrulyRandomCardInCombat(c.type);
                if (card.cost >= 0) {// 32
                    int newCost = AbstractDungeon.cardRandomRng.random(3);// 33
                    if (card.cost != newCost) {// 34
                        card.cost = newCost;// 35
                        card.costForTurn = card.cost;// 36
                        card.isCostModified = true;// 37
                    }

                    card.freeToPlayOnce = false;// 39
                }
                this.addToBot(new MakeTempCardInHandAction(card, true));// 34
                this.isDone = true;// 82
            }
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {// 87
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                p.hand.moveToExhaustPile(c);
                AbstractCard card = AbstractDungeon.returnTrulyRandomCardInCombat(c.type);
                if (card.cost >= 0) {// 32
                    int newCost = AbstractDungeon.cardRandomRng.random(3);// 33
                    if (card.cost != newCost) {// 34
                        card.cost = newCost;// 35
                        card.costForTurn = card.cost;// 36
                        card.isCostModified = true;// 37
                    }

                    card.freeToPlayOnce = false;// 39
                }
                this.addToBot(new MakeTempCardInHandAction(card, true));// 34
            }

            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;// 96
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();// 97
            this.isDone = true;// 98
        }
        this.tickDuration();// 101
    }// 102
}
