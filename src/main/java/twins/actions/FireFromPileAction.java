package twins.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class FireFromPileAction extends AbstractGameAction {
    private CardGroup g;

    public FireFromPileAction(CardGroup t, int amount) {
        this.g = t;
        this.amount = amount;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if (this.duration == this.startDuration) {
            if (this.g.isEmpty() || this.amount <= 0) {
                this.isDone = true;
                return;
            }
            if (this.g.size() <= this.amount) {
                ArrayList<AbstractCard> cardsToMove = new ArrayList<>(this.g.group);
                for (AbstractCard c : cardsToMove) {
                    if (AbstractDungeon.player.hand.size() < 10) {
                        AbstractDungeon.player.hand.addToHand(c);
                        this.g.removeCard(c);
                    }
                    c.lighten(false);
                    c.applyPowers();
                }
                this.isDone = true;
                return;
            }
            if (this.amount == 1) {
                AbstractDungeon.gridSelectScreen.open(this.g, this.amount, "CHOOSE.", false);
            } else {
                AbstractDungeon.gridSelectScreen.open(this.g, this.amount, "CHOOSE " + this.amount + ", please!", false);
            }
            tickDuration();
            return;
        }
        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                if (AbstractDungeon.player.hand.size() < 10) {
                    AbstractDungeon.player.hand.addToHand(c);
                    this.g.removeCard(c);
                }
                c.lighten(false);
                c.unhover();
                c.applyPowers();
            }
            /*
            for (AbstractCard c : this.g.group) {
                c.unhover();
                c.target_x = CardGroup.DISCARD_PILE_X;
                c.target_y = 0.0F;
            }
            */ // What do we do here?
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            AbstractDungeon.player.hand.refreshHandLayout();
        }
        tickDuration();
        if (this.isDone)
            for (AbstractCard c : AbstractDungeon.player.hand.group)
                c.applyPowers();
    }
}
