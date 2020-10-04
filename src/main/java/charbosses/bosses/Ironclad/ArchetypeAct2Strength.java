package charbosses.bosses.Ironclad;

import charbosses.cards.colorless.EnBite;
import charbosses.cards.colorless.EnJAX;
import charbosses.cards.curses.EnDoubt;
import charbosses.cards.curses.EnRegret;
import charbosses.cards.red.*;
import charbosses.relics.*;
import charbosses.relics.EventRelics.CBR_Augmenter;
import charbosses.relics.EventRelics.CBR_BigFish;
import charbosses.relics.EventRelics.CBR_Serpent;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ArchetypeAct2Strength extends ArchetypeBaseIronclad {

    public ArchetypeAct2Strength() {
        super("IC_STRENGTH_ARCHETYPE", "Strength");
    }

    public void initialize() {
        //Tuning Recommendation per Act (CARDS): 1 Card Removal, 2 Upgrades, ~6-8 cards added to deck for act 1 plus ~3 per act beyond it
        //Tuning Recommendation per Act (RELICS): 2 relics and an Event relic (simulate what the Event did)
        //Tuning Recommendation for Act 2-3: At least 1 Rare and 1 Boss Relic in addition to above 2
        //Make the total cards always divisible by 3 - Shuffle should not occur on a partial hand

        //STARTER DECK - 5 Strikes, 4 Defends, 1 Bash

        //2 Strike 2 Defend Removed

        //11 Cards Added, 4 Upgrades:
        //Demon Form
        //Seeing Red
        //Inflame+
        //Heavy Blade
        //Armaments+
        //Twin Strike
        //Iron Wave+
        //Iron Wave
        //Flame Barrier
        //Clothesline+

        /////   RELICS   /////

        addRelic(new CBR_NeowsBlessing());

        addRelic(new CBR_Girya(2));
        addRelic(new CBR_CaptainsWheel());
        addRelic(new CBR_RedSkull());
        addRelic(new CBR_Torii());
        addRelic(new CBR_Lantern());

        /////   CARDS   /////
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;

        //Turn 1
        addToDeck(new EnInflame(), true); // removed
        addToDeck(new EnArmaments(), true);
        addToDeck(new EnDefendRed(), false); // now upgraded

        //Turn 2
        addToDeck(new EnTwinStrike(), false);
        addToDeck(new EnIronWave(), extraUpgrades);
        addToDeck(new EnRegret(), false);

        //Turn 3
        addToDeck(new EnFlameBarrier(), false);
        addToDeck(new EnJAX(), extraUpgrades);
        addToDeck(new EnBite(), false);

        //Turn 4
        addToDeck(new EnHeavyBlade(), false);
        addToDeck(new EnDefendRed(), false);
        addToDeck(new EnIronWave(), true);

        //Turn 5
        addToDeck(new EnBash(), false);
        addToDeck(new EnBite(), false);
        addToDeck(new EnBite(), false);

        //Turn 6
        addToDeck(new EnDemonForm(), extraUpgrades); // removed
        addToDeck(new EnSeeingRed(), false); // removed
        addToDeck(new EnClothesline(), true);

        //INFINITE LOOP

        addToDeck(new EnJAX(), extraUpgrades);
        addToDeck(new EnClothesline(), true);
        addToDeck(new EnDefendRed(), false);

        addToDeck(new EnFlameBarrier(), false);
        addToDeck(new EnArmaments(), true);
        addToDeck(new EnRegret(), false);

        addToDeck(new EnIronWave(), extraUpgrades);
        addToDeck(new EnBite(), false);
        addToDeck(new EnBash(), false);

        addToDeck(new EnTwinStrike(), false);
        addToDeck(new EnIronWave(), true);
        addToDeck(new EnBite(), false);

        addToDeck(new EnHeavyBlade(), false);
        addToDeck(new EnDefendRed(), false); // upgraded from armaments
        addToDeck(new EnBite(), false);




    }

    @Override
    public void initializeBonusRelic() {
        addRelic(new CBR_Ginger());
    }
}