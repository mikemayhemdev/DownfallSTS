package hermit.actions;

import java.util.Iterator;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import java.util.Iterator;

public class EclipseAction extends AbstractGameAction {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private AbstractPlayer p;

    public EclipseAction(int amount) {
        this.p = AbstractDungeon.player;
        this.setValues(this.p, AbstractDungeon.player, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;
    }

    public void update() {
        AbstractCard card;
        if (this.duration == Settings.ACTION_DUR_MED) {
            CardGroup tmp = new CardGroup(CardGroupType.UNSPECIFIED);
            Iterator var5 = this.p.exhaustPile.group.iterator();

            while(var5.hasNext()) {
                card = (AbstractCard)var5.next();
                tmp.addToRandomSpot(card);
            }

            if (tmp.size() == 0) {
                this.isDone = true;
            } else if (tmp.size() == 1) {
                card = tmp.getTopCard();
                if (this.p.hand.size() == 10) {
                    this.p.exhaustPile.moveToDiscardPile(card);
                    this.p.exhaustPile.removeCard(card);
                    this.p.createHandIsFullDialog();
                } else {
                    card.unhover();
                    card.lighten(true);
                    card.setAngle(0.0F);
                    card.drawScale = 0.12F;
                    card.unfadeOut();
                    card.targetDrawScale = 0.75F;
                    card.current_x = CardGroup.DISCARD_PILE_X;
                    card.current_y = CardGroup.DISCARD_PILE_Y;
                    this.p.exhaustPile.moveToHand(card);
                    this.p.exhaustPile.removeCard(card);
                    card.setCostForTurn(0);
                    AbstractDungeon.player.hand.refreshHandLayout();
                    AbstractDungeon.player.hand.applyPowers();
                }

                this.isDone = true;
            } else if (tmp.size() <= this.amount) {
                for(int i = 0; i < tmp.size(); ++i) {
                    card = tmp.getNCardFromTop(i);
                    if (this.p.hand.size() == 10) {
                        this.p.exhaustPile.moveToDiscardPile(card);
                        this.p.createHandIsFullDialog();
                    } else {
                        card.unhover();
                        card.lighten(true);
                        card.setAngle(0.0F);
                        card.drawScale = 0.12F;
                        card.targetDrawScale = 0.75F;
                        card.unfadeOut();
                        card.current_x = CardGroup.DISCARD_PILE_X;
                        card.current_y = CardGroup.DISCARD_PILE_Y;
                        this.p.exhaustPile.moveToHand(card);
                        this.p.exhaustPile.removeCard(card);
                        card.setCostForTurn(0);
                        AbstractDungeon.player.hand.refreshHandLayout();
                        AbstractDungeon.player.hand.applyPowers();
                    }
                }

                this.isDone = true;
            } else {
                if (this.amount == 1) {
                    AbstractDungeon.gridSelectScreen.open(tmp, this.amount, TEXT[0], false);
                } else {
                    AbstractDungeon.gridSelectScreen.open(tmp, this.amount, TEXT[1], false);
                }

                this.tickDuration();
            }
        } else {
            if (AbstractDungeon.gridSelectScreen.selectedCards.size() != 0) {
                Iterator var1 = AbstractDungeon.gridSelectScreen.selectedCards.iterator();

                while(var1.hasNext()) {
                    card = (AbstractCard)var1.next();
                    card.unhover();
                    if (this.p.hand.size() == 10) {
                        this.p.exhaustPile.moveToDiscardPile(card);
                        this.p.exhaustPile.removeCard(card);
                        card.setCostForTurn(0);
                        card.unfadeOut();
                        this.p.createHandIsFullDialog();
                    } else {
                        this.p.exhaustPile.moveToHand(card);
                        this.p.exhaustPile.removeCard(card);
                        card.setCostForTurn(0);
                        card.unfadeOut();
                    }

                    this.p.hand.refreshHandLayout();
                    this.p.hand.applyPowers();
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                this.p.hand.refreshHandLayout();
            }

            this.tickDuration();
        }
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("AnyCardFromDeckToHandAction");
        TEXT = uiStrings.TEXT;
    }
}
