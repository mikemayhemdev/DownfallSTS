package sneckomod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;
import sneckomod.SneckoMod;
import sneckomod.powers.MudshieldPower;

import java.util.ArrayList;
import java.util.Collections;

public class MuddleRandomCardAction extends AbstractGameAction {
    public int bruh = 0;

    public MuddleRandomCardAction(int i) {
        bruh = i;
    }

    public void update() {
        isDone = true;
        ArrayList<AbstractCard> myList = new ArrayList<>(AbstractDungeon.player.hand.group);
        Collections.shuffle(myList, AbstractDungeon.cardRandomRng.random);
        for (int i = 0; i < bruh; i++) {
            if (!myList.isEmpty()) {
                if (AbstractDungeon.player.hasPower(MudshieldPower.POWER_ID)) {
                    AbstractPower q = AbstractDungeon.player.getPower(MudshieldPower.POWER_ID);
                    q.flash();
                    addToBot(new ApplyPowerAction(q.owner, q.owner, new NextTurnBlockPower(q.owner, q.amount)));
                }
                AbstractCard card = myList.remove(0);
                card.superFlash();
                if (card.cost >= 0 && !card.hasTag(SneckoMod.SNEKPROOF)) {// 32
                    int newCost = AbstractDungeon.cardRandomRng.random(3);// 33
                    if (card.cost != newCost) {// 34
                        card.cost = newCost;// 35
                        card.costForTurn = card.cost;// 36
                        card.isCostModified = true;// 37
                    }

                    card.freeToPlayOnce = false;// 39
                }
            }
        }
    }
}
