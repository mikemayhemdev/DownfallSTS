package charbosses.bosses.Defect;

import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Ironclad.ArchetypeBaseIronclad;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.anticards.Debug;
import charbosses.cards.blue.*;
import charbosses.cards.curses.EnRegret;
import charbosses.cards.purple.*;
import charbosses.orbs.AbstractEnemyOrb;
import charbosses.relics.*;
import charbosses.relics.EventRelics.CBR_Colosseum;
import charbosses.relics.EventRelics.CBR_ScrapOoze;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import java.util.ArrayList;

public class ArchetypeAct3Orbs extends ArchetypeBaseDefect {

    public ArchetypeAct3Orbs() {
        super("DF_ARCHETYPE_", "Strike");
    }

    private void increasePretendFocus(int amount) {
        for (AbstractOrb o : AbstractCharBoss.boss.orbs) {
            if (o instanceof AbstractEnemyOrb) {
                ((AbstractEnemyOrb) o).pretendFocus += amount;
                AbstractEnemyOrb.masterPretendFocus += amount;
                o.applyFocus();
            }
        }
    }

    public static void resetPretendFocus() {
        for (AbstractOrb o : AbstractCharBoss.boss.orbs) {
            if (o instanceof AbstractEnemyOrb) {
                ((AbstractEnemyOrb) o).pretendFocus = 0;
                AbstractEnemyOrb.masterPretendFocus = 0;
                o.applyFocus();
            }
        }
    }

    public void initialize() {
        //Tuning Recommendation per Act (CARDS): 1 Card Removal, 2 Upgrades, ~6-8 cards added to deck
        //Tuning Recommendation per Act (RELICS): 2 relics and an Event relic (simulate what the Event did)
        //Tuning Recommendation for Act 2-3: At least 1 Rare and 1 Boss Relic in addition to above 2
        //Make the total cards always divisible by 3 - Shuffle should not occur on a partial hand

        //STARTER DECK - 4 Strikes, 4 Defends, 1 Zap, 1 Dualcast

        //2 Strikes 2 Defend Removed

        //15 Cards Added, 3 Upgrades:  (Zap+)  (fewer than usual upgrades due to Fusion Hammer)
        //Ball Lightning
        //Chill+
        //Cold Snap
        //Ball Lightning
        //Defragment
        //Capacitor
        //Leap
        //Core Surge
        //Barrage
        //Cold Snap
        //Blizzard
        //Glacier
        //Storm+
        //Consume
        //Biased Cognition


        /////   RELICS   /////

        addRelic(new CBR_NeowsBlessing());

        addRelic(new CBR_BirdFacedUrn());
        addRelic(new CBR_Anchor());
        addRelic(new CBR_Inserter());
        addRelic(new CBR_SymbioticVirus());
        addRelic(new CBR_FusionHammer());

        //addRelic(new CBR_ScrapOoze());
    }


        @Override
        public ArrayList<AbstractCard> getThisTurnCards () {
            ArrayList<AbstractCard> cardsList = new ArrayList<>();
            boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;
            if (looped) {
                switch (turn) {
                    case 0:
                        //Frost Lightning Dark Lightning Frost Frost Lightning Lightning
                        //Turn 8 - 9 Slots
                        addToList(cardsList, new EnDualcast(), false); //Evokes Frost Twice
                        if (AbstractCharBoss.boss.orbs.get(0) instanceof AbstractEnemyOrb)
                            ((AbstractEnemyOrb) AbstractCharBoss.boss.orbs.get(0)).evokeOverride = true; // Evokes Lightning
                        if (AbstractCharBoss.boss.orbs.get(0) instanceof AbstractEnemyOrb)
                            ((AbstractEnemyOrb) AbstractCharBoss.boss.orbs.get(0)).evokeMult = 2; // Twice!
                        addToList(cardsList, new EnColdSnap(), false);
                        addToList(cardsList, new EnCoreSurge(), false);
                        //Lightning Dark Lightning Frost Frost Lightning Lightning Frost Lightning
                        break;
                    case 1:
                        addToList(cardsList, new EnLeap(), false);
                        addToList(cardsList, new EnDarkness(), false);
                        addToList(cardsList, new EnBlizzard(), false);
                        //Lightning Dark Lightning Frost Frost Lightning Lightning Frost Lightning Dark
                        break;
                    case 2:
                        //Turn 10 - 10 Slots
                        addToList(cardsList, new EnGlacier(), extraUpgrades); // Evokes Lightning
                        if (AbstractCharBoss.boss.orbs.get(0) instanceof AbstractEnemyOrb)
                            ((AbstractEnemyOrb) AbstractCharBoss.boss.orbs.get(0)).evokeOverride = true;
                        addToList(cardsList, new EnStrikeBlue(), false);
                        addToList(cardsList, new EnZap(), true); // Evokes Dark
                        if (AbstractCharBoss.boss.orbs.get(1) instanceof AbstractEnemyOrb)
                            ((AbstractEnemyOrb) AbstractCharBoss.boss.orbs.get(1)).evokeOverride = true; // Evokes
                        //Dark Lightning Frost Frost Lightning Lightning Frost Lightning Dark Frost Frost
                        break;
                    case 3:
                        addToList(cardsList, new EnStrikeBlue(), false);
                        addToList(cardsList, new EnRegret(), false);
                        addToList(cardsList, new EnBallLightning(), false); // Evokes Lightning
                        if (AbstractCharBoss.boss.orbs.get(0) instanceof AbstractEnemyOrb)
                            ((AbstractEnemyOrb) AbstractCharBoss.boss.orbs.get(0)).evokeOverride = true;
                        //Dark Lightning Frost Frost Lightning Lightning Frost Lightning Dark Frost Frost
                        break;
                    case 4:
                        addToList(cardsList, new EnForceField(), extraUpgrades);
                        addToList(cardsList, new EnBallLightning(), false); // Evokes Frost
                        if (AbstractCharBoss.boss.orbs.get(0) instanceof AbstractEnemyOrb)
                            ((AbstractEnemyOrb) AbstractCharBoss.boss.orbs.get(0)).evokeOverride = true;
                        addToList(cardsList, new EnDefendBlue(), false);
                        //Dark Lightning Frost Frost Lightning Lightning Frost Lightning Dark Frost Frost
                        break;
                }
            } else {
                switch (turn) {
                    case 0:
                        //Turn 1
                        //Starts with Dark
                        addToList(cardsList, new EnBallLightning(), false);
                        addToList(cardsList, new EnChill(), true);  //removed
                        addToList(cardsList, new EnStorm(), true);  //removed
                        //Darkness Lightning Frost
                        break;
                    case 1:        //Turn 2 - 4 Slots
                        addToList(cardsList, new EnCapacitor(), false);  //removed
                        addToList(cardsList, new EnColdSnap(), false);
                        addToList(cardsList, new EnForceField(), extraUpgrades);
                        //Frost Frost Lightning Frost
                        break;
                    case 2:
                        //Turn 3 - 6 Slots
                        addToList(cardsList, new EnBallLightning(), false);
                        addToList(cardsList, new EnDarkness(), false); // Evokes Dark
                        if (AbstractCharBoss.boss.orbs.get(0) instanceof AbstractEnemyOrb)
                            ((AbstractEnemyOrb) AbstractCharBoss.boss.orbs.get(0)).evokeOverride = true; // Evokes dark!
                        addToList(cardsList, new EnStrikeBlue(), false);
                        //Frost Frost Lightning Frost Lightning Dark
                        break;
                    case 3:        //Turn 4 - 7 Slots
                        addToList(cardsList, new EnDefragment(), false);  //removed - Does NOT Evoke Lightning
                        increasePretendFocus(1);
                        //TODO - Big Genetic Algorithm
                 /*
                 AbstractBossCard c = new EnRitualDagger();  //removed
                 c.misc += 5;
                 c.applyPowers();
                 c.baseDamage = c.misc;
                 c.isDamageModified = false;
                 addToDeck(c, true);
        */
                        addToList(cardsList, new EnRegret(), false);
                        //Frost Frost Lightning Frost Lightning Dark Lightning
                        break;
                    case 4:
                        //Turn 5 - 7 Slots
                        addToList(cardsList, new EnDualcast(), false); // Evokes Lightning
                        if (AbstractCharBoss.boss.orbs.get(0) instanceof AbstractEnemyOrb)
                            ((AbstractEnemyOrb) AbstractCharBoss.boss.orbs.get(0)).evokeOverride = true; // Evokes Lightning
                        if (AbstractCharBoss.boss.orbs.get(0) instanceof AbstractEnemyOrb)
                            ((AbstractEnemyOrb) AbstractCharBoss.boss.orbs.get(0)).evokeMult = 2; // Twice!
                        addToList(cardsList, new EnGlacier(), extraUpgrades); // Evokes Frost
                        if (AbstractCharBoss.boss.orbs.get(1) instanceof AbstractEnemyOrb)
                            ((AbstractEnemyOrb) AbstractCharBoss.boss.orbs.get(1)).evokeOverride = true; // Evokes frost!
                        addToList(cardsList, new EnStrikeBlue(), false);
                        //Lightning Frost Lightning Dark Lightning Frost Frost
                        break;
                    case 5:
                        //Turn 6 - 8 Slots
                        addToList(cardsList, new EnBlizzard(), false); // Does 0 damage currently :(
                        addToList(cardsList, new EnLeap(), false);
                        addToList(cardsList, new EnZap(), true); // No evoke
                        //Lightning Frost Lightning Frost Lightning Frost Frost Lightning
                        break;
                    case 6:
                        //Turn 7 - 8 Slots
                        addToList(cardsList, new EnCoreSurge(), false); // removed
                        addToList(cardsList, new EnBiasedCognition(), extraUpgrades);  //removed - Evokes Lightning
                        increasePretendFocus(extraUpgrades ? 5 : 4);
                        if (AbstractCharBoss.boss.orbs.get(0) instanceof AbstractEnemyOrb)
                            ((AbstractEnemyOrb) AbstractCharBoss.boss.orbs.get(0)).evokeOverride = true; // Evokes Lightning
                        addToList(cardsList, new EnDefendBlue(), false);
                        //Frost Lightning Dark Lightning Frost Frost Lightning Lightning
                        break;
                }
            }
            turn++;
            if (turn > 6 && !looped) {
                looped = true;
                turn = 0;
            }
            else if (turn > 4 && looped) {
                turn = 0;
            }
            return cardsList;
        }



    @Override
    public void initializeBonusRelic() {
        addRelic(new CBR_DataDisk());
    }
}