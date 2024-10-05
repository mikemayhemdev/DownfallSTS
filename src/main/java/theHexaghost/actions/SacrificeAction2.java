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

public class SacrificeAction2 extends AbstractGameAction {
    public boolean upgrade_the_new_card;
    private AbstractPlayer p;
    public static final String[] EXTENDED_DESCRIPTIONS = CardCrawlGame.languagePack.getCardStrings(NecessarySacrifice.ID).EXTENDED_DESCRIPTION;

    public SacrificeAction2(boolean upgrade) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
        upgrade_the_new_card = upgrade;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
//            if (this.p.hand.group.size() > 2) {
            if (this.p.hand.group.size() == 0) {
                this.isDone = true;
                return;
            }else if (this.p.hand.group.size() == 1) {
                AbstractCard c = this.p.hand.getTopCard();
                p.hand.moveToExhaustPile(c);
                if (c.type == AbstractCard.CardType.ATTACK) {
                    AbstractCard q = AbstractDungeon.returnTrulyRandomCardInCombat(AbstractCard.CardType.SKILL).makeCopy();
                    if (upgrade_the_new_card) {
                        q.upgrade();
                    }
                    this.addToBot(new MakeTempCardInHandAction(q, true));
                } else if (c.type == AbstractCard.CardType.SKILL) {
                    AbstractCard q = AbstractDungeon.returnTrulyRandomCardInCombat(AbstractCard.CardType.ATTACK).makeCopy();
                    if (upgrade_the_new_card) {
                        q.upgrade();
                    }
                    this.addToBot(new MakeTempCardInHandAction(q, true));
                }
                this.isDone = true;
                return;
            } else if (this.p.hand.group.size() == 2) {
                for(int i = 0; i < 2; i++){
                    AbstractCard c = this.p.hand.getTopCard();
                    p.hand.moveToExhaustPile(c);
                    if (c.type == AbstractCard.CardType.ATTACK) {
                        AbstractCard q = AbstractDungeon.returnTrulyRandomCardInCombat(AbstractCard.CardType.SKILL).makeCopy();
                        if (upgrade_the_new_card) {
                            q.upgrade();
                        }
                        this.addToBot(new MakeTempCardInHandAction(q, true));
                    } else if (c.type == AbstractCard.CardType.SKILL) {
                        AbstractCard q = AbstractDungeon.returnTrulyRandomCardInCombat(AbstractCard.CardType.ATTACK).makeCopy();
                        if (upgrade_the_new_card) {
                            q.upgrade();
                        }
                        this.addToBot(new MakeTempCardInHandAction(q, true));
                    }
                }
                this.isDone = true;
                return;
            } else {
                AbstractDungeon.handCardSelectScreen.open(EXTENDED_DESCRIPTIONS[0], 2, false, false);
                this.tickDuration();
                return;
            }
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                p.hand.moveToExhaustPile(c);
                if (c.type == AbstractCard.CardType.ATTACK) {
                    AbstractCard q = AbstractDungeon.returnTrulyRandomCardInCombat(AbstractCard.CardType.SKILL).makeCopy();
                    if (upgrade_the_new_card) {
                        q.upgrade();
                    }
                    this.addToBot(new MakeTempCardInHandAction(q, true));
                } else if (c.type == AbstractCard.CardType.SKILL) {
                    AbstractCard q = AbstractDungeon.returnTrulyRandomCardInCombat(AbstractCard.CardType.ATTACK).makeCopy();
                    if (upgrade_the_new_card) {
                        q.upgrade();
                    }
                    this.addToBot(new MakeTempCardInHandAction(q, true));
                }
            }

            this.p.hand.refreshHandLayout();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone = true;
        }
        this.tickDuration();

    }

}
