package sneckomod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import downfall.downfallMod;
import sneckomod.powers.MudshieldPower;
import sneckomod.relics.CleanMud;
import sneckomod.relics.CrystallizedMud;

import java.util.ArrayList;

public class MuddleAction extends AbstractGameAction {

    private final AbstractCard card;
    private final boolean no3;

    public MuddleAction(AbstractCard bruhCard, boolean modified) {
        card = bruhCard;
        this.no3 = modified;
    }

    public MuddleAction(AbstractCard bruhCard) {
        this(bruhCard, false);
    }

    public void update() {
        isDone = true;
        if (card.cost >= 0 && !card.hasTag(downfallMod.SNEKPROOF)) {// 32
            if (AbstractDungeon.player.hasPower(MudshieldPower.POWER_ID)) {
                AbstractDungeon.player.getPower(MudshieldPower.POWER_ID).onSpecificTrigger();
            }
            card.superFlash();
            ArrayList<Integer> numList = new ArrayList<>();
            if (!AbstractDungeon.player.hasRelic(CrystallizedMud.ID)) {
                if (card.costForTurn != 0) numList.add(0);
            }
            if (card.costForTurn != 1) numList.add(1);
            if (card.costForTurn != 2) numList.add(2);
            if (!AbstractDungeon.player.hasRelic(CleanMud.ID)) {
                if (!this.no3) {
                    if (card.costForTurn != 3) numList.add(3);
                }
            }
            int newCost = numList.get(AbstractDungeon.cardRandomRng.random(numList.size() - 1));// 33
            //SlimeboundMod.logger.info("muddling " + card.name + " base " + card.cost + " new " + newCost);
            if (card.costForTurn != newCost) {// 34
                card.setCostForTurn(newCost);
            }

            card.freeToPlayOnce = false;// 39
        }
    }
}
