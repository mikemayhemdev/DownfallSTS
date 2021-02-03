package charbosses.bosses.Defect.NewAge;

import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Defect.ArchetypeBaseDefect;
import charbosses.bosses.Defect.CharBossDefect;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.blue.*;
import charbosses.cards.colorless.EnBlind;
import charbosses.orbs.AbstractEnemyOrb;
import charbosses.powers.bossmechanicpowers.DefectCuriosityPower;
import charbosses.relics.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import java.util.ArrayList;

public class ArchetypeAct3OrbsNewAge extends ArchetypeBaseDefect {

    private EnClaw c;
    private CharBossDefect cB;
    private boolean A19 = AbstractDungeon.ascensionLevel >= 19;

    public ArchetypeAct3OrbsNewAge() {
        super("DF_ARCHETYPE_ORBS", "Orbs");

        maxHPModifier += 350;
        actNum = 3;
    }

    @Override
    public void addedPreBattle() {
        super.addedPreBattle();
        AbstractCreature p = AbstractCharBoss.boss;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DefectCuriosityPower(p)));

    }

    public static void increasePretendFocus(int amount) {
        for (AbstractOrb o : AbstractCharBoss.boss.orbs) {
            if (o instanceof AbstractEnemyOrb) {
                ((AbstractEnemyOrb) o).pretendFocus += amount;
                AbstractEnemyOrb.masterPretendFocus += amount;
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

    public void initialize() {

        addRelic(new CBR_NeowsBlessing());
        addRelic(new CBR_DataDisk());
        addRelic(new CBR_Lantern());
        addRelic(new CBR_FossilizedHelix());
        addRelic(new CBR_PhilosopherStone());

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
                    // No Orbs
                    addToList(cardsList, new EnRainbow());
                    addToList(cardsList, new EnReinforcedBody());
                    addToList(cardsList, new EnChargeBattery());
                    // Lightning Frost Dark
                    turn++;
                    break;
                case 1:
                    //Turn 2
                    // Lightning Frost Dark
                    addToList(cardsList, new EnZap(), true);
                    //Evokes Lightning
                    cB.orbsAsEn().get(0).evokeOverride = true;
                    // Frost Dark Lightning
                    addToList(cardsList, new EnDualcast());
                    // Evokes Frost
                    cB.orbsAsEn().get(1).evokeOverride = true;
                    cB.orbsAsEn().get(1).evokeMult = 2;
                    // Dark Lightning
                    // Evokes Dark
                    if (!A19) {
                        addToList(cardsList, new EnMulticast(2));
                        cB.orbsAsEn().get(2).evokeOverride = true;
                        cB.orbsAsEn().get(2).evokeMult = 2;
                    }
                    else {
                        addToList(cardsList, new EnMulticast(3));
                        cB.orbsAsEn().get(2).evokeOverride = true;
                        cB.orbsAsEn().get(2).evokeMult = 3;
                    }
                    // Lightning
                    turn++;
                    break;
                case 2:
                    //Turn 3
                    //Lightning
                    addToList(cardsList, new EnColdSnap());
                    addToList(cardsList, new EnBarrage(), true); // Bullseye was here, but Bullseye is a cursed thing
                    //Lightning Frost
                    addToList(cardsList, new EnLeap());
                    turn++;
                    break;
                case 3:
                    //Turn 4
                    //Lightning Frost
                    addToList(cardsList, new EnStorm(), false);
                    AbstractBossCard.fakeStormPower = true;
                    addToList(cardsList, new EnDefragment(), true);
                    increasePretendFocus(2);
                    //Lightning Frost Lightning
                    addToList(cardsList, new EnBlind(), extraUpgrades);
                    turn = 0;
                    looped = true;
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    //Lightning Frost Lightning
                    addToList(cardsList, new EnLeap());
                    addToList(cardsList, new EnChargeBattery());
                    addToList(cardsList, new EnBarrage(), true); //Vex replaced Bullseye with Barrage+ here.
                    turn++;
                    break;
                case 1:
                    //Lightning Frost Lightning
                    addToList(cardsList, new EnBlind(), true);
                    addToList(cardsList, new EnDualcast());
                    //Evokes Lightning
                    cB.orbsAsEn().get(0).evokeOverride = true;
                    cB.orbsAsEn().get(0).evokeMult = 2;
                    //Lightning Frost
                    addToList(cardsList, new EnMulticast(3));
                    // Evokes Frost!
                    cB.orbsAsEn().get(1).evokeOverride = true;
                    cB.orbsAsEn().get(1).evokeMult = 3;
                    // Lightning
                    turn++;
                    break;
                case 2:
                    // Lightning
                    addToList(cardsList, new EnColdSnap(), false);
                    //Lightning Frost
                    addToList(cardsList, new EnZap(), true);
                    // Lightning Frost Lightning. Perfect loop! :D
                    if (!A19)
                        addToList(cardsList, new EnReinforcedBody());
                    else
                        addToList(cardsList, new EnReinforcedBody(3));
                    turn = 0;
                    looped = true;
                    break;
            }
        }

        return cardsList;
    }

    @Override
    public void initializeBonusRelic() {
        addRelic(new CBR_ArtOfWar());
    }
}