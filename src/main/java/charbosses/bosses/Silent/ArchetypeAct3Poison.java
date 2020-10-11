package charbosses.bosses.Silent;

import charbosses.bosses.Ironclad.ArchetypeBaseIronclad;
import charbosses.cards.anticards.Antidote;
import charbosses.cards.curses.EnDoubt;
import charbosses.cards.green.*;
import charbosses.relics.*;
import charbosses.relics.EventRelics.CBR_DesignerInSpire;
import charbosses.relics.EventRelics.CBR_OldBeggar;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

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
        addRelic(new CBR_PhilosopherStone());
        addRelic(new CBR_ClockworkSouvenir());
        addRelic(new CBR_TungstenRod());
       // addRelic(new CBR_Pocketwatch());
       // addRelic(new CBR_SmilingMask());
       // addRelic(new CBR_DesignerInSpire());
       // addRelic(new CBR_FrozenEgg());
       // addRelic(new CBR_PenNib());
        //addRelic(new CBR_OldBeggar());


        /////   CARDS   /////
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;//Turn 1

    }


    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;
        if (looped) {

            switch (turn) {
                case 0:
                    addToList(cardsList, new EnNeutralize(), true);
                    addToList(cardsList, new EnBouncingFlask(), true);
                    addToList(cardsList, new EnBane());
                    break;
                case 1:
                    addToList(cardsList, new EnBouncingFlask(), true);
                    addToList(cardsList, new EnDodgeAndRoll(), extraUpgrades);
                    addToList(cardsList, new EnDefendGreen());
                    break;
                case 2:
                    addToList(cardsList, new EnBane(), true);
                    addToList(cardsList, new EnStrikeGreen(), true);
                    addToList(cardsList, new EnDeadlyPoison(), true);
                    break;
                case 3:
                    addToList(cardsList, new EnCripplingCloud());
                    addToList(cardsList, new EnDefendGreen(), true);
                    addToList(cardsList, new EnDefendGreen());  //not played
                    break;
                case 4:
                    addToList(cardsList, new EnDeadlyPoison(), extraUpgrades);
                    addToList(cardsList, new EnStrikeGreen(), true);
                    addToList(cardsList, new EnDefendGreen());
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    addToList(cardsList, new EnDeadlyPoison(), extraUpgrades);
                    addToList(cardsList, new EnDodgeAndRoll(), extraUpgrades);
                    addToList(cardsList, new EnBane());
                    break;
                case 1:
                    addToList(cardsList, new EnNoxiousFumes(), true);  //removed
                    addToList(cardsList, new EnBouncingFlask(), true);
                    addToList(cardsList, new EnDefendGreen()); //not played
                    break;
                case 2:
                    addToList(cardsList, new EnFootwork(), true);  //removed
                    addToList(cardsList, new EnDeadlyPoison(), true);
                    addToList(cardsList, new EnStrikeGreen(), true);
                    break;
                case 3:
                    addToList(cardsList, new EnBouncingFlask(), extraUpgrades);
                    addToList(cardsList, new EnBane(), true);
                    addToList(cardsList, new EnDefendGreen());  //not played
                    break;
                case 4:
                    addToList(cardsList, new EnWraithForm(), true);  //removed
                    addToList(cardsList, new EnStrikeGreen(), true); //not played
                    addToList(cardsList, new EnDefendGreen(), true); //not played
                    break;
                case 5:
                    addToList(cardsList, new EnNeutralize(), true);
                    addToList(cardsList, new EnCripplingCloud());
                    addToList(cardsList, new EnDefendGreen());
                    break;
            }
        }
        turn++;
        if (turn > 5 && !looped) looped = true;
        else if (turn > 4 && looped) {
            turn = 0;
        }
        return cardsList;
    }


    @Override
    //TODO - Switch this to poison skull relic
    public void initializeBonusRelic() {
        addRelic(new CBR_HappyFlower());
    }
}