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

                    Collections.shuffle(myCardList, AbstractDungeon.cardRandomRng.random);
                    myCardList.sort((AbstractCard z1, AbstractCard z2) -> {
                        if (z1.cost < z2.cost)
                            return 1;
                        if (z1.cost > z2.cost)
                            return -1;
                        return 0;
                    });

                    card = myCardList.remove(0);

                } else {
                    card = myCardList.remove(AbstractDungeon.cardRandomRng.random(myCardList.size() - 1));

                }

                addToTop(new MuddleAction(card));
            }
        }
    }
}
