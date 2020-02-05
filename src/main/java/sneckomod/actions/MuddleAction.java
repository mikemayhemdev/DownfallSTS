package sneckomod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;
import sneckomod.SneckoMod;
import sneckomod.powers.MudshieldPower;
import sneckomod.relics.CleanMud;
import sneckomod.relics.CrystallizedMud;

import java.util.ArrayList;

public class MuddleAction extends AbstractGameAction {

    private AbstractCard card;

    public MuddleAction(AbstractCard bruhCard) {
        card = bruhCard;
    }

    public void update() {
        isDone = true;
        card.superFlash();
        if (AbstractDungeon.player.hasPower(MudshieldPower.POWER_ID)) {
            AbstractPower q = AbstractDungeon.player.getPower(MudshieldPower.POWER_ID);
            q.flash();
            addToBot(new ApplyPowerAction(q.owner, q.owner, new NextTurnBlockPower(q.owner, q.amount)));
        }
        if (card.cost >= 0 && !card.hasTag(SneckoMod.SNEKPROOF)) {// 32
            ArrayList<Integer> numList = new ArrayList<>();
            if (!AbstractDungeon.player.hasRelic(CrystallizedMud.ID)) {
                numList.add(0);
            }
            numList.add(1);
            numList.add(2);
            if (!AbstractDungeon.player.hasRelic(CleanMud.ID)) {
                numList.add(3);
            }
            int newCost = numList.get(AbstractDungeon.cardRandomRng.random(numList.size() - 1));// 33
            if (card.cost != newCost) {// 34
                card.cost = newCost;// 35
                card.costForTurn = card.cost;// 36
                card.isCostModified = true;// 37
            }

            card.freeToPlayOnce = false;// 39
        }
    }
}
