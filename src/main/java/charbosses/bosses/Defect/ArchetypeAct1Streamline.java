package charbosses.bosses.Defect;

import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.blue.*;
import charbosses.orbs.AbstractEnemyOrb;
import charbosses.relics.CBR_Abacus;
import charbosses.relics.CBR_NeowsBlessing;
import charbosses.relics.CBR_SmoothStone;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class ArchetypeAct1Streamline extends ArchetypeBaseDefect {

    public ArchetypeAct1Streamline() {
        super("DF_ARCHETYPE_STREAMLINE", "Streamline");
    }

    public void initialize() {
        theVeryImportantStreamline.upgrade(); // this seems a little strong
        //Tuning Recommendation per Act (CARDS): 1 Card Removal, 2 Upgrades, ~6-8 cards added to deck
        //Tuning Recommendation per Act (RELICS): 2 relics and an Event relic (simulate what the Event did)
        //Tuning Recommendation for Act 2-3: At least 1 Rare and 1 Boss Relic in addition to above 2
        //Make the total cards always divisible by 3 - Shuffle should not occur on a partial hand

        //STARTER DECK - 4 Strikes, 4 Defends, 1 Zap, 1 Dualcast

        //1 Strike 1 Defend Removed

        //7 Cards Added, 3 Upgrades:
        //Cold Snap
        //Leap+
        //Streamline+
        //Ball Lightning+
        //Rebound
        //Charge Battery
        //Rip and Tear


        /////   RELICS   /////

        addRelic(new CBR_NeowsBlessing());

        //data disk
        addRelic(new CBR_SmoothStone());
        addRelic(new CBR_Abacus());
        // addRelic(new CBR_Transmogrifier());  //Could be something else, no strong lean in any direction
    }

    private AbstractCard theVeryImportantStreamline = new EnStreamline();

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;
        if (looped) {
            switch (turn) {
                case 0:
                    addToList(cardsList, new EnDualcast(), false);  //Evokes Lightning twice
                    if (AbstractCharBoss.boss.orbs.get(0) instanceof AbstractEnemyOrb)
                        ((AbstractEnemyOrb) AbstractCharBoss.boss.orbs.get(0)).evokeOverride = true;
                    if (AbstractCharBoss.boss.orbs.get(0) instanceof AbstractEnemyOrb)
                        ((AbstractEnemyOrb) AbstractCharBoss.boss.orbs.get(0)).evokeMult = 2;
                    addToList(cardsList, new EnBallLightning(), true);
                    addToList(cardsList, new EnDefendBlue(), false);
                    //Frost Lightning Lightning
                    break;
                case 1:
                    //Turn 2
                    addToList(cardsList, new EnDefendBlue(), extraUpgrades);
                    addToList(cardsList, new EnColdSnap(), extraUpgrades);  //Evokes Frost
                    if (AbstractCharBoss.boss.orbs.get(0) instanceof AbstractEnemyOrb)
                        ((AbstractEnemyOrb) AbstractCharBoss.boss.orbs.get(0)).evokeOverride = true; // Evokes frost!
                    addToList(cardsList, new EnStrikeBlue(), false); //not used
                    //Lightning Lightning Frost
                    break;
                case 2:
                    //Turn 3
                    addToList(cardsList, theVeryImportantStreamline);
                    addToList(cardsList, new EnChargeBattery(), extraUpgrades);
                    addToList(cardsList, new EnStrikeBlue(), false);
                    //Lightning Frost Lightning
                    break;
                case 3:
                    //Turn 4
                    addToList(cardsList, new EnRebound(), false);
                    addToList(cardsList, new EnLeap(), true);
                    addToList(cardsList, new EnZap(), false); //not used
                    //Lightning Frost Lightning
                    break;
                case 4:
                    //Turn 5
                    addToList(cardsList, new EnLeap(), true);
                    addToList(cardsList, new EnRipAndTear(), false);
                    addToList(cardsList, new EnStrikeBlue(), false); //not used
                    //Lightning Frost Lightning
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    addToList(cardsList, new EnZap(), false);
                    addToList(cardsList, new EnDefendBlue(), false);
                    addToList(cardsList, new EnDualcast(), false); //not used
                    //1 Lightning
                    break;
                case 1:
                    //Turn 2
                    addToList(cardsList, new EnColdSnap(), extraUpgrades);
                    addToList(cardsList, new EnLeap(), true);
                    addToList(cardsList, new EnStrikeBlue(), false); //not used
                    //1 Lightning 1 Frost
                    break;
                case 2:
                    //Turn 3
                    addToList(cardsList, new EnBallLightning(), true);
                    addToList(cardsList, new EnChargeBattery(), extraUpgrades);
                    addToList(cardsList, new EnStrikeBlue(), false);
                    //Lightning Frost Lightning
                    break;
                case 3:
                    //Turn 4
                    addToList(cardsList, new EnRebound(), false);
                    addToList(cardsList, theVeryImportantStreamline);
                    addToList(cardsList, new EnDefendBlue(), extraUpgrades);
                    break;
                case 4:
                    //Turn 5
                    addToList(cardsList, theVeryImportantStreamline);  //1-cost
                    addToList(cardsList, new EnStrikeBlue(), false);
                    addToList(cardsList, new EnRipAndTear(), false);
                    //Lightning Frost Lightning
                    break;
            }
        }
        turn++;
        if (turn > 4) {
            if (!looped) looped = true;
            turn = 0;
        }
        return cardsList;
    }

    @Override
    public void initializeBonusRelic() {
        addRelic(new CBR_Abacus());
    }
}