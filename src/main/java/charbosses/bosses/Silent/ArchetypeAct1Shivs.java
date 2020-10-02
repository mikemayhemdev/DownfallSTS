package charbosses.bosses.Silent;

import charbosses.bosses.Ironclad.ArchetypeBaseIronclad;
import charbosses.cards.green.*;
import charbosses.relics.*;
import charbosses.relics.EventRelics.CBR_Cleric;
import charbosses.relics.EventRelics.CBR_UpgradeShrine;
import charbosses.relics.EventRelics.CBR_WeMeetAgain;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

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

        //Turn 1
        addToDeck(new EnDash());
        addToDeck(new EnNeutralize());
        addToDeck(new EnAccuracy());  //Removed

        //Turn 2
        addToDeck(new EnFlyingKnee(), true);
        addToDeck(new EnCloakAndDagger(), true);
        addToDeck(new EnDefendGreen());  //Not played here

        //Turn 3
        addToDeck(new EnStrikeGreen());
        addToDeck(new EnDefendGreen());
        addToDeck(new EnSurvivor(), extraUpgrades);

        //Turn 4
        addToDeck(new EnBladeDance(), extraUpgrades);
        addToDeck(new EnSuckerPunch());
        addToDeck(new EnStrikeGreen(), extraUpgrades);  //Not played here

        //Turn 5
        addToDeck(new EnInfiniteBlades());  //Removed
        addToDeck(new EnAfterImage());  //Removed
        addToDeck(new EnDefendGreen());  //Not played here

        //INFINITE LOOP
        addToDeck(new EnNeutralize());
        addToDeck(new EnBladeDance(), extraUpgrades);
        addToDeck(new EnSurvivor(), extraUpgrades);

        addToDeck(new EnDash());
        addToDeck(new EnStrikeGreen());  //Not played here
        addToDeck(new EnDefendGreen());  //Not played here

        addToDeck(new EnFlyingKnee(), true);
        addToDeck(new EnCloakAndDagger(), true);
        addToDeck(new EnDefendGreen());  //Not played here

        addToDeck(new EnSuckerPunch());
        addToDeck(new EnStrikeGreen(), extraUpgrades);
        addToDeck(new EnDefendGreen());




    }

    @Override
    public void initializeBonusRelic() {
        addRelic(new CBR_Vajra());
    }
}