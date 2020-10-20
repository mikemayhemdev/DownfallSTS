package charbosses.bosses.Defect;

import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Ironclad.ArchetypeBaseIronclad;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.blue.*;
import charbosses.cards.colorless.EnSwiftStrike;
import charbosses.cards.curses.EnClumsy;
import charbosses.cards.curses.EnPain;
import charbosses.cards.curses.EnShame;
import charbosses.relics.*;
import charbosses.relics.EventRelics.CBR_Bandits;
import charbosses.relics.EventRelics.CBR_OminousForge;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.Clumsy;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class ArchetypeAct2Claw extends ArchetypeBaseDefect {

    private EnClaw c;
    private CharBossDefect cB;

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
        addRelic(new CBR_RedMask());


    }


    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        if (cB == null){
            cB = (CharBossDefect) AbstractCharBoss.boss;
        }
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;
        if (looped) {
            switch (turn) {

                case 0:
                    c = new EnClaw();
                    c.baseDamage += cB.clawsPlayed * 2;
                    addToList(cardsList, c, true);
                    c = new EnClaw();
                    c.baseDamage += (cB.clawsPlayed + 1) * 2;
                    addToList(cardsList, c, true);
                    addToList(cardsList, new EnLeap(), extraUpgrades);
                    addToList(cardsList, new EnDefendBlue(), false);
                    addToList(cardsList, new EnStrikeBlue(), true);
                    //Kunai Proc
                    break;
                case 1:
                    addToList(cardsList, new EnChargeBattery(), false);
                    AbstractCard r = new EnChargeBattery();
                    r.freeToPlayOnce = true;
                    addToList(cardsList, r, false);
                    addToList(cardsList, new EnStrikeBlue(), true);
                    addToList(cardsList, new EnStrikeBlue(), extraUpgrades);
                    addToList(cardsList, new EnDefendBlue(), false);
                    //Kunai Proc
                    break;
                case 2:
                    //Turn 3
                    addToList(cardsList, new EnReprogram(), false);
                    AbstractBossCard c2 = new EnRebound();
                    c2.manualCustomDamageModifier = 2;
                    addToList(cardsList, c2, false);
                    AbstractBossCard c3 = new EnSwiftStrike();
                    c3.manualCustomDamageModifier = 2;
                    addToList(cardsList, c3, false);
                    AbstractBossCard c4 = new EnStrikeBlue();
                    c4.manualCustomDamageModifier = 2;
                    addToList(cardsList, c4, false);
                    break;
                case 3:
                    //Turn 4
                    c = new EnClaw();
                    c.baseDamage += cB.clawsPlayed * 2;
                    addToList(cardsList, c, extraUpgrades);
                    c = new EnClaw();
                    c.baseDamage += (cB.clawsPlayed + 1) * 2;
                    addToList(cardsList, c, extraUpgrades);
                    addToList(cardsList, new EnSwiftStrike(), false);
                    addToList(cardsList, new EnDefendBlue(), true);
                    addToList(cardsList, new EnDefendBlue(), false);
                    //Kunai Proc
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    addToList(cardsList, new EnBootSequence(), false);  // removed
                    c = new EnClaw();
                    c.baseDamage += cB.clawsPlayed * 2;
                    addToList(cardsList, c, extraUpgrades);
                    addToList(cardsList, new EnMachineLearning(), extraUpgrades);  // removed
                    break;
                case 1:
                    //Turn 2
                    addToList(cardsList, new EnLeap(), extraUpgrades);
                    addToList(cardsList, new EnStrikeBlue(), true);
                    addToList(cardsList, new EnStrikeBlue(), true);
                    addToList(cardsList, new EnClumsy(), false);  //removed
                    break;
                case 2:
                    //Turn 3
                    addToList(cardsList, new EnReprogram(), false);
                    AbstractBossCard c5 = new EnSwiftStrike();
                    c5.manualCustomDamageModifier = 1;
                    addToList(cardsList, c5, false);
                    AbstractBossCard c6 = new EnRebound();
                    c6.manualCustomDamageModifier = 1;
                    addToList(cardsList, c6, false);
                    c = new EnClaw();
                    c.baseDamage += cB.clawsPlayed * 2;
                    c.manualCustomDamageModifier = 1;
                    addToList(cardsList, c, extraUpgrades);
                    //Kunai Proc
                    break;
                case 3:
                    //Turn 4
                    c = new EnClaw();
                    c.baseDamage += cB.clawsPlayed * 2;
                    addToList(cardsList, c, extraUpgrades);
                    addToList(cardsList, new EnChargeBattery(), false);
                    addToList(cardsList, new EnGeneticAlgorithm(), false);  //removed
                    addToList(cardsList, new EnShame(), false);  //removed
                    addToList(cardsList, new EnStrikeBlue(), false);
                    break;
                case 4:
                    //Turn 5
                    addToList(cardsList, new EnEchoForm(), true);  // removed
                    addToList(cardsList, new EnStrikeBlue(), false);
                    addToList(cardsList, new EnDefendBlue(), false);
                    addToList(cardsList, new EnDefendBlue(), false);
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
        addRelic(new CBR_Anchor());
    }
}