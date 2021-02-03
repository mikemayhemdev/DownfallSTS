package charbosses.bosses.Silent.NewAge;

import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Silent.ArchetypeBaseSilent;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.colorless.EnShiv;
import charbosses.cards.curses.EnClumsy;
import charbosses.cards.curses.EnDecay;
import charbosses.cards.green.*;
import charbosses.powers.bossmechanicpowers.SilentShivTimeEaterPower;
import charbosses.relics.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class ArchetypeAct1ShivsNewAge extends ArchetypeBaseSilent {

    public ArchetypeAct1ShivsNewAge() {
        super("SI_SHIV_ARCHETYPE", "Shivs");

        maxHPModifier += 60;
        actNum = 1;
    }

    @Override
    public void addedPreBattle() {
        super.addedPreBattle();
        AbstractCreature p = AbstractCharBoss.boss;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new SilentShivTimeEaterPower(p)));
    }

    public void initialize() {

        /////   RELICS   /////

        addRelic(new CBR_NeowsBlessing());
        // addRelic(new CBR_BagOfPreparation());
        addRelic(new CBR_Lantern());
        //addRelic(new CBR_CaptainsWheel());
        addRelic(new CBR_OrnamentalFan());
        // addRelic(new CBR_DreamCatcher());
        // addRelic(new CBR_Cleric()); // Cleric to remove +1 Strike
        // addRelic(new CBR_UpgradeShrine()); // To upgrade Infinite Blades
        // addRelic(new CBR_WeMeetAgain());

    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;
        if (!looped) {
            switch (turn) {
                case 0:
                    //Turn 1
                    addToList(cardsList, new EnLegSweep());
                    addToList(cardsList, new EnSurvivor());
                    addToList(cardsList, new EnClumsy());  //Removed
                    turn++;
                    break;
                case 1:
                    //Turn 2
                    addToList(cardsList, new EnCloakAndDagger(), false);
                    addToList(cardsList, new EnDefendGreen());
                    addToList(cardsList, new EnDefendGreen());  //Not played here
                    turn++;
                    break;
                case 2:
                    //Turn 3
                    addToList(cardsList, new EnBladeDance(), extraUpgrades);
                    addToList(cardsList, new EnDefendGreen());
                    addToList(cardsList, new EnDecay());
                    turn++;
                    break;
                case 3:
                    //Turn 4
                    addToList(cardsList, new EnFootwork()); //Removed
                    addToList(cardsList, new EnInfiniteBlades()); //Removed
                    addToList(cardsList, new EnBurst());  //Not played here
                    turn = 0;
                    looped = true;
                    break;

            }
        } else {

            switch (turn) {
                case 0:
                    addToList(cardsList, new EnShiv());
                    addToList(cardsList, new EnBurst());
                    addToList(cardsList, new EnCloakAndDagger());
                    AbstractBossCard c = new EnCloakAndDagger();
                    c.cost = 0;
                    c.freeToPlayOnce = true;
                    c.modifyCostForCombat(-1);
                    addToList(cardsList, c, extraUpgrades);
                    addToList(cardsList, new EnSurvivor());
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new EnShiv());
                    addToList(cardsList, new EnLegSweep());
                    addToList(cardsList, new EnDecay());
                    addToList(cardsList, new EnStrikeGreen());  //Not played here
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new EnShiv());
                    addToList(cardsList, new EnBladeDance(), extraUpgrades);
                    addToList(cardsList, new EnDefendGreen());
                    addToList(cardsList, new EnDefendGreen());  //Not played here
                    turn = 0;
                    break;
            }
        }
        return cardsList;
    }


    @Override
    public void initializeBonusRelic() {
        addRelic(new CBR_Shuriken());
    }
}