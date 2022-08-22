package automaton.actions;

import automaton.cards.FunctionCard;
import automaton.cards.Repair;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

import static java.lang.Math.min;

public class CleanseAction extends AbstractGameAction {
    private AbstractPlayer p;
    private ArrayList<AbstractCard> cannotExhume = new ArrayList<>();
    private ArrayList<AbstractCard> canExhume = new ArrayList<>();
    public static final String[] EXTENDED_DESCRIPTIONS = CardCrawlGame.languagePack.getCardStrings(Repair.ID).EXTENDED_DESCRIPTION;

    public CleanseAction(int amount) {
        this.p = AbstractDungeon.player;
        this.setValues(this.p, AbstractDungeon.player, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.p.drawPile.isEmpty()) {
                this.isDone = true;
            } else {
                for (AbstractCard c : AbstractDungeon.player.drawPile.group)
                    if (c.type != AbstractCard.CardType.STATUS) {
                        this.cannotExhume.add(c);
                    } else {
                        this.canExhume.add(c);
                    }
            }

            if (canExhume.size() == 0) {
                isDone = true;
                return;
            }

                p.drawPile.group.removeAll(cannotExhume);
                AbstractDungeon.gridSelectScreen.open(this.p.drawPile, min(amount, p.drawPile.size()), amount == 1 ? EXTENDED_DESCRIPTIONS[0] : (EXTENDED_DESCRIPTIONS[1] + amount + EXTENDED_DESCRIPTIONS[2]), false);
                this.tickDuration();

        }
        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                c.unfadeOut();
                this.p.drawPile.moveToExhaustPile(c);
                c.unhover();
                c.fadingOut = false;
            }

            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            isDone = true;
        }

        this.tickDuration();
    }
}
