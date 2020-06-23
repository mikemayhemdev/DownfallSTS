package guardian.actions;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class CloneAction extends com.megacrit.cardcrawl.actions.AbstractGameAction {
    private static final float DURATION_PER_CARD = 0.25F;
    public static String[] TEXT;

    static {
        TEXT = CardCrawlGame.languagePack.getUIString("Guardian:UIOptions").TEXT;
    }

    private final AbstractPlayer p;

    public CloneAction(AbstractCreature source) {
        setValues(AbstractDungeon.player, source);
        this.actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.DRAW;
        this.duration = 0.25F;
        this.p = AbstractDungeon.player;
    }

    public void update() {
        if (this.duration == com.megacrit.cardcrawl.core.Settings.ACTION_DUR_FAST) {
            if (this.p.hand.group.size() == 0) {
                this.isDone = true;
                return;
            }

            if (this.p.hand.group.size() == 1) {
                AbstractDungeon.actionManager.addToTop(new PlaceActualCardIntoStasis(this.p.hand.getBottomCard().makeStatEquivalentCopy()));
                this.isDone = true;
                return;
            }

            AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false, false, false, false);
            tickDuration();
            return;
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                AbstractDungeon.actionManager.addToTop(new PlaceActualCardIntoStasis(c.makeStatEquivalentCopy()));
            }

            returnCards();

            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone = true;
        }

        tickDuration();
    }

    private void returnCards() {
        for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
            this.p.hand.addToTop(c);
        }
        this.p.hand.refreshHandLayout();
    }
}


