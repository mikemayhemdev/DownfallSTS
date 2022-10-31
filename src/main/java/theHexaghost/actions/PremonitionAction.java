//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package theHexaghost.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theHexaghost.cards.NecessarySacrifice;
import theHexaghost.cards.Premonition;

import java.util.ArrayList;
import java.util.Iterator;

public class PremonitionAction extends AbstractGameAction {
    private AbstractPlayer p;
    public static final String[] EXTENDED_DESCRIPTIONS = CardCrawlGame.languagePack.getCardStrings(Premonition.ID).EXTENDED_DESCRIPTION;

    public PremonitionAction(boolean upgrade) {
        this.actionType = ActionType.CARD_MANIPULATION;// 22
        this.p = AbstractDungeon.player;// 23
        this.duration = Settings.ACTION_DUR_FAST;// 24
    }// 26

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {// 30
            if (this.p.hand.group.size() == 0) {// 53
                this.isDone = true;// 54
                return;// 55
            }

            if (this.p.hand.group.size() == 1) {// 78
                AbstractCard c = p.hand.getTopCard();
                c.exhaust = true;
                p.hand.group.remove(c);
                AbstractDungeon.getCurrRoom().souls.remove(c);
                this.addToBot(new NewQueueCardAction(c, true, false, true));
                this.isDone = true;// 82
            }

            if (this.p.hand.group.size() > 1) {// 74
                AbstractDungeon.handCardSelectScreen.open(EXTENDED_DESCRIPTIONS[0], 1, false, false);// 75
                this.tickDuration();// 76
                return;// 77
            }
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {// 87
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                c.exhaust = true;
                p.hand.group.remove(c);
                AbstractDungeon.getCurrRoom().souls.remove(c);
                this.addToBot(new NewQueueCardAction(c, true, false, true));
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;// 96
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();// 97
            this.isDone = true;// 98
        }
        this.tickDuration();// 101
    }// 102
}
