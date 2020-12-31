package sneckomod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.Collections;


public class MuddleRandomCardAction extends AbstractGameAction {

    private boolean onlyHighest = false;


    public MuddleRandomCardAction(int i) {
        this(i, false);
    }

    public MuddleRandomCardAction(int i, boolean highest) {
        amount = i;
        onlyHighest = highest;
    }


    public void update() {
        isDone = true;
        ArrayList<AbstractCard> myCardList = new ArrayList<>(AbstractDungeon.player.hand.group);


        for (int i = 0; i < this.amount; ++i) {// 101
            if (!myCardList.isEmpty()) {
                AbstractCard card = null;
                if (onlyHighest) {
                    myCardList.sort((AbstractCard z1, AbstractCard z2) -> Integer.compare(z2.costForTurn, z1.costForTurn));

                    card = myCardList.remove(0);

                } else {
                    card = myCardList.remove(AbstractDungeon.cardRandomRng.random(myCardList.size() - 1));

                }

                addToTop(new MuddleAction(card));
            }
        }
    }
}
