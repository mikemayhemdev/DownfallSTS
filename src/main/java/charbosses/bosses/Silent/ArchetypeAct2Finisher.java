package charbosses.bosses.Silent;

import charbosses.bosses.Ironclad.ArchetypeBaseIronclad;
import charbosses.cards.curses.EnDoubt;
import charbosses.cards.green.*;
import charbosses.relics.*;
import charbosses.relics.EventRelics.CBR_Serpent;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ArchetypeAct2Finisher extends ArchetypeBaseIronclad {

    public ArchetypeAct2Finisher() {
        super("SI_SPAM_ARCHETYPE", "Combo");
    }

    public void initialize() {
        //Tuning Recommendation per Act (CARDS): 1 Card Removal, 2 Upgrades, ~6-8 cards added to deck
        //Tuning Recommendation per Act (RELICS): 2 relics and an Event relic (simulate what the Event did)
        //Tuning Recommendation for Act 2-3: At least 1 Rare and 1 Boss Relic in addition to above 2
        //Make the total cards always divisible by 3 - Shuffle should not occur on a partial hand

        //STARTER DECK - 5 Strikes, 5 Defends, 1 Neutralize, 1 Survivor

        //4 Strike removed

        /*ENDING DECK -
        Strike //
        Defend//
        Defend//
        Defend//
        Defend+//
        Neutralize+ //
        Survivor//
        Cloak And Dagger+//
        Finisher+//
        Blade Dance+
        Infinite Blades//
        Flying Knee//
        Slice//
        Predator//
        Endless Agony //
        Outmaneuver//
        Leg Sweep//
        Thousand Cuts//
        After Image//
        Doubt//
         */


        /////   RELICS   /////

        addRelic(new CBR_NeowsBlessing());

        //addRelic(new CBR_LetterOpener());
        addRelic(new CBR_Shuriken());
        addRelic(new CBR_Kunai());
        //addRelic(new CBR_Serpent()); // Gold used to buy cards
//        addRelic(new CBR_Ectoplasm());
        //addRelic(new CBR_Boot());
        addRelic(new CBR_HornCleat());
        addRelic(new CBR_Orichalcum());

        /////   CARDS   /////
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;

        //Turn 1
        addToDeck(new EnFlyingKnee(), extraUpgrades);
        addToDeck(new EnStrikeGreen());
        addToDeck(new EnSlice());  //Shuriken Proc

        //Turn 2
        addToDeck(new EnNeutralize(), true);
        addToDeck(new EnPredator());
        addToDeck(new EnStrikeGreen(), true);    //Shuriken Proc

        //Turn 3
        addToDeck(new EnSlice(), extraUpgrades);
        addToDeck(new EnInfiniteBlades());  //removed
        addToDeck(new EnDefendGreen());
        addToDeck(new EnDoubt());
        addToDeck(new EnEndlessAgony());  //removed    //Shuriken Proc

        //Turn 4
        addToDeck(new EnAfterImage());  //removed
        addToDeck(new EnCloakAndDagger(), true);
        addToDeck(new EnFinisher(), true);     //Shuriken Proc   x4 hits

        //Turn 5
        addToDeck(new EnThousandCuts());  //removed
        addToDeck(new EnLegSweep(), extraUpgrades);
        addToDeck(new EnEndlessAgony());  //removed     //Shuriken Proc

        //Turn 6
        addToDeck(new EnEndlessAgony());  //removed
        addToDeck(new EnDefendGreen());
        addToDeck(new EnBladeDance(), true);     //Shuriken Proc x2

        //INFINITE LOOP
        addToDeck(new EnNeutralize(), true);
        addToDeck(new EnPredator());
        addToDeck(new EnSlice());          //Shuriken Proc

        addToDeck(new EnFlyingKnee(), extraUpgrades);
        addToDeck(new EnCloakAndDagger(), true);
        addToDeck(new EnDoubt());
        addToDeck(new EnSlice(), extraUpgrades);
        addToDeck(new EnDefendGreen());   //Shuriken Proc x2

        addToDeck(new EnBladeDance(), true);     //Shuriken Proc
        addToDeck(new EnStrikeGreen());
        addToDeck(new EnFinisher(), true);   //x5, Shuriken Proc x2

        addToDeck(new EnLegSweep(), extraUpgrades);
        addToDeck(new EnDefendGreen());
        addToDeck(new EnStrikeGreen(), true);
    }

    @Override
    public void initializeBonusRelic() {
        addRelic(new CBR_CaptainsWheel());
    }
}