package automaton.actions;

import automaton.AutomatonMod;
import automaton.cards.AbstractBronzeCard;
import automaton.powers.OnFireSubscriber;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class FireFromPileAction extends AbstractGameAction {
    private CardGroup g;

    public FireFromPileAction(CardGroup t, int amount) { //TODO: Can only choose playable cards. End immediately and quote if all cards can't be played.
        this.g = t;
        this.amount = amount;
    }

    @Override
    public void update() {
        if (duration == startDuration) {
            if (g.isEmpty() || amount < 1) {
                isDone = true;
            }
            else {
                AbstractDungeon.gridSelectScreen.open(g, amount, false, "Choose."); //TODO: Localize
                tickDuration();
            }
        } else {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                    addToTop(new FireCardAction(c, g));
                }
                isDone = true;
            }
        }
    }
}
