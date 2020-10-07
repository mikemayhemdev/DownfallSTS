package charbosses.bosses.Silent;

import charbosses.bosses.Ironclad.ArchetypeBaseIronclad;
import charbosses.cards.curses.EnDoubt;
import charbosses.cards.green.*;
import charbosses.relics.*;
import charbosses.relics.EventRelics.CBR_Serpent;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

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
    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;
        if (looped) {
            switch (turn) {
                case 0:
                    addToList(cardsList, new EnNeutralize(), true);
                    addToList(cardsList, new EnPredator());
                    addToList(cardsList, new EnSlice());          //Shuriken Proc
                    break;
                case 1:
                    addToList(cardsList, new EnFlyingKnee(), extraUpgrades);
                    addToList(cardsList, new EnCloakAndDagger(), true);
                    addToList(cardsList, new EnDoubt());
                    addToList(cardsList, new EnSlice(), extraUpgrades);
                    addToList(cardsList, new EnDefendGreen());   //Shuriken Proc x2
                    break;
                case 2:
                    addToList(cardsList, new EnBladeDance(), true);     //Shuriken Proc
                    addToList(cardsList, new EnStrikeGreen());
                    addToList(cardsList, new EnFinisher(), true);   //x5, Shuriken Proc x2
                    break;
                case 3:
                    addToList(cardsList, new EnLegSweep(), extraUpgrades);
                    addToList(cardsList, new EnDefendGreen());
                    addToList(cardsList, new EnStrikeGreen(), true);
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    //Turn 1
                    addToList(cardsList, new EnFlyingKnee(), extraUpgrades);
                    addToList(cardsList, new EnStrikeGreen());
                    addToList(cardsList, new EnSlice());  //Shuriken Proc
                    break;
                case 1:
                    //Turn 2
                    addToList(cardsList, new EnNeutralize(), true);
                    addToList(cardsList, new EnPredator());
                    addToList(cardsList, new EnStrikeGreen(), true);    //Shuriken Proc
                    break;
                case 2:
                    //Turn 3
                    addToList(cardsList, new EnSlice(), extraUpgrades);
                    addToList(cardsList, new EnInfiniteBlades());  //removed
                    addToList(cardsList, new EnDefendGreen());
                    addToList(cardsList, new EnDoubt());
                    addToList(cardsList, new EnEndlessAgony());  //removed    //Shuriken Proc
                    break;
                case 3:
                    //Turn 4
                    addToList(cardsList, new EnAfterImage());  //removed
                    addToList(cardsList, new EnCloakAndDagger(), true);
                    addToList(cardsList, new EnFinisher(), true);     //Shuriken Proc   x4 hits
                    break;
                case 4:
                    //Turn 5
                    addToList(cardsList, new EnThousandCuts());  //removed
                    addToList(cardsList, new EnLegSweep(), extraUpgrades);
                    addToList(cardsList, new EnEndlessAgony());  //removed     //Shuriken Proc
                    break;
                case 5:
                    //Turn 6
                    addToList(cardsList, new EnEndlessAgony());  //removed
                    addToList(cardsList, new EnDefendGreen());
                    addToList(cardsList, new EnBladeDance(), true);     //Shuriken Proc x2
                    break;
            }
        }
        turn++;
        if (turn > 5 && !looped) { looped = true;
            turn = 0;
        }
        else if (turn > 3 && looped) {
            turn = 0;
        }
        return cardsList;
    }

    @Override
    public void initializeBonusRelic() {
        addRelic(new CBR_CaptainsWheel());
    }
}