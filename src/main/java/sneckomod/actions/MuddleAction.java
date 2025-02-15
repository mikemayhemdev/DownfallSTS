package sneckomod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;
import gremlin.relics.WizardHat;
import slimebound.SlimeboundMod;
import sneckomod.SneckoMod;
import sneckomod.cards.RiskySword;
import sneckomod.powers.BlunderGuardPower;
import sneckomod.powers.MudshieldPower;
import sneckomod.relics.CleanMud;
import sneckomod.relics.CrystallizedMud;
import sneckomod.relics.LoadedDie;

import java.util.ArrayList;

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
        if (card instanceof RiskySword) {
            // Cast the card to RiskySword and call the onMuddled method
            ((RiskySword) card).onMuddledSword();
        }
        if (card.cost >= 0 && !card.hasTag(SneckoMod.SNEKPROOF)) {// 32
            if (AbstractDungeon.player.hasPower(MudshieldPower.POWER_ID)) {
                AbstractDungeon.player.getPower(MudshieldPower.POWER_ID).onSpecificTrigger();
            }
            card.superFlash();
            ArrayList<Integer> numList = new ArrayList<>();
           // if (!AbstractDungeon.player.hasRelic(CrystallizedMud.ID)) {
                if (card.costForTurn != 0) numList.add(0);
           // }
            if (card.costForTurn != 1) numList.add(1);
            if (card.costForTurn != 2) numList.add(2);
            //if (!AbstractDungeon.player.hasRelic(CleanMud.ID)) {
                if (!this.no3) {
                    if (card.costForTurn != 3) numList.add(3);
                }
            //}
            int newCost = numList.get(AbstractDungeon.cardRandomRng.random(numList.size() - 1));// 33
            int truecost = newCost;

            System.out.println("DEBUG: Cost: " + newCost);

            if ((
                    ((newCost == 3) && AbstractDungeon.player.hasRelic(CleanMud.ID))
                            || ((newCost == 0) && (AbstractDungeon.player.hasRelic(CrystallizedMud.ID)))
            )) {

                System.out.println("DEBUG: Cost is 0 or 3: " + newCost);
                CleanMud cleanMudInstance = new CleanMud();
                if (AbstractDungeon.player.hasRelic(CleanMud.ID) && (newCost == 3)) {
                    this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, cleanMudInstance));
                    AbstractDungeon.player.getRelic(CleanMud.ID).onTrigger();
                }

                CrystallizedMud crystallizedMudInstance = new CrystallizedMud();
                if (AbstractDungeon.player.hasRelic(CrystallizedMud.ID) && (newCost == 0)) {
                    this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, crystallizedMudInstance));
                    AbstractDungeon.player.getRelic(CrystallizedMud.ID).onTrigger();
                }

                ArrayList<Integer> numListMud = new ArrayList<>();
                if (!AbstractDungeon.player.hasRelic(CrystallizedMud.ID)) {
                    if (card.costForTurn != 0) numListMud.add(0);
                }
                if (card.costForTurn != 1) numListMud.add(1);
                if (card.costForTurn != 2) numListMud.add(2);
                if (!AbstractDungeon.player.hasRelic(CleanMud.ID)) {
                    if (!this.no3) {
                        if (card.costForTurn != 3) numListMud.add(3);
                    }
                }

                int newCosts = numListMud.get(AbstractDungeon.cardRandomRng.random(numListMud.size() - 1));// 33
                truecost = newCosts;
                System.out.println("DEBUG: Modified (?) Cost: " + truecost);
            }


            System.out.println("DEBUG: Final Cost: " + truecost);

            if (card.costForTurn != truecost) {
                card.setCostForTurn(truecost);
            }

            if (AbstractDungeon.player.hasPower(BlunderGuardPower.POWER_ID)){
                if ((card.costForTurn == 3)) {
                    AbstractDungeon.player.getPower(BlunderGuardPower.POWER_ID).onSpecificTrigger();
                }
            }

            LoadedDie loadedDieInstance = new LoadedDie();
            if (AbstractDungeon.player.hasRelic(LoadedDie.ID)) {
                addToBot(new GainBlockAction(AbstractDungeon.player, 1));
                loadedDieInstance.flash();
                this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, loadedDieInstance));
                AbstractDungeon.player.getRelic(LoadedDie.ID).onTrigger();
            }

            card.freeToPlayOnce = false;// 39
        }
    }

}