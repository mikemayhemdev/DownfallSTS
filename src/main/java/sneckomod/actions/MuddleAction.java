package sneckomod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;
import slimebound.SlimeboundMod;
import sneckomod.SneckoMod;
import sneckomod.cards.Cower;
import sneckomod.cards.RiskySword;
import sneckomod.powers.BlunderGuardPower;
import sneckomod.powers.MudshieldPower;
import sneckomod.relics.CleanMud;
import sneckomod.relics.CrystallizedMud;
import sneckomod.relics.LoadedDie;

import java.util.ArrayList;

import static sneckomod.SneckoMod.MUDDLED;

public class MuddleAction extends AbstractGameAction {

    private AbstractCard card;
    private boolean no3;

    public MuddleAction(AbstractCard bruhCard, boolean modified) {
        card = bruhCard;
        this.no3 = modified;
    }

    public MuddleAction(AbstractCard bruhCard) {
        this(bruhCard, false);
    }

    public void update() {
        isDone = true;
        if ((card instanceof RiskySword)) {
            ((RiskySword) card).onMuddledSword();
        }
        if ((card instanceof Cower)) {
            ((Cower) card).onMuddledSword();
        }

        if (card.cost >= 0 && !card.hasTag(SneckoMod.SNEKPROOF)) {// 32
            if (AbstractDungeon.player.hasPower(MudshieldPower.POWER_ID)) {
                AbstractDungeon.player.getPower(MudshieldPower.POWER_ID).onSpecificTrigger();
            }

            LoadedDie loadedDieInstance = new LoadedDie();
            if (AbstractDungeon.player.hasRelic(LoadedDie.ID)) {
                addToBot(new GainBlockAction(AbstractDungeon.player, 1));
                loadedDieInstance.flash();
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
            //card.tags.add(MUDDLED); // THIS LITERALLY ONLY EXISTS FOR BABY SNECKO AND IS IRRELEVANT OTHERWISE
            card.freeToPlayOnce = false;// 39
        }
    }

}
