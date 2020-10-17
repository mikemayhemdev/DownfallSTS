package charbosses.bosses.Watcher;

import charbosses.bosses.Defect.ArchetypeBaseDefect;
import charbosses.cards.blue.EnStreamline;
import charbosses.cards.curses.EnClumsy;
import charbosses.cards.purple.*;
import charbosses.relics.CBR_CloakClasp;
import charbosses.relics.CBR_NeowsBlessing;
import charbosses.relics.CBR_TungstenRod;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.Clumsy;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class ArchetypeAct1Retain extends ArchetypeBaseDefect {


    private AbstractCard retainedcardsWindmillStrike = new EnWindmillStrike();
    private AbstractCard retainedcardsProtect = new EnProtect();
    private AbstractCard retainedcardsFlyingSleeves = new EnFlyingSleeves();
    private AbstractCard retainedcardsSandsOfTime = new EnSandsOfTime();

    public ArchetypeAct1Retain() {
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


        //Retain build, starts with Establishment

        //Calm build, alpha/beta/omega, battle hymn at the end

        //Mantra build, devotion as main source


        /////   RELICS   /////


        addRelic(new CBR_NeowsBlessing());
        addRelic(new CBR_CloakClasp());
        //addRelic(new CBR_IceCream());
        //addRelic(new CBR_Lantern());
        //addRelic(new CBR_BagOfPreparation());
        //addRelic(new CBR_UpgradeShrine());
    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;
        if (looped) {
            switch (turn) {
                case 0:

                    //Turn 1
                    addToList(cardsList, retainedcardsProtect, true);
                    addToList(cardsList, new EnStrikePurple(), false);
                    addToList(cardsList, new EnSashWhip(), extraUpgrades);
                    break;
                case 1:
                    //Turn 2
                    addToList(cardsList, retainedcardsWindmillStrike, false);
                    addToList(cardsList, new EnDefendPurple(), false);
                    addToList(cardsList, new EnDefendPurple(), false);
                    break;
                case 2:
                    //Turn 3
                    addToList(cardsList, new EnVigilance(), false);
                    addToList(cardsList, retainedcardsSandsOfTime, false);
                    addToList(cardsList, new EnStrikePurple(), false);
                    break;
                case 3:
                    addToList(cardsList, new EnDefendPurple(), true);
                    addToList(cardsList, new EnStrikePurple(), false);
                    addToList(cardsList, retainedcardsFlyingSleeves, extraUpgrades);
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    //Turn 1
                    addToList(cardsList, new EnEstablishment(), true);  //removed
                    addToList(cardsList, new EnTalkToTheHand(), extraUpgrades); //removed
                    addToList(cardsList, retainedcardsProtect, true);
                    break;
                case 1:
                    //Turn 2
                    addToList(cardsList, new EnStrikePurple(), false);
                    addToList(cardsList, new EnSashWhip(), extraUpgrades);
                    addToList(cardsList, retainedcardsWindmillStrike, false);  //not used
                    break;
                case 2:
                    //Turn 3
                    //Protect Retained 0 cost - use this turn
                    //Windmill Retainde 1 cost
                    addToList(cardsList, new EnVigilance(), false);
                    addToList(cardsList, retainedcardsSandsOfTime, false);  //not used
                    addToList(cardsList, new EnClumsy(), false);
                    break;
                case 3:
                    //Turn 4
                    //Sands Retained 2-cost
                    //Windmill Retained 0-cost - use this turn
                    addToList(cardsList, new EnDefendPurple(), true);
                    addToList(cardsList, new EnDefendPurple(), false);
                    addToList(cardsList, retainedcardsFlyingSleeves, extraUpgrades);  //not used
                    break;
                case 4:
                    //Turn 5
                    //Sands Retained 0-cost - use this turn
                    //Flying Sleeves Retained 0-cost - use this turn
                    addToList(cardsList, new EnStrikePurple(), false);
                    addToList(cardsList, new EnDefendPurple(), false);
                    addToList(cardsList, new EnStrikePurple(), false);  //not used
                    break;
            }
        }
        turn++;
        if (turn > 4 && !looped) {
            looped = true;
            turn = 0;
        } else if (turn > 3 && looped) {
            turn = 0;
        }
        return cardsList;
    }

    @Override
    public void initializeBonusRelic() {
        addRelic(new CBR_TungstenRod());
    }
}