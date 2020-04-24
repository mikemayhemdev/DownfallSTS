package charbosses.bosses.Defect;

import charbosses.bosses.Ironclad.ArchetypeBaseIronclad;
import charbosses.cards.blue.*;
import charbosses.cards.curses.EnPain;
import charbosses.relics.*;
import charbosses.relics.EventRelics.CBR_Bandits;
import charbosses.relics.EventRelics.CBR_OminousForge;

public class ArchetypeAct2Claw extends ArchetypeBaseDefect {

    public ArchetypeAct2Claw() {
        super("DF_ARCHETYPE_", "Strike");
    }

    public void initialize() {
        //Tuning Recommendation per Act (CARDS): 1 Card Removal, 2 Upgrades, ~6-8 cards added to deck
        //Tuning Recommendation per Act (RELICS): 2 relics and an Event relic (simulate what the Event did)
        //Tuning Recommendation for Act 2-3: At least 1 Rare and 1 Boss Relic in addition to above 2
        //Make the total cards always divisible by 3 - Shuffle should not occur on a partial hand

        //STARTER DECK - 4 Strikes, 4 Defends, 1 Zap, 1 Dualcast

        //2 Strikes 2 Defend Removed

        //10 Cards Added, 6 Upgrades:  (1 Strike 1 Defend 1 Zap upgraded)
        //Boot Sequence
        //Claw+
        //Claw
        //Claw+
        //Leap
        //Rebound
        //Auto-Shields+
        //Charge Battery
        //Echo Form
        //Machine Learning

        /////   RELICS   /////

        addRelic(new CBR_NeowsBlessing());
        addRelic(new CBR_Kunai());
        addRelic(new CBR_WarPaint());
        addRelic(new CBR_OminousForge());
        addRelic(new CBR_WarpedTongs());
        addRelic(new CBR_Bandits());
        addRelic(new CBR_RedMask());
        addRelic(new CBR_DollysMirror("Claw+"));



        /////   CARDS   /////

        //Turn 1
        addToDeck(new EnBootSequence(), false);
        addToDeck(new EnClaw(), true);
        addToDeck(new EnStrikeBlue(), false);

        //Turn 2
        addToDeck(new EnLeap(), false);
        addToDeck(new EnPain(), false);
        addToDeck(new EnZap(), true);

        //Turn 3
        addToDeck(new EnRebound(), false);
        addToDeck(new EnClaw(), false);
        addToDeck(new EnDefendBlue(), false);

        //Turn 4
        //Top Deck Claw
        addToDeck(new EnStrikeBlue(), true);
        addToDeck(new EnDualcast(), false);

        //Turn 5
        addToDeck(new EnAutoShields(), true);
        addToDeck(new EnClaw(), true);
        addToDeck(new EnChargeBattery(), false);

        //Turn 6
        addToDeck(new EnEchoForm(), false);
        addToDeck(new EnDefendBlue(), true);
        addToDeck(new EnMachineLearning(), false);





    }

    @Override
    public void initializeBonusRelic() {
        addRelic(new CBR_IceCream());
    }
}