package charbosses.bosses.Ironclad;

import charbosses.cards.anticards.ShieldSmash;
import charbosses.cards.curses.EnDoubt;
import charbosses.cards.curses.EnPain;
import charbosses.cards.curses.EnWrithe;
import charbosses.cards.red.*;
import charbosses.relics.*;
import charbosses.relics.EventRelics.*;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class ArchetypeAct3Block extends ArchetypeBaseIronclad {

    public ArchetypeAct3Block() {
        super("IC_BLOCK_ARCHETYPE", "Block");
    }

    public void initialize() {
        //Tuning Recommendation per Act (CARDS): 1 Card Removal, 2 Upgrades, ~6-8 cards added to deck for act 1 plus ~3 per act beyond it
        //Tuning Recommendation per Act (RELICS): 2 relics and an Event relic (simulate what the Event did)
        //Tuning Recommendation for Act 2-3: At least 1 Rare and 1 Boss Relic in addition to above 2
        //Make the total cards always divisible by 3 - Shuffle should not occur on a partial hand

        //STARTER DECK - 5 Strikes, 4 Defends, 1 Bash

        //4 Strike Removed

        //14 Cards Added, 5 Upgrades:
        //Barricade
        //Body Slam+
        //Body Slam+
        //Impervious
        //Iron Wave+
        //Iron Wave
        //Ghostly Armor+
        //Ghostly Armor
        //Metallicize
        //Metallicize+
        //Armaments
        //Juggernaut
        //FlameBarrier
        //PowerThrough




        /////   RELICS   /////

        addRelic(new CBR_NeowsBlessing());


        addRelic(new CBR_ThreadAndNeedle());
        addRelic(new CBR_HornCleat());
        addRelic(new CBR_MatchAndKeep());   //excuse for having both jug and barricade
        addRelic(new CBR_IncenseBurner());
        addRelic(new CBR_AncientWriting());   //upgraded all defends
        addRelic(new CBR_CursedKey());  //2 curse remaining in the deck
        addRelic(new CBR_Calipers());
        addRelic(new CBR_OrnamentalFan());
        addRelic(new CBR_TombOfRedMask());  // gain mask
        addRelic(new CBR_RedMask());  // gremlin mask


        /////   CARDS   /////


        //Turn 1
        addToDeck(new EnMetallicize(), false);
        addToDeck(new EnIronWave(), true);
        addToDeck(new EnWrithe(), true);

        //Turn 2
        addToDeck(new EnIronWave(), false);
        addToDeck(new EnArmaments(), false);
        addToDeck(new EnBodySlam(), true);

        //Turn 3
        addToDeck(new EnDefendRed(), true);
        addToDeck(new EnPowerThrough(), false);
        addToDeck(new EnJuggernaut(), false);

        //Turn 4
        addToDeck(new EnImpervious(), false);
        addToDeck(new EnBodySlam(), true);
        addToDeck(new EnMetallicize(), true);

        //Turn 5
        addToDeck(new EnDefendRed(), true);
        addToDeck(new EnFlameBarrier(), false);
        addToDeck(new EnBash(), false);

        //turn 6
        addToDeck(new EnGhostlyArmor(), true);
        addToDeck(new EnJuggernaut(), false);
        addToDeck(new EnDefendRed(), true);

        //Turn 7
        addToDeck(new EnBarricade(), false);
        addToDeck(new EnGhostlyArmor(), false);
        addToDeck(new EnDefendRed(), true);
    }

    @Override
    public AbstractCard anticard() {
        return new ShieldSmash();
    }

    @Override
    public void initializeBonusRelic() {
        addRelic(new CBR_SmoothStone());
    }
}