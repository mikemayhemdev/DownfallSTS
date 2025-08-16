package sneckomod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
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

public class MuddleMarkedAction extends AbstractGameAction {

    private AbstractCard card;
    private boolean no3;

    public MuddleMarkedAction(AbstractCard bruhCard, boolean modified) {
        card = bruhCard;
        this.no3 = modified;
    }

    public MuddleMarkedAction(AbstractCard bruhCard) {
        this(bruhCard, false);
    }

    public void update() {
        isDone = true;

        if ((card instanceof RiskySword)) {
            ((RiskySword) card).onMuddledSword();
        }

        if (card.cost >= 0 && !card.hasTag(SneckoMod.SNEKPROOF)) {
            if (AbstractDungeon.player.hasPower(MudshieldPower.POWER_ID)) {
                AbstractDungeon.player.getPower(MudshieldPower.POWER_ID).onSpecificTrigger();
            }

            if (AbstractDungeon.player.hasRelic(LoadedDie.ID)) {
                AbstractDungeon.player.getRelic(LoadedDie.ID).onTrigger();
            }

            card.superFlash();
            ArrayList<Integer> numList = new ArrayList<>();

            if (!AbstractDungeon.player.hasRelic(CrystallizedMud.ID)) {
                numList.add(0);
            }

            if (card.costForTurn > 1) numList.add(1);
            if (card.costForTurn > 2) numList.add(2);
            if (card.costForTurn > 3 && !AbstractDungeon.player.hasRelic(CleanMud.ID) && !this.no3) numList.add(3);

            if (numList.isEmpty()) {
                numList.add(1);
            }

            int newCost = numList.get(AbstractDungeon.cardRandomRng.random(numList.size() - 1));

            if (((newCost == 3) && AbstractDungeon.player.hasRelic(CleanMud.ID))){
                ArrayList<Integer> numListMud = new ArrayList<>();
                if (!AbstractDungeon.player.hasRelic(CrystallizedMud.ID) && (card.costForTurn != 1)){
                    numListMud.add(0);
                }

                CleanMud cleanMudInstance = new CleanMud();
                if (AbstractDungeon.player.hasRelic(CleanMud.ID) && (newCost == 3)) {
                    this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, cleanMudInstance));
                    //cleanMudInstance.flash();
                    AbstractDungeon.player.getRelic(CleanMud.ID).onTrigger();
                }

                if (card.costForTurn > 1) numListMud.add(1);
                if (card.costForTurn > 2) numListMud.add(2);
                newCost = numListMud.get(AbstractDungeon.cardRandomRng.random(numListMud.size() - 1));
            }

            int truecost = newCost;

            if (card.costForTurn != truecost) {
                card.setCostForTurn(truecost);
            }

            card.freeToPlayOnce = false;
        }
    }
}