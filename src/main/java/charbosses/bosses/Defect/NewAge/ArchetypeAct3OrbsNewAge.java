package charbosses.bosses.Defect.NewAge;

import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Defect.ArchetypeBaseDefect;
import charbosses.bosses.Defect.CharBossDefect;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.blue.*;
import charbosses.cards.colorless.EnBlind;
import charbosses.cards.colorless.EnGoodInstincts;
import charbosses.cards.curses.EnShame;
import charbosses.orbs.AbstractEnemyOrb;
import charbosses.powers.bossmechanicpowers.DefectBiasCuriosityPower;
import charbosses.relics.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.FocusPower;

import java.util.ArrayList;

public class ArchetypeAct3OrbsNewAge extends ArchetypeBaseDefect {

    private EnClaw c;
    private CharBossDefect cB;
    private boolean A19 = AbstractDungeon.ascensionLevel >= 19;

    public ArchetypeAct3OrbsNewAge() {
        super("DF_ARCHETYPE_ORBS", "Orbs");

        maxHPModifier += 350;
        actNum = 3;
        bossMechanicName = DefectBiasCuriosityPower.NAME;
        bossMechanicDesc = DefectBiasCuriosityPower.DESCRIPTIONS[0];
    }

    @Override
    public void addedPreBattle() {
        super.addedPreBattle();
        AbstractCreature p = AbstractCharBoss.boss;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FocusPower(p,4)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DefectBiasCuriosityPower(p)));
        if(AbstractDungeon.ascensionLevel >= 19)
        AbstractDungeon.actionManager.addToBottom(new HealAction(p, p, 2));
    }

    public void initialize() {

        addRelic(new CBR_NeowsBlessing());
        addRelic(new CBR_ArtOfWar());
        addRelic(new CBR_Lantern());
        addRelic(new CBR_IceCream());
        addRelic(new CBR_FusionHammer());
    }


    public static void increasePretendFocus(int amount) {
        AbstractEnemyOrb.masterPretendFocus += amount;
        for (AbstractOrb o : AbstractCharBoss.boss.orbs) {
            if (o instanceof AbstractEnemyOrb) {
                ((AbstractEnemyOrb) o).pretendFocus += amount;
                o.applyFocus();
                //((AbstractEnemyOrb) o).applyLockOn();
            }
        }
    }

    public static void resetPretendFocus() {
        for (AbstractOrb o : AbstractCharBoss.boss.orbs) {
            if (o instanceof AbstractEnemyOrb) {
                ((AbstractEnemyOrb) o).pretendFocus = 0;
                AbstractEnemyOrb.masterPretendFocus = 0;
                o.applyFocus();
                //((AbstractEnemyOrb) o).applyLockOn();
            }
        }
    }




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
                    // No Orbs 4e

                    addToList(cardsList, new EnMachineLearning(),true);
                    addToList(cardsList, new EnChargeBattery());
                    addToList(cardsList, new EnRainbow());
                    // Lightning Frost Dark 0e
                    turn++;
                    break;
                case 1:
                    //Turn 2
                    // Lightning Frost Dark 5e
                    addToList(cardsList, new EnDualcast());
                    // Evokes Lightning 4e
                    cB.orbsAsEn().get(0).evokeOverride = true;
                    cB.orbsAsEn().get(0).evokeMult = 2;
                    addToList(cardsList, new EnHologram(), true);
                    // Frost Dark 3e
                    addToList(cardsList, new EnDualcast());
                    // Evokes Frost 2e
                    cB.orbsAsEn().get(1).evokeOverride = true;
                    cB.orbsAsEn().get(1).evokeMult = 2;
                    addToList(cardsList, new EnStorm());
                    AbstractBossCard.fakeStormPower = true;
                    // Dark 1e
                    // Evokes Dark
                        addToList(cardsList, new EnMulticast(1));
                        cB.orbsAsEn().get(2).evokeOverride = true;
                        cB.orbsAsEn().get(2).evokeMult = 1;
                    // No Orbs 0e
                    turn++;
                    break;
                case 2:
                    //Turn 3
                    // No Orbs 4e
                    addToList(cardsList, new EnConsume());
                    increasePretendFocus(2);
                    addToList(cardsList, new EnCapacitor());
                    //Lightning 1e
                    addToList(cardsList, new EnColdSnap(),extraUpgrades);
                    //Lightning Frost 0e

                    addToList(cardsList, new EnShame());
                    turn++;
                    break;
                case 3:
                    //Turn 4
                    //Lightning Frost 3e
                    addToList(cardsList, new EnDoubleEnergy(),false);
                    // 4e
                    addToList(cardsList, new EnCreativeAI(),false);
                    //Lightning Frost Lightning 1e
                    addToList(cardsList, new EnFusion(true,false),true);
                    //Lightning Frost Lightning Plasma 0e
                    addToList(cardsList, new EnForceField());
                    turn = 0;
                    looped = true;
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    //Lightning Frost Lightning Plasma 5e
                    addToList(cardsList, new EnBuffer(true), false);//AI
                    cB.orbsAsEn().get(0).evokeOverride = true;
                    cB.orbsAsEn().get(0).evokeMult = 1;
                    // Evokes Lightning
                    //Frost Lightning Plasma Lightning 3e
                    addToList(cardsList, new EnGoodInstincts());
                    addToList(cardsList, new EnBarrage(4));
                    addToList(cardsList, new EnColdSnap(),extraUpgrades);
                    cB.orbsAsEn().get(1).evokeOverride = true;
                    cB.orbsAsEn().get(1).evokeMult = 1;
                    //Lightning Plasma Lightning Frost 1e
                    addToList(cardsList, new EnChargeBattery());

                    //Lightning Plasma Lightning Frost 0e
                    turn++;
                    break;
                case 1:
                    //Lightning Plasma Lightning Frost 5e
                    addToList(cardsList, new EnElectrodynamics(), false);//AI
                    cB.orbsAsEn().get(0).evokeOverride = true;
                    cB.orbsAsEn().get(0).evokeMult = 1;
                    cB.orbsAsEn().get(1).evokeOverride = true;
                    cB.orbsAsEn().get(1).evokeMult = 1;
                    cB.orbsAsEn().get(2).evokeOverride = true;
                    cB.orbsAsEn().get(2).evokeMult = 1;
                    //Frost Lightning Lightning Lightning 5e
                    addToList(cardsList, new EnShame());
                    addToList(cardsList, new EnDualcast());
                    //Evokes Frost 4e
                    cB.orbsAsEn().get(3).evokeOverride = true;
                    cB.orbsAsEn().get(3).evokeMult = 2;
                    //Lightning Lightning Lightning 4e
                    addToList(cardsList, new EnFusion(true,false),true);
                    addToList(cardsList, new EnForceField());
                    //Lightning Lightning Lightning Plasma 3e
                    turn++;
                    break;
                case 2:
                    //Lightning Lightning Lightning Plasma
                    addToList(cardsList, new EnDefragment(), false);//AI
                    increasePretendFocus(1);
                    cB.orbsAsEn().get(0).evokeOverride = true;
                    cB.orbsAsEn().get(0).evokeMult = 1;
                    //Lightning Lightning Plasma Lightning
                    addToList(cardsList, new EnColdSnap(), false);
                    cB.orbsAsEn().get(1).evokeOverride = true;
                    cB.orbsAsEn().get(1).evokeMult = 1;
                    //Lightning Plasma Lightning Frost
                    addToList(cardsList, new EnHologram(), true);
                    addToList(cardsList, new EnDualcast());
                    cB.orbsAsEn().get(2).evokeOverride = true;
                    cB.orbsAsEn().get(2).evokeMult = 2;
                    //Plasma Lightning Frost
                    addToList(cardsList, new EnChargeBattery());
                    addToList(cardsList, new EnGoodInstincts());
                    cB.orbsAsEn().get(1).evokeOverride = true;
                    cB.orbsAsEn().get(1).evokeMult = 1;
                    //Plasma Lightning Frost
                    turn ++;
                    break;
                case 3:
                    //Plasma Lightning Frost
                    addToList(cardsList, new EnCapacitor(), false);//AI
                    //Plasma Lightning Frost Lightning
                    addToList(cardsList, new EnMulticast(cB.energyPanel.getCurrentEnergy()-1),false,2*(cB.energyPanel.getCurrentEnergy()-1));
                    //Evokes Plasma
                    cB.orbsAsEn().get(0).evokeOverride = true;
                    cB.orbsAsEn().get(0).evokeMult = 4;
                    addToList(cardsList, new EnConsume());
                    increasePretendFocus(2);
                    //Lightning Frost Lightning
                    addToList(cardsList, new EnFusion(true,false),true);
                    //Lightning Frost Lightning Plasma

                    addToList(cardsList, new EnHologram(), true);
                    addToList(cardsList, new EnConsume());
                    increasePretendFocus(2);
                    //Lightning Frost Lightning Plasma. Perfect loop! :D
                    turn = 0;
                    looped = true;
                    break;
            }
        }

        return cardsList;
    }

    @Override
    public void initializeBonusRelic() {
        addRelic(new CBR_BirdFacedUrn());
    }
}