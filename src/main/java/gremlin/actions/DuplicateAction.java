package gremlin.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import expansioncontent.actions.EchoACardAction;
import expansioncontent.expansionContentMod;

import java.util.ArrayList;

public class DuplicateAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("DualWieldAction");
    public static final String[] TEXT = uiStrings.TEXT;
    private final AbstractPlayer p;
    private final int dupeAmount;
    private final ArrayList<AbstractCard> cannotDuplicate = new ArrayList<>();

    public DuplicateAction(AbstractCreature source, int amount) {
        setValues(AbstractDungeon.player, source, amount);
        this.actionType = AbstractGameAction.ActionType.DRAW;
        this.duration = Settings.ACTION_DUR_FAST;
        this.p = AbstractDungeon.player;
        this.dupeAmount = amount;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.p.hand.group.isEmpty()) {
                this.isDone = true;
                return;
            }

            for (AbstractCard c : this.p.hand.group) {
                if (c.type != AbstractCard.CardType.ATTACK || c.hasTag(expansionContentMod.ECHO)) {
                    this.cannotDuplicate.add(c);
                }
            }

            if (this.p.hand.size() == this.cannotDuplicate.size()) {
                this.isDone = true;
                return;
            }

            if (this.p.hand.group.size() - this.cannotDuplicate.size() == 1) {
                for (AbstractCard c : this.p.hand.group) {
                    if (!this.cannotDuplicate.contains(c)) {
                        createEchoes(c);
                        this.isDone = true;
                        return;
                    }
                }
            }

            this.p.hand.group.removeAll(this.cannotDuplicate);

            AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false, false, false, false);

        } else if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                this.p.hand.addToTop(c);
                createEchoes(c);
            }
            returnCards();

            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone = true;
        }
        tickDuration();
    }

    private void returnCards() {
        for (AbstractCard c : this.cannotDuplicate) {
            this.p.hand.addToTop(c);
        }
        this.p.hand.refreshHandLayout();
    }

    private void createEchoes(AbstractCard card) {
        AbstractCard c = card.makeStatEquivalentCopy();

        if(c.cost >= 0)
            c.updateCost(-1);

        addToTop(new EchoACardAction(c, dupeAmount));
    }
}