package automaton.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class FireFromPileAction extends AbstractGameAction {
    private CardGroup g;

    public FireFromPileAction(CardGroup t, int amount) {
        this.g = t;
        this.amount = amount;
    }

    @Override
    public void update() {
        if (duration == startDuration) {
            AbstractDungeon.gridSelectScreen.open(g, amount, false, "Choose."); //TODO: Localize
            tickDuration();
        }
        else {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                    addToTop(new NewQueueCardAction(c, true));
                }
                isDone = true;
            }
        }
    }
}
