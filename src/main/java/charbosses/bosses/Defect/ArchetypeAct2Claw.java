package charbosses.bosses.Defect;

import charbosses.bosses.Ironclad.ArchetypeBaseIronclad;
import charbosses.cards.blue.*;
import charbosses.cards.curses.EnPain;
import charbosses.cards.curses.EnShame;
import charbosses.relics.*;
import charbosses.relics.EventRelics.CBR_Bandits;
import charbosses.relics.EventRelics.CBR_OminousForge;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

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
        addRelic(new CBR_BlueCandle());
        addRelic(new CBR_RedMask());
        //addRelic(new CBR_WarPaint());
        //addRelic(new CBR_OminousForge());
        //addRelic(new CBR_WarpedTongs());
        //addRelic(new CBR_Bandits());
        //addRelic(new CBR_DollysMirror(CardCrawlGame.languagePack.getCardStrings("Gash").NAME));

    }


    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;
        if (looped) {
            switch (turn) {
                case 0:
                    addToList(cardsList, new EnBootSequence(), false);  // removed
                    addToList(cardsList, new EnClaw(), true);
                    addToList(cardsList, new EnMachineLearning(), extraUpgrades);  // removed
                    break;
                case 1:
                    //Turn 2
                    addToList(cardsList, new EnLeap(), extraUpgrades);
                    addToList(cardsList, new EnPain(), false);  //removed
                    addToList(cardsList, new EnStrikeBlue(), true);
                    addToList(cardsList, new EnClaw(), true);
                    break;
                case 2:
                    //Turn 3
                    addToList(cardsList, new EnBeamCell(), false);
                    addToList(cardsList, new EnRebound(), false);
                    addToList(cardsList, new EnClaw(), extraUpgrades);
                    //TODO: Reprogram
                    //Kunai Proc
                    break;
                case 3:
                    //Turn 4
                    addToList(cardsList, new EnClaw(), extraUpgrades);  //Rebounded
                    addToList(cardsList, new EnChargeBattery(), false);
                    addToList(cardsList, new EnStrikeBlue(), false);
                    addToList(cardsList, new EnShame(), false);  //removed
                    break;
                case 4:
                    //Turn 5
                    addToList(cardsList, new EnEchoForm(), true);  // removed
                    addToList(cardsList, new EnStrikeBlue(), false);
                    addToList(cardsList, new EnDefendBlue(), false);
                    addToList(cardsList, new EnDefendBlue(), false);
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    addToList(cardsList, new EnClaw(), true);
                    addToList(cardsList, new EnLeap(), extraUpgrades);
                    addToList(cardsList, new EnStrikeBlue(), true);
                    addToList(cardsList, new EnDefendBlue(), false);
                    //Kunai Proc
                    break;
                case 1:
                    //Turn 2
                    addToList(cardsList, new EnClaw(), extraUpgrades);
                    addToList(cardsList, new EnChargeBattery(), false);
                    addToList(cardsList, new EnStrikeBlue(), extraUpgrades);
                    addToList(cardsList, new EnDefendBlue(), false);
                    //Kunai Proc
                    break;
                case 2:
                    //Turn 3
                    //TODO: Reprogram
                    addToList(cardsList, new EnRebound(), false);
                    addToList(cardsList, new EnBeamCell(), false);
                    addToList(cardsList, new EnStrikeBlue(), false);
                    break;
                case 3:
                    //Turn 4
                    addToList(cardsList, new EnBeamCell(), false);
                    addToList(cardsList, new EnClaw(), extraUpgrades);
                    addToList(cardsList, new EnDefendBlue(), true);
                    addToList(cardsList, new EnDefendBlue(), false);
                    //Kunai Proc
                    break;
            }
        }
        turn++;
        if (turn > 4) {
            if (!looped) looped = true;
            else turn = 0;
        }
        return cardsList;
    }

    @Override
    public void initializeBonusRelic() {
        addRelic(new CBR_Anchor());
    }
}