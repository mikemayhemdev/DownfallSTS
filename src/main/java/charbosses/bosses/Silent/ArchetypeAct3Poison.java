package charbosses.bosses.Silent;

import charbosses.bosses.Ironclad.ArchetypeBaseIronclad;
import charbosses.cards.green.*;
import charbosses.relics.*;
import charbosses.relics.EventRelics.CBR_DesignerInSpire;
import charbosses.relics.EventRelics.CBR_OldBeggar;

public class ArchetypeAct3Poison extends ArchetypeBaseIronclad {

    public ArchetypeAct3Poison() {
        super("SI_POISON_ARCHETYPE", "Poison");
    }

    public void initialize() {
        //Tuning Recommendation per Act (CARDS): 1 Card Removal, 2 Upgrades, ~6-8 cards added to deck
        //Tuning Recommendation per Act (RELICS): 2 relics and an Event relic (simulate what the Event did)
        //Tuning Recommendation for Act 2-3: At least 1 Rare and 1 Boss Relic in addition to above 2
        //Make the total cards always divisible by 3 - Shuffle should not occur on a partial hand

        //STARTER DECK - 5 Strikes, 5 Defends, 1 Neutralize, 1 Survivor

        //5 Strike, 4 Defend removed

        /*ENDING DECK -
        Defend//
        Defend//
        Defend//
        Defend+//
        Dodge & Roll//
        Neutralize+
        Survivor+//
        Bane+//
        Bane//
        Deadly Poison//
        Deadly Poison+//
        Bouncing Flask//
        Catalyst+
        Crippling Cloud+
        Noxious Fumes+//
        Footwork+//
        Malaise+//
        Wraith Form+//
         */


        /////   RELICS   /////

        addRelic(new CBR_NeowsBlessing());
        addRelic(new CBR_Orichalcum());
        addRelic(new CBR_SmilingMask());
        addRelic(new CBR_DesignerInSpire());
        addRelic(new CBR_PhilosopherStone());
        addRelic(new CBR_ClockworkSouvenir());
        addRelic(new CBR_FrozenEgg());
        addRelic(new CBR_PenNib());
        addRelic(new CBR_OldBeggar());
        addRelic(new CBR_Pocketwatch());
        addRelic(new CBR_TungstenRod());


        /////   CARDS   /////

        //Turn 1
        addToDeck(new EnBane());
        addToDeck(new EnDeadlyPoison());
        addToDeck(new EnDodgeAndRoll());

        //Turn 2
        addToDeck(new EnNoxiousFumes(), true);
        addToDeck(new EnMalaise(), true);
        addToDeck(new EnDefendGreen());

        //Turn 3
        addToDeck(new EnFootwork(), true);
        addToDeck(new EnDeadlyPoison(), true);
        addToDeck(new EnSurvivor(), true);

        //Turn 4
        addToDeck(new EnBouncingFlask());
        addToDeck(new EnDefendGreen());
        addToDeck(new EnBane(), true);

        //Turn 5
        addToDeck(new EnWraithForm(), true);
        addToDeck(new EnCatalyst(), true);
        addToDeck(new EnDefendGreen(), true);

        //Turn 6
        addToDeck(new EnNeutralize(), true);
        addToDeck(new EnCripplingCloud());
        addToDeck(new EnDefendGreen());
    }


}