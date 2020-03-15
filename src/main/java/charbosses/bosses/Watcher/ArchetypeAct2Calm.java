package charbosses.bosses.Watcher;

import charbosses.bosses.Defect.ArchetypeBaseDefect;
import charbosses.cards.colorless.EnRitualDagger;
import charbosses.cards.purple.*;
import charbosses.relics.*;
import charbosses.relics.EventRelics.*;

public class ArchetypeAct2Calm extends ArchetypeBaseDefect {

    public ArchetypeAct2Calm() {
        super("DF_ARCHETYPE_STREAMLINE", "Streamline");
    }

    public void initialize() {
        //Tuning Recommendation per Act (CARDS): 1 Card Removal, 2 Upgrades, ~6-8 cards added to deck
        //Tuning Recommendation per Act (RELICS): 2 relics and an Event relic (simulate what the Event did)
        //Tuning Recommendation for Act 2-3: At least 1 Rare and 1 Boss Relic in addition to above 2
        //Make the total cards always divisible by 3 - Shuffle should not occur on a partial hand

        //STARTER DECK - 4 Strikes, 4 Defends, 1 Zap, 1 Dualcast

        //1 Strike 1 Defend Removed

        //7 Cards Added, 3 Upgrades:


        //Crush Joints - 8 damage vuln if skill last
        //Empty Body / Fist - Strike/Defend, Exit Stance
        //Flying Sleeves - 4 damage twice, retain
        //Halt - Gain block, extra if Wrath
        //Pressure Points
        //Prostrate - 2 mantra, 4 block
        //Protect - 12 block, retain
        //Sash Whip - 8 damage 1 weak if last was attack
        //stance enterers that retain
        //battle hymn - gain a smite each turn
        //deceive reality - 4 block, add safety
        //fasting
        //inidignation - enter wrath or apply 3 vuln if in wrath
        //like water - metallicize if in calm
        //mental fortress - gain block when changing stances
        //perseverance - gain 5 block, retain, grows block when retained
        //reach heaven - damage, shuffle through violence in
        //sands of time - big damage, lowers cost when retained
        //swivel - next attack played costs 0
        //talk to the hand - block generation
        //tantrum - 3x3 damage, enter wrath
        //Wallop - 9 damage, gain block equal to unblocked damage
        //wave of the hand - 1 weak whenever you gain block
        //windmill strike - big damage, gets bigger when retained
        //worship - gain 5 mantra
        //wreath of flame - next attack deal 5 more damage
        //alpha - beta - omega
        //brilliance - damage equal to mantra gained
        //devotion - 2 mantra per turn
        //establishment - retain costs less
        //ragnarok - 5x5 damage
        //wish - plated armor or 3 strength


        //Retain build, starts with Establishment, battle hymn at the end

        //Mantra build, devotion as main source

        //Calm build, alpha/beta/omega





        /////   RELICS   /////

        addRelic(new CBR_NeowsBlessing());
        addRelic(new CBR_SmoothStone());
        addRelic(new CBR_TeardropLocket());
        addRelic(new CBR_Duplicator());
        addRelic(new CBR_MercuryHourglass());
        addRelic(new CBR_ArtOfWar());
        addRelic(new CBR_Nest());
        //addRelic(new CBR_PhilosopherStone());

        /////   CARDS   /////

        //Turn 1
        addToDeck(new EnLikeWater(), false);
        addToDeck(new EnDefendPurple(), false);
        addToDeck(new EnWallop(), true);

        //Turn 2
        addToDeck(new EnDefendPurple(), false);
        addToDeck(new EnStrikePurple(), false);
        addToDeck(new EnRitualDagger(), true);

        //Turn 3
        addToDeck(new EnReachHeaven(), true);
        addToDeck(new EnWaveOfTheHand(), false);
        addToDeck(new EnStrikePurple(), false);

        //Turn 4
        addToDeck(new EnReachHeaven(), true);
        addToDeck(new EnCrushJoints(), false);
        addToDeck(new EnThroughViolence(), false);

        //Turn 5
        addToDeck(new EnLikeWater(), true);
        addToDeck(new EnDefendPurple(), true);
        addToDeck(new EnSashWhip(), false);

        //Turn 6
        addToDeck(new EnDevaForm(), false);
        addToDeck(new EnThroughViolence(), false);
        addToDeck(new EnRagnarok(), false);


    }


}