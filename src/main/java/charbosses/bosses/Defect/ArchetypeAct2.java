package charbosses.bosses.Defect;

import charbosses.bosses.Ironclad.ArchetypeBaseIronclad;
import charbosses.relics.CBR_NeowsBlessing;

public class ArchetypeAct2 extends ArchetypeBaseDefect {

    public ArchetypeAct2() {
        super("DF_ARCHETYPE_", "Strike");
    }

    public void initialize() {
        //Tuning Recommendation per Act (CARDS): 1 Card Removal, 2 Upgrades, ~6-8 cards added to deck
        //Tuning Recommendation per Act (RELICS): 2 relics and an Event relic (simulate what the Event did)
        //Tuning Recommendation for Act 2-3: At least 1 Rare and 1 Boss Relic in addition to above 2
        //Make the total cards always divisible by 3 - Shuffle should not occur on a partial hand

        //STARTER DECK - 5 Strikes, 4 Defends, 1 Bash

        //1 Strike 1 Defend Removed

        //6 Cards Added, 3 Upgrades:


        /////   RELICS   /////

        addRelic(new CBR_NeowsBlessing());


        /////   CARDS   /////

        //Turn 1

        //Turn 2

        //Turn 3

        //Turn 4

        //Turn 5

        //Turn 6




    }


}