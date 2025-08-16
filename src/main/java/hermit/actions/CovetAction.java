//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package hermit.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import java.util.Iterator;

public class CovetAction extends AbstractGameAction {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private final AbstractPlayer p;
    public int extra_draw;

    public CovetAction(int extra) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
        this.extra_draw = extra;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.p.hand.isEmpty()) {
                this.addToTop(new DrawCardAction(this.extra_draw));
                this.isDone = true;
            } else if (this.p.hand.size() == 1) {
                this.addToTop(new DrawCardAction(this.extra_draw));
                if (this.p.hand.getBottomCard().color == AbstractCard.CardColor.CURSE) {
                    this.p.hand.moveToExhaustPile(this.p.hand.getBottomCard());
                } else {
                    this.p.hand.moveToDiscardPile(this.p.hand.getBottomCard());
                }

                this.tickDuration();
            } else {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false);
                this.tickDuration();
            }
        } else {
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                if (!AbstractDungeon.handCardSelectScreen.selectedCards.group.isEmpty()) {

                    this.addToTop(new DrawCardAction(this.extra_draw));

                    AbstractCard c;
                    for (Iterator var1 = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator(); var1.hasNext();) {
                        c = (AbstractCard) var1.next();
                        if (c.color == AbstractCard.CardColor.CURSE) {
                            this.p.hand.moveToExhaustPile(c);
                        } else {
                            this.p.hand.moveToDiscardPile(c);

                            c.triggerOnManualDiscard();
                            GameActionManager.incrementDiscard(false);
                        }
                    }
                }

                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
                AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            }

            this.tickDuration();
        }
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("DiscardAction");
        TEXT = uiStrings.TEXT;
    }
}
