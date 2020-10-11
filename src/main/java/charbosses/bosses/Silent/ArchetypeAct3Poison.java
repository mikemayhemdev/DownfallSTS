package charbosses.bosses.Silent;

import charbosses.bosses.Ironclad.ArchetypeBaseIronclad;
import charbosses.cards.anticards.Antidote;
import charbosses.cards.green.*;
import charbosses.relics.*;
import charbosses.relics.EventRelics.CBR_DesignerInSpire;
import charbosses.relics.EventRelics.CBR_OldBeggar;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

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
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;//Turn 1
        addToList(cardsList, new EnBane());
        addToList(cardsList, new EnDeadlyPoison(), extraUpgrades);
        addToList(cardsList, new EnDodgeAndRoll(), extraUpgrades);

        //Turn 2
        addToList(cardsList, new EnNoxiousFumes(), true);
        addToList(cardsList, new EnBouncingFlask(), true);
        addToList(cardsList, new EnDefendGreen());

        //Turn 3
        addToList(cardsList, new EnFootwork(), true);
        addToList(cardsList, new EnDeadlyPoison(), true);
        addToList(cardsList, new EnStrikeGreen(), true);

        //Turn 4
        addToList(cardsList, new EnBouncingFlask(), extraUpgrades);
        addToList(cardsList, new EnDefendGreen());
        addToList(cardsList, new EnBane(), true);

        //Turn 5
        addToList(cardsList, new EnWraithForm(), true);
        addToList(cardsList, new EnCatalyst(), true);
        addToList(cardsList, new EnDefendGreen(), true);

        //Turn 6
        addToList(cardsList, new EnNeutralize(), true);
        addToList(cardsList, new EnCripplingCloud());
        addToList(cardsList, new EnDefendGreen());
    }



    @Override
    public void initializeBonusRelic() {
        addRelic(new CBR_HappyFlower());
    }
}