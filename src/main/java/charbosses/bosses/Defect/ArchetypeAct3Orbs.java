package charbosses.bosses.Defect;

import charbosses.bosses.Ironclad.ArchetypeBaseIronclad;
import charbosses.cards.blue.*;
import charbosses.relics.*;
import charbosses.relics.EventRelics.CBR_Colosseum;
import charbosses.relics.EventRelics.CBR_ScrapOoze;
import charbosses.relics.EventRelics.CBR_WheelOfChange;

public class ArchetypeAct3Orbs extends ArchetypeBaseDefect {

    public ArchetypeAct3Orbs() {
        super("DF_ARCHETYPE_", "Strike");
    }

    public void initialize() {
        //Tuning Recommendation per Act (CARDS): 1 Card Removal, 2 Upgrades, ~6-8 cards added to deck
        //Tuning Recommendation per Act (RELICS): 2 relics and an Event relic (simulate what the Event did)
        //Tuning Recommendation for Act 2-3: At least 1 Rare and 1 Boss Relic in addition to above 2
        //Make the total cards always divisible by 3 - Shuffle should not occur on a partial hand

        //STARTER DECK - 4 Strikes, 4 Defends, 1 Zap, 1 Dualcast

        //2 Strikes 2 Defend Removed

        //15 Cards Added, 3 Upgrades:  (Zap+)  (fewer than usual upgrades due to Fusion Hammer)
        //Ball Lightning
        //Chill+
        //Cold Snap
        //Ball Lightning
        //Defragment
        //Capacitor
        //Leap
        //Core Surge
        //Barrage
        //Cold Snap
        //Blizzard
        //Glacier
        //Storm+
        //Consume
        //Biased Cognition


        /////   RELICS   /////

        addRelic(new CBR_NeowsBlessing());

        addRelic(new CBR_BirdFacedUrn());
        addRelic(new CBR_Anchor());

        addRelic(new CBR_ScrapOoze());
        addRelic(new CBR_Inserter());

        addRelic(new CBR_Colosseum());
        addRelic(new CBR_FossilizedHelix());
        addRelic(new CBR_SymbioticVirus());

        addRelic(new CBR_FusionHammer());


        /////   CARDS   /////

        //Turn 1
        addToDeck(new EnBallLightning(), false);
        addToDeck(new EnChill(), true);
        addToDeck(new EnStorm(), true);

        //Turn 2
        addToDeck(new EnColdSnap(), false);
        addToDeck(new EnForceField(), false);
        addToDeck(new EnCapacitor(), false);

        //Turn 3
        addToDeck(new EnBallLightning(), false);
        addToDeck(new EnColdSnap(), false);
        addToDeck(new EnStrikeBlue(), false);

        //Turn 4
        addToDeck(new EnDefragment(), false);
        addToDeck(new EnDefendBlue(), false);
        addToDeck(new EnBarrage(), false);

        //Turn 5
        addToDeck(new EnDualcast(), false);
        addToDeck(new EnGlacier(), false);
        addToDeck(new EnStrikeBlue(), false);

        //Turn 6
        addToDeck(new EnBlizzard(), false);
        addToDeck(new EnLeap(), false);
        addToDeck(new EnZap(), true);

        //Turn 7
        addToDeck(new EnCoreSurge(), false);
        addToDeck(new EnBiasedCognition(), false);
        addToDeck(new EnDefendBlue(), false);




    }


}