package theHexaghost.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theHexaghost.cards.RecurringNightmare;

import java.util.ArrayList;

import static java.lang.Math.min;

public class RecurringNightmareAction extends AbstractGameAction {
    private AbstractPlayer p;
    private ArrayList<AbstractCard> cannotExhume = new ArrayList<>();
    private ArrayList<AbstractCard> canExhume = new ArrayList<>();
    public static final String[] EXTENDED_DESCRIPTIONS = CardCrawlGame.languagePack.getCardStrings(RecurringNightmare.ID).EXTENDED_DESCRIPTION;

    public RecurringNightmareAction(int amount) {
        this.p = AbstractDungeon.player;
        this.setValues(this.p, AbstractDungeon.player, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (AbstractDungeon.player.hand.size() == 10) {
                AbstractDungeon.player.createHandIsFullDialog();
                this.isDone = true;
            } else if (this.p.exhaustPile.isEmpty()) {
                this.isDone = true;
            } else {
                for (AbstractCard c : AbstractDungeon.player.exhaustPile.group)
                    if (!c.isEthereal) {
                        this.cannotExhume.add(c);
                    } else {
                        this.canExhume.add(c);
                    }
            }

            if (canExhume.size() == 0) {
                isDone = true;
                return;
            }

            // if the amount to exhume is greater than or equal to the amount of exhumable, exhume them all
            if (canExhume.size() <= amount) {
                for (AbstractCard c : canExhume) {
                    c.unfadeOut();
                    this.p.hand.addToHand(c);
                    this.p.exhaustPile.removeCard(c);
                    c.unhover();
                    c.fadingOut = false;
                    this.isDone = true;
//                    break;
                }
                return;
            } else {
                p.exhaustPile.group.removeAll(cannotExhume);
                AbstractDungeon.gridSelectScreen.open(this.p.exhaustPile, min(amount, p.exhaustPile.size()), amount == 1 ? EXTENDED_DESCRIPTIONS[0] : (EXTENDED_DESCRIPTIONS[1] + amount + EXTENDED_DESCRIPTIONS[2]), false);
                this.tickDuration();
            }
        }
        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                c.unfadeOut();
                this.p.hand.addToHand(c);
                this.p.exhaustPile.removeCard(c);
                c.unhover();
                c.fadingOut = false;
            }

            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            this.p.hand.refreshHandLayout();
            this.p.exhaustPile.group.addAll(cannotExhume);
            isDone = true;
        }

        this.tickDuration();
    }
}
