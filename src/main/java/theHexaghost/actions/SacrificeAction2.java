package theHexaghost.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class SacrificeAction2 extends AbstractGameAction {
    private AbstractPlayer p;
    private ArrayList<AbstractCard> cannotUpgrade = new ArrayList<>();

    public SacrificeAction2() {
        this.actionType = ActionType.EXHAUST;// 22
        this.p = AbstractDungeon.player;// 23
        this.duration = Settings.ACTION_DUR_FAST;// 24
    }// 26

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {// 30
            for (AbstractCard c : AbstractDungeon.player.hand.group)
                if (c.type == AbstractCard.CardType.POWER) {// 47
                    this.cannotUpgrade.add(c);// 48
                }
        }

        if (this.cannotUpgrade.size() == this.p.hand.group.size()) {// 53
            this.isDone = true;// 54
            return;// 55
        }

        if (this.p.hand.group.size() - this.cannotUpgrade.size() == 1) {// 58
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                this.p.hand.moveToExhaustPile(c);// 93
                if (c.type == AbstractCard.CardType.ATTACK) {
                    AbstractCard q = AbstractDungeon.returnTrulyRandomCardInCombat(AbstractCard.CardType.SKILL).makeCopy();// 32
                    q.setCostForTurn(0);// 33
                    this.addToBot(new MakeTempCardInHandAction(q, true));// 34
                } else if (c.type == AbstractCard.CardType.SKILL) {
                    AbstractCard q = AbstractDungeon.returnTrulyRandomCardInCombat(AbstractCard.CardType.ATTACK).makeCopy();// 32
                    q.setCostForTurn(0);// 33
                    this.addToBot(new MakeTempCardInHandAction(q, true));// 34
                }
            }
            this.isDone = true;// 64
            return;// 65

        }

        this.p.hand.group.removeAll(this.cannotUpgrade);// 72
        if (this.p.hand.group.size() > 1) {// 74
            AbstractDungeon.handCardSelectScreen.open("to Exhaust for Necessary Sacrifice", 1, false, false);// 75
            this.tickDuration();// 76
            return;// 77
        }

        if (this.p.hand.group.size() == 1) {// 78
            AbstractCard c = p.hand.getTopCard();
            this.p.hand.moveToExhaustPile(c);// 93
            if (c.type == AbstractCard.CardType.ATTACK) {
                AbstractCard q = AbstractDungeon.returnTrulyRandomCardInCombat(AbstractCard.CardType.SKILL).makeCopy();// 32
                q.setCostForTurn(0);// 33
                this.addToBot(new MakeTempCardInHandAction(q, true));// 34
            } else if (c.type == AbstractCard.CardType.SKILL) {
                AbstractCard q = AbstractDungeon.returnTrulyRandomCardInCombat(AbstractCard.CardType.ATTACK).makeCopy();// 32
                q.setCostForTurn(0);// 33
                this.addToBot(new MakeTempCardInHandAction(q, true));// 34
            }
            this.returnCards();// 81
            this.isDone = true;// 82
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {// 87
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                this.p.hand.moveToExhaustPile(c);// 93
                if (c.type == AbstractCard.CardType.ATTACK) {
                    AbstractCard q = AbstractDungeon.returnTrulyRandomCardInCombat(AbstractCard.CardType.SKILL).makeCopy();// 32
                    q.setCostForTurn(0);// 33
                    this.addToBot(new MakeTempCardInHandAction(q, true));// 34
                } else if (c.type == AbstractCard.CardType.SKILL) {
                    AbstractCard q = AbstractDungeon.returnTrulyRandomCardInCombat(AbstractCard.CardType.ATTACK).makeCopy();// 32
                    q.setCostForTurn(0);// 33
                    this.addToBot(new MakeTempCardInHandAction(q, true));// 34
                }
            }

            this.returnCards();// 95
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;// 96
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();// 97
            this.isDone = true;
        }
    }

    private void returnCards() {
        for (AbstractCard c : cannotUpgrade)
            this.p.hand.addToTop(c);
        this.p.hand.refreshHandLayout();
    }
}
