//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package theHexaghost.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theHexaghost.cards.NecessarySacrifice;

import java.util.ArrayList;

public class SacrificeAction2 extends AbstractGameAction {
    public boolean costify;
    private AbstractPlayer p;
    private ArrayList<AbstractCard> cannotUpgrade = new ArrayList<>();
    public static final String[] EXTENDED_DESCRIPTIONS = CardCrawlGame.languagePack.getCardStrings(NecessarySacrifice.ID).EXTENDED_DESCRIPTION;

    public SacrificeAction2(boolean upgrade) {
        this.actionType = ActionType.CARD_MANIPULATION;// 22
        this.p = AbstractDungeon.player;// 23
        this.duration = Settings.ACTION_DUR_FAST;// 24
        costify = upgrade;
    }// 26

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {// 30
            for (AbstractCard c : p.hand.group)
                if (!(c.type == AbstractCard.CardType.SKILL || c.type == AbstractCard.CardType.ATTACK)) {// 47
                    this.cannotUpgrade.add(c);// 48
                }

            if (this.cannotUpgrade.size() == this.p.hand.group.size()) {// 53
                this.isDone = true;// 54
                return;// 55
            }

            if (this.p.hand.group.size() - this.cannotUpgrade.size() == 1) {// 58
                for (AbstractCard c : p.hand.group)
                    if (c.type == AbstractCard.CardType.ATTACK || c.type == AbstractCard.CardType.SKILL) {// 60
                        p.hand.moveToExhaustPile(c);
                        if (c.type == AbstractCard.CardType.ATTACK) {
                            AbstractCard q = AbstractDungeon.returnTrulyRandomCardInCombat(AbstractCard.CardType.SKILL).makeCopy();// 32
                            if (costify)
                                q.modifyCostForCombat(-1);
                            this.addToBot(new MakeTempCardInHandAction(q, true));// 34
                        } else {
                            AbstractCard q = AbstractDungeon.returnTrulyRandomCardInCombat(AbstractCard.CardType.ATTACK).makeCopy();// 32
                            if (costify)
                                q.modifyCostForCombat(-1);
                            this.addToBot(new MakeTempCardInHandAction(q, true));// 34
                        }
                        this.isDone = true;// 64
                        return;// 65
                    }
            }

            this.p.hand.group.removeAll(this.cannotUpgrade);// 72
            if (this.p.hand.group.size() > 1) {// 74
                AbstractDungeon.handCardSelectScreen.open(EXTENDED_DESCRIPTIONS[0], 1, false, false);// 75
                this.tickDuration();// 76
                return;// 77
            }

            if (this.p.hand.group.size() == 1) {// 78
                AbstractCard c = p.hand.getTopCard();
                p.hand.moveToExhaustPile(c);
                if (c.type == AbstractCard.CardType.ATTACK) {
                    AbstractCard q = AbstractDungeon.returnTrulyRandomCardInCombat(AbstractCard.CardType.SKILL).makeCopy();// 32
                    if (costify)
                        q.modifyCostForCombat(-1);
                    this.addToBot(new MakeTempCardInHandAction(q, true));// 34
                } else {
                    AbstractCard q = AbstractDungeon.returnTrulyRandomCardInCombat(AbstractCard.CardType.ATTACK).makeCopy();// 32
                    if (costify)
                        q.modifyCostForCombat(-1);
                    this.addToBot(new MakeTempCardInHandAction(q, true));// 34
                }
                this.returnCards();// 81
                this.isDone = true;// 82
            }
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {// 87
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                p.hand.moveToExhaustPile(c);
                if (c.type == AbstractCard.CardType.ATTACK) {
                    AbstractCard q = AbstractDungeon.returnTrulyRandomCardInCombat(AbstractCard.CardType.SKILL).makeCopy();// 32
                    if (costify)
                        q.modifyCostForCombat(-1);
                    this.addToBot(new MakeTempCardInHandAction(q, true));// 34
                } else {
                    AbstractCard q = AbstractDungeon.returnTrulyRandomCardInCombat(AbstractCard.CardType.ATTACK).makeCopy();// 32
                    if (costify)
                        q.modifyCostForCombat(-1);
                    this.addToBot(new MakeTempCardInHandAction(q, true));// 34
                }
            }

            this.returnCards();// 95
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;// 96
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();// 97
            this.isDone = true;// 98
        }
        this.tickDuration();// 101
    }// 102

    private void returnCards() {
        for (AbstractCard c : this.cannotUpgrade) {
            this.p.hand.addToTop(c);// 106
        }
        this.p.hand.refreshHandLayout();// 108
    }// 109
}
