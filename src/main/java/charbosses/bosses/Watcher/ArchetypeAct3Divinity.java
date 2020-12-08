package charbosses.bosses.Watcher;

import charbosses.bosses.Defect.ArchetypeBaseDefect;
import charbosses.cards.anticards.PeaceOut;
import charbosses.cards.curses.EnWrithe;
import charbosses.cards.green.*;
import charbosses.cards.purple.*;
import charbosses.relics.*;
import charbosses.relics.EventRelics.CBR_FaceTrader;
import charbosses.relics.EventRelics.CBR_Falling;
import charbosses.relics.EventRelics.CBR_Mausoleum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class ArchetypeAct3Divinity extends ArchetypeBaseDefect {

    public ArchetypeAct3Divinity() {
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
        //wreath of flame - next attack deal 5 more damage
        //alpha - beta - omega
        //establishment - retain costs less
        //ragnarok - 5x5 damage
        //wish - plated armor or 3 strength
        //Prostrate - 2 mantra, 4 block
        //worship - gain 5 mantra
        //brilliance - damage equal to mantra gained
        //devotion - 2 mantra per turn


        //Retain build, starts with Establishment, battle hymn at the end

        //Mantra build, devotion as main source

        //Calm build, alpha/beta/omega


        /////   RELICS   /////

        addRelic(new CBR_NeowsBlessing());
        addRelic(new CBR_HandDrill());
        addRelic(new CBR_FaceTrader());
        addRelic(new CBR_MaskGremlin());
        addRelic(new CBR_Abacus());
        addRelic(new CBR_Mausoleum());
        addRelic(new CBR_IceCream());
        addRelic(new CBR_WarPaint());
        addRelic(new CBR_PhilosopherStone());
        addRelic(new CBR_Falling());

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

                    addToList(cardsList, new EnStrikePurple(), false);
                    addToList(cardsList, new EnDevotion(), false);
                    addToList(cardsList, new EnWrithe(), false);
                    break;
                case 1:

                    addToList(cardsList, new EnSashWhip(), extraUpgrades);
                    addToList(cardsList, new EnStrikePurple(), false);
                    addToList(cardsList, new EnDefendPurple(), true);
                    break;
                case 2:

                    addToList(cardsList, new EnDefendPurple(), false);
                    addToList(cardsList, new EnWaveOfTheHand(), false);
                    addToList(cardsList, new EnWallop(), extraUpgrades);
                    break;
                case 3:

                    addToList(cardsList, new EnProtect(), true);
                    addToList(cardsList, new EnStrikePurple(), false);
                    addToList(cardsList, new EnStrikePurple(), false);
                    break;
                case 4:

                    addToList(cardsList, new EnFasting(), extraUpgrades);
                    addToList(cardsList, new EnCrushJoints(), false);
                    addToList(cardsList, new EnBrilliance(), false);
                    break;
            }
        } else {
            switch (turn) {
                case 0:

                    addToList(cardsList, new EnStrikePurple(), false);
                    addToList(cardsList, new EnDevotion(), false);
                    addToList(cardsList, new EnWrithe(), false);
                    break;
                case 1:

                    addToList(cardsList, new EnSashWhip(), extraUpgrades);
                    addToList(cardsList, new EnStrikePurple(), false);
                    addToList(cardsList, new EnDefendPurple(), true);
                    break;
                case 2:

                    addToList(cardsList, new EnDefendPurple(), false);
                    addToList(cardsList, new EnWaveOfTheHand(), false);
                    addToList(cardsList, new EnWallop(), extraUpgrades);
                    break;
                case 3:

                    addToList(cardsList, new EnProtect(), true);
                    addToList(cardsList, new EnStrikePurple(), false);
                    addToList(cardsList, new EnStrikePurple(), false);
                    break;
                case 4:

                    addToList(cardsList, new EnFasting(), extraUpgrades);
                    addToList(cardsList, new EnCrushJoints(), false);
                    addToList(cardsList, new EnBrilliance(), false);
                case 5:

                    addToList(cardsList, new EnProtect(), true);
                    addToList(cardsList, new EnWallop(), false);
                    addToList(cardsList, new EnDefendPurple(), false);
                    break;
                case 6:
                    addToList(cardsList, new EnBattleHymn(), false);
                    addToList(cardsList, new EnFasting(), true);
                    addToList(cardsList, new EnDevotion(), false);
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
    public void initializeBonusRelic() {
        addRelic(new CBR_SmoothStone());
    }
}