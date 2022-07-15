package charbosses.bosses.Defect.NewAge;

import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Defect.ArchetypeBaseDefect;
import charbosses.bosses.Defect.CharBossDefect;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.blue.*;
import charbosses.cards.colorless.EnBlind;
import charbosses.cards.curses.EnClumsy;
import charbosses.orbs.AbstractEnemyOrb;
import charbosses.orbs.EnemyEmptyOrbSlot;
import charbosses.orbs.EnemyPlasma;
import charbosses.powers.bossmechanicpowers.DefectCuriosityLightningPower;
import charbosses.powers.bossmechanicpowers.DefectCuriosityPower;
import charbosses.relics.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;

import java.util.ArrayList;
import java.util.Collection;

import static java.lang.Math.min;

public class ArchetypeAct3InserterNewAge extends ArchetypeBaseDefect {

    private CharBossDefect cB;
    private boolean A19 = AbstractDungeon.ascensionLevel >= 19;

    public ArchetypeAct3InserterNewAge() {
        super("DF_ARCHETYPE_ORBS", "Inserter");

        maxHPModifier += 330;
        actNum = 3;
        bossMechanicName = DefectCuriosityLightningPower.NAME;
        bossMechanicDesc = DefectCuriosityLightningPower.DESCRIPTIONS[0] + 1 + DefectCuriosityLightningPower.DESCRIPTIONS[1];
    }

    @Override
    public void addedPreBattle() {
        super.addedPreBattle();
        AbstractCreature p = AbstractCharBoss.boss;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DefectCuriosityLightningPower(p)));

    }

    public void initialize() {

        addRelic(new CBR_NeowsBlessing());
        addRelic(new CBR_Calipers());
        addRelic(new CBR_IceCream());
        addRelic(new CBR_ChemicalX());
        addRelic(new CBR_Inserter());
        ArchetypeAct3OrbsNewAge.resetPretendFocus();  // In case the player quits mid fight we need to reset masterFocus
    }

    int loops = 0;

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        if (cB == null){
            cB = (CharBossDefect) AbstractCharBoss.boss;
        }
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;

        if (!looped) {
            switch (turn) {
                case 0:
                    // No Orbs
                    addToList(cardsList, new EnFusion(), true);
                    addToList(cardsList, new EnBallLightning(), extraUpgrades);
                    addToList(cardsList, new EnClumsy());
                    // Plasma Lightning
                    turn++;
                    break;
                case 1:
                    //Turn 2 - 3E
                    // Plasma Lightning
                    addToList(cardsList, new EnColdSnap());
                    // Plasma Lightning Frost
                    addToList(cardsList, new EnBarrage(3), extraUpgrades);
                    addToList(cardsList, new EnDefendBlue());
                    turn++;
                    break;
                case 2:
                    //Turn 3 3E
                    // Plasma Lightning Frost
                    addToList(cardsList, new EnDoubleEnergy(3), true);
                    // Lightning Frost
                    addToList(cardsList, new EnRebound());
                    addToList(cardsList, new EnMeteorStrike(), false);
                    // Frost Plasma Plasma Plasma
                    turn++;
                    break;
                case 3:
                    //Turn 4 5E
                    EnDualcast dualcast = new EnDualcast();
//                    dualcast.energyGeneratedIfPlayed = 4; // Safe to assume there will always be enough E
                    addToList(cardsList, dualcast);
                    cB.orbsAsEn().get(0).evokeOverride = true;
                    cB.orbsAsEn().get(0).evokeMult = 2;
                    // Frost Plasma Plasma Plasma 8E
                    addToList(cardsList, new EnMeteorStrike(), false);
                    addToList(cardsList, new EnZap());
                    // Plasma Plasma Plasma Plasma Lightning 6E
                    turn++;
                    break;
                case 4:
                    //Turn 5 12E
                    addToList(cardsList, new EnDefragment(), false);
                    ArchetypeAct3OrbsNewAge.increasePretendFocus(1);
                    addToList(cardsList, new EnThunderStrike(EnThunderStrike.getLightningCount()), false);
                    // Plasma Plasma Plasma Plasma Lightning 8E
                    addToList(cardsList, new EnReinforcedBody(this.cB.energyPanel.getCurrentEnergy() - 4), false); // Actual cost will be calculated in fixUpOrbIntents below
                    // Plasma Plasma Plasma Plasma Lightning 0E
                    turn = 0;
                    looped = true;
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    // first round: Plasma Plasma Plasma Plasma Lightning 6E
                    // second round (turn 10): Frost Plasma Lightning Plasma Plasma Plasma Lightning 16E
                    // second round (turn 14): Plasma Lightning Frost Plasma Lightning Plasma Plasma Plasma Lightning 17E
                    addToList(cardsList, new EnRebound());
                    addToList(cardsList, new EnBarrage(this.cB.filledOrbCount()), extraUpgrades);
                    addToList(cardsList, new EnDefendBlue());
                    turn++;
                    break;
                case 1:
                    // first round: Plasma Plasma Plasma Plasma Lightning 9E
                    // second round (turn 11): Frost Plasma Lightning Plasma Plasma Plasma Lightning 19E
                    addToList(cardsList, new EnColdSnap());
                    // first round: Plasma Plasma Plasma Plasma Lightning Frost 8E
                    addToList(cardsList, new EnBarrage(min(this.cB.maxOrbs, this.cB.filledOrbCount() + 1)), extraUpgrades);
                    addToList(cardsList, new EnReinforcedBody(this.cB.energyPanel.getCurrentEnergy() - 2), false); // Actual cost will be calculated in fixUpOrbIntents below
                    // first round: Plasma Plasma Plasma Plasma Lightning Frost 0E
                    turn++;
                    break;
                case 2:
                    // If no plasma, no zap and Thunderstrike activates
                    // If 1 plasma, 7e for meteor and ball lightning
                    // If 2 plasma, everything

                    // first round: Plasma Plasma Plasma Plasma Lightning Frost 6E
                    // second round (turn 12): Frost Plasma Lightning Plasma Plasma Plasma Lightning Frost 6E
                    addToList(cardsList, new EnDualcast());
                    cB.orbsAsEn().get(0).evokeOverride = true;
                    cB.orbsAsEn().get(0).evokeMult = 2;
                    addToList(cardsList, new EnFusion(), true);
                    addToList(cardsList, new EnBallLightning(), extraUpgrades);
                    // first round: Plasma Plasma Plasma Lightning Frost Plasma Lightning 7E
                    turn++;
                    break;
                case 3:
                    // first round: Plasma Plasma Plasma Lightning Frost Plasma Lightning 13E
                    // second round (turn 13): Plasma Lightning Plasma Plasma Plasma Lightning Frost Plasma Lightning 10E
                    addToList(cardsList, new EnMeteorStrike(), false);
                    // first round: Lightning Frost Plasma Lightning Plasma Plasma Plasma 14E
                    addToList(cardsList, new EnThunderStrike(EnThunderStrike.getLightningCount()), false);
                    addToList(cardsList, new EnZap());
                    // first round: Frost Plasma Lightning Plasma Plasma Plasma Lightning 10E
                    turn = 0;
                    looped = true;
                    loops++;
                    break;
            }
        }
        fixUpOrbIntents(cardsList);
        return cardsList;
    }
    public static void fixUpOrbIntents() {
        fixUpOrbIntents(AbstractCharBoss.boss.hand.group);
    }

    private static void fixUpOrbIntents(Collection<AbstractCard> hand) {
        CharBossDefect cB = (CharBossDefect) AbstractCharBoss.boss;
        int currentE = cB.energyPanel.getCurrentEnergy();
        ArrayList<AbstractEnemyOrb> mockOrbs = new ArrayList<>();
        for (AbstractEnemyOrb orb: cB.orbsAsEn()) {
            if (!(orb instanceof EnemyEmptyOrbSlot))
                mockOrbs.add((AbstractEnemyOrb) orb.makeCopy());
        }
        int orbsEvoked = 0;
        System.out.println(hand);
        System.out.println(mockOrbs);

        for (AbstractCard absCard: hand) {
            AbstractBossCard card = (AbstractBossCard) absCard;
            if (card.cost > currentE) {
                card.bossDarken();
                continue;
            }
            if (card instanceof EnReinforcedBody) {
                card.cost = currentE;
                // Dunno which of these three is needed
                cB.refreshIntentHbLocation();
                card.applyPowers();
                AbstractCharBoss.boss.hand.refreshHandLayout();
                currentE = 0;
            }
            else {
                currentE -= card.cost;
            }
            card.bossLighten();
            currentE += card.energyGeneratedIfPlayed;

            if (card instanceof EnDualcast) {
                if (mockOrbs.get(0) instanceof EnemyPlasma) {
                    currentE += 4;
                    card.energyGeneratedIfPlayed = 4;
                }
                orbsEvoked+=1;
                mockOrbs.remove(0);
            }
            for (int i = 0; i < card.showEvokeOrbCount; i++) {
                mockOrbs.add(new EnemyEmptyOrbSlot()); // empty on assumption you will never be making more orbs than you have slots in a turn (via cards)
            }
            while (mockOrbs.size() > cB.maxOrbs) {
                if (mockOrbs.get(0) instanceof EnemyPlasma) {
                    currentE += 2;
                    card.energyGeneratedIfPlayed += 2;
                }
                orbsEvoked+=1;
                mockOrbs.remove(0);
            }
            System.out.println(mockOrbs);
            System.out.println(card + ": " + card.energyGeneratedIfPlayed);
        }
        for (int i = 0; i < cB.orbsAsEn().size(); i++) {
            cB.orbsAsEn().get(i).evokeOverride = i < orbsEvoked;
        }
    }

    @Override
    public void initializeBonusRelic() {
        addRelic(new CBR_StrikeDummy());
    }
}