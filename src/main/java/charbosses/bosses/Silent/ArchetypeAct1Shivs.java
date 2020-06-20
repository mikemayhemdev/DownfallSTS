package charbosses.bosses.Silent;

import charbosses.bosses.Ironclad.ArchetypeBaseIronclad;
import charbosses.cards.green.*;
import charbosses.relics.*;
import charbosses.relics.EventRelics.CBR_Cleric;
import charbosses.relics.EventRelics.CBR_UpgradeShrine;
import charbosses.relics.EventRelics.CBR_WeMeetAgain;

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
        addRelic(new CBR_DreamCatcher());
        addRelic(new CBR_Lantern());
        addRelic(new CBR_Cleric()); // Cleric to remove +1 Strike
        addRelic(new CBR_UpgradeShrine()); // To upgrade Infinite Blades
        addRelic(new CBR_WeMeetAgain());
        addRelic(new CBR_BagOfPreparation());

        /////   CARDS   /////

        //Turn 1

        addToDeck(new EnStrikeGreen());
        addToDeck(new EnDefendGreen());
        addToDeck(new EnDefendGreen());
        addToDeck(new EnDash());
        addToDeck(new EnNeutralize());

        //Turn 2
        addToDeck(new EnFlyingKnee(), true);
        addToDeck(new EnDefendGreen());
        addToDeck(new EnAccuracy());

        //Turn 3
        addToDeck(new EnCloakAndDagger(), true);
        addToDeck(new EnStrikeGreen());
        addToDeck(new EnDefendGreen());

        //Turn 4
        addToDeck(new EnStrikeGreen());
        addToDeck(new EnBladeDance());
        addToDeck(new EnSuckerPunch());

        //Turn 5
        addToDeck(new EnInfiniteBlades(), false);
        addToDeck(new EnDefendGreen());
        addToDeck(new EnAccuracy());

    }

    @Override
    public void initializeBonusRelic() {
        addRelic(new CBR_Vajra());
    }
}