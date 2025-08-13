package charbosses.bosses.Defect.Simpler;

import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Defect.ArchetypeBaseDefect;
import charbosses.bosses.Defect.CharBossDefect;
import charbosses.cards.blue.*;
import charbosses.orbs.AbstractEnemyOrb;
import charbosses.powers.bossmechanicpowers.DefectCuriosityPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import expansioncontent.expansionContentMod;

import java.util.ArrayList;

public class ArchetypeAct3OrbsSimple extends ArchetypeBaseDefect {

    private CharBossDefect cB;

    public ArchetypeAct3OrbsSimple() {
        super("DF_ARCHETYPE_ORBS", "Orbs");

        maxHPModifier += 300;
        actNum = 3;
       }

    @Override
    public void addedPreBattle() {
        super.addedPreBattle();
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

            switch (turn) {
                case 0:
                    if (!looped) addToList(cardsList, new EnCuriousRobot());
                    addToList(cardsList, new EnTemptation());
                    turn++;
                    break;
                case 1:
                    increasePretendFocus(-1);
                    addToList(cardsList, new EnColdSnap(),extraUpgrades);
                    addToList(cardsList, new EnBarrage());

                    turn++;
                    break;
                case 2:
                    increasePretendFocus(-1);
                    addToList(cardsList, new EnDefragment(), extraUpgrades);
                    increasePretendFocus(2);
                    addToList(cardsList, new EnDualcast());
                    cB.orbsAsEn().get(0).evokeOverride = true;
                    cB.orbsAsEn().get(0).evokeMult = 2;
                    turn++;
                    break;
                case 3:
                    increasePretendFocus(-1);
                    //Turn 4
                    //Lightning Frost 3e
                    addToList(cardsList, new EnGlacier(),false);

                    turn++;
                    break;
                case 4:
                    increasePretendFocus(-1);
                    //Turn 4
                    //Lightning Frost 3e
                    addToList(cardsList, new EnBallLightning(),extraUpgrades);
                    addToList(cardsList, new EnBallLightning(),false);
                    cB.orbsAsEn().get(0).evokeOverride = true;
                    cB.orbsAsEn().get(0).evokeMult = 1;
                    cB.orbsAsEn().get(1).evokeOverride = true;
                    cB.orbsAsEn().get(1).evokeMult = 1;

                    turn++;
                    break;
                case 5:
                    increasePretendFocus(-1);
                    //Turn 4
                    //Lightning Frost 3e
                    addToList(cardsList, new EnBiasedCognition(),false);
                    addToList(cardsList, new EnDualcast());
                    cB.orbsAsEn().get(0).evokeOverride = true;
                    cB.orbsAsEn().get(0).evokeMult = 2;
                    turn = 0;
                    looped = true;
                    break;
            }


        return cardsList;
    }

}