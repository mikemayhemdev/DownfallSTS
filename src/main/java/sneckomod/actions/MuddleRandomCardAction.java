package sneckomod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;


public class MuddleRandomCardAction extends AbstractGameAction {

    private boolean onlyHighest = false;

    private boolean modifiedCost = false;

    public MuddleRandomCardAction(int i) {
        this(i, false);
    }

    public MuddleRandomCardAction(int i, boolean highest) {
        amount = i;
        onlyHighest = highest;
    }

    public MuddleRandomCardAction(int i, boolean highest, boolean no3s) {
        amount = i;
        onlyHighest = highest;
        modifiedCost = no3s;
    }


    public void update() {
        isDone = true;
        ArrayList<AbstractCard> myCardList = new ArrayList<>(AbstractDungeon.player.hand.group);


        for (int i = 0; i < this.amount; ++i) {// 101
            if (!myCardList.isEmpty()) {
                AbstractCard card = null;
                if (onlyHighest) {
                    int highestCostFound = 0;
                    for (AbstractCard r : myCardList) {
                        if (r.costForTurn > highestCostFound) {
                            highestCostFound = r.costForTurn;
                        }
                    }
                    ArrayList<AbstractCard> highestCostCards = new ArrayList<>();
                    for (AbstractCard r : myCardList) {
                        if (r.costForTurn == highestCostFound) {
                            highestCostCards.add(r);
                        }
                    }
                    if (!highestCostCards.isEmpty()) {
                        card = highestCostCards.get(AbstractDungeon.cardRandomRng.random(highestCostCards.size() - 1));
                        myCardList.remove(card);
                    }
                } else {
                    card = myCardList.remove(AbstractDungeon.cardRandomRng.random(myCardList.size() - 1));
                }

                if (card != null)
                    addToTop(new MuddleAction(card, modifiedCost));
            }
        }
    }
}
