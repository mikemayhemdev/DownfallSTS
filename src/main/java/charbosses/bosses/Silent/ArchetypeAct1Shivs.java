package charbosses.bosses.Silent;

import charbosses.bosses.Ironclad.ArchetypeBaseIronclad;
import charbosses.cards.curses.EnDoubt;
import charbosses.cards.green.*;
import charbosses.cards.red.*;
import charbosses.cards.status.EnWound;
import charbosses.relics.*;
import charbosses.relics.EventRelics.CBR_Cleric;
import charbosses.relics.EventRelics.CBR_UpgradeShrine;
import charbosses.relics.EventRelics.CBR_WeMeetAgain;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class ArchetypeAct1Shivs extends ArchetypeBaseIronclad {

    public ArchetypeAct1Shivs() {
        super("SI_SHIV_ARCHETYPE", "Shivs");
    }

    public void initialize() {
        //Tuning Recommendation per Act (CARDS): 1 Card Removal, 2 Upgrades, ~6-8 cards added to deck
        //Tuning Recommendation per Act (RELICS): 2 relics and an Event relic (simulate what the Event did)
        //Tuning Recommendation for Act 2-3: At least 1 Rare and 1 Boss Relic in addition to above 2
        //Make the total cards always divisible by 3 - Shuffle should not occur on a partial hand

        //STARTER DECK - 5 Strikes, 5 Defends, 1 Neutralize, 1 Survivor

        //2 Strike removed

        //8 Cards Added, 3 Upgrades:
        //Cloak and Dagger+
        //Accuracy
        //Blade Dance
        //Infinite Blades+
        //Flying Knee+
        //Sucker Punch
        //Dash

        //ENDING DECK - 3 Strikes, 5 Defends, 1 Neutralize, 1 Survivor, 1 Cloak and Dagger+, 1 Accuracy, 1 Blade Dance, 1 Infinite Blades, 1 Flying Knee+, 1 Sucker Punch, 1 Dash


        /////   RELICS   /////

        addRelic(new CBR_NeowsBlessing());
        addRelic(new CBR_BagOfPreparation());
        addRelic(new CBR_Lantern());
       // addRelic(new CBR_DreamCatcher());
       // addRelic(new CBR_Cleric()); // Cleric to remove +1 Strike
       // addRelic(new CBR_UpgradeShrine()); // To upgrade Infinite Blades
       // addRelic(new CBR_WeMeetAgain());

        /////   CARDS   /////
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;//Turn 1











        //INFINITE LOOP







    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;
        if (looped) {
            switch (turn) {
                case 0:
                    addToList(cardsList, new EnNeutralize());
                    addToList(cardsList, new EnBladeDance(), extraUpgrades);
                    addToList(cardsList, new EnSurvivor(), extraUpgrades);
                    break;
                case 1:
                    addToList(cardsList, new EnDash());
                    addToList(cardsList, new EnStrikeGreen());  //Not played here
                    addToList(cardsList, new EnDefendGreen());  //Not played here
                    break;
                case 2:
                    addToList(cardsList, new EnFlyingKnee(), true);
                    addToList(cardsList, new EnCloakAndDagger(), true);
                    addToList(cardsList, new EnDefendGreen());  //Not played here
                    break;
                case 3:
                    addToList(cardsList, new EnSuckerPunch());
                    addToList(cardsList, new EnStrikeGreen(), extraUpgrades);
                    addToList(cardsList, new EnDefendGreen());
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    //Turn 1
                    addToList(cardsList, new EnDash());
                    addToList(cardsList, new EnNeutralize());
                    addToList(cardsList, new EnAccuracy());  //Removed
                    break;
                case 1:
                    //Turn 2
                    addToList(cardsList, new EnFlyingKnee(), true);
                    addToList(cardsList, new EnCloakAndDagger(), true);
                    addToList(cardsList, new EnDefendGreen());  //Not played here
                    break;
                case 2:
                    //Turn 3
                    addToList(cardsList, new EnStrikeGreen());
                    addToList(cardsList, new EnDefendGreen());
                    addToList(cardsList, new EnSurvivor(), extraUpgrades);
                    break;
                case 3:
                    //Turn 4
                    addToList(cardsList, new EnBladeDance(), extraUpgrades);
                    addToList(cardsList, new EnSuckerPunch());
                    addToList(cardsList, new EnStrikeGreen(), extraUpgrades);  //Not played here
                    break;
                case 4:
                    //Turn 5
                    addToList(cardsList, new EnInfiniteBlades());  //Removed
                    addToList(cardsList, new EnAfterImage());  //Removed
                    addToList(cardsList, new EnDefendGreen());  //Not played here
                    break;
            }
        }
        turn++;
        if (turn > 4 && !looped) looped = true;
        else if (turn > 3 && looped) {
            turn = 0;
        }
        return cardsList;
    }


    @Override
    public void initializeBonusRelic() {
        addRelic(new CBR_Vajra());
    }
}