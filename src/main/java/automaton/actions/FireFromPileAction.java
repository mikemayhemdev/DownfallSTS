package automaton.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class FireFromPileAction extends AbstractGameAction {
    private CardGroup g;

    public FireFromPileAction(CardGroup t, int amount) { //TODO: Can only choose playable cards. End immediately and quote if all cards can't be played.
        this.g = t;
        this.amount = amount;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if (duration == startDuration) {
            if (g.isEmpty() || amount < 1) {
                isDone = true;
            } else {
                for (AbstractCard q : g.group) {
                    q.applyPowers(); // hopefully this will make things display right
                    q.stopGlowing();
                }
                AbstractDungeon.gridSelectScreen.open(g, amount, true, "Choose."); //TODO: Localize
                tickDuration();
            }
        } else {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                    addToTop(new FireCardAction(c, g));
                }
                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                isDone = true;
            }
        }
    }
}
