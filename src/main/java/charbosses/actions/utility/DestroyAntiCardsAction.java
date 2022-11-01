package charbosses.actions.utility;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class DestroyAntiCardsAction extends AbstractGameAction {

    private final String cardIDToKill;

    public DestroyAntiCardsAction(String ID) {
        cardIDToKill = ID;
    }

    @Override
    public void update() {
        ArrayList<AbstractCard> cardsToKill = new ArrayList<>();
        for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
            if (c.cardID == cardIDToKill) {
                cardsToKill.add(c);
            }
        }
        for (AbstractCard c : cardsToKill) {
            AbstractDungeon.player.exhaustPile.removeCard(c);
        }
        this.isDone = true;
    }
}
