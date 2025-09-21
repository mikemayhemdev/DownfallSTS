package charbosses.bosses.Defect.Simpler;

import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Defect.ArchetypeBaseDefect;
import charbosses.bosses.Defect.CharBossDefect;
import charbosses.cards.blue.*;
import charbosses.cards.hermit.EnDefendHermit;
import charbosses.cards.hermit.EnStrikeHermit;
import charbosses.orbs.AbstractEnemyOrb;
import charbosses.powers.bossmechanicpowers.DefectCuriosityPower;
import charbosses.relics.CBR_Anchor;
import charbosses.relics.CBR_Calipers;
import charbosses.relics.CBR_RunicCapacitor;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import expansioncontent.expansionContentMod;

import java.util.ArrayList;
import java.util.Collections;

public class ArchetypeAct3OrbsSimple extends ArchetypeBaseDefect {

    private CharBossDefect cB;
    public    ArrayList<AbstractCard> turn23order = new ArrayList<>();

    public ArchetypeAct3OrbsSimple() {
        super("DF_ARCHETYPE_ORBS", "Orbs");

        maxHPModifier += 295;
        maxHPModifierAsc = 30;
        actNum = 3;
       }

    @Override
    public void addedPreBattle() {
        super.addedPreBattle();

        AbstractCreature m = AbstractCharBoss.boss;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new DefectCuriosityPower(m)));

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

    //When you play a Power, Defect gains 1 Focus. Starts with Biased Cognition.
    //

public void shuffleTurns(){

    turn23order.clear();
    turn23order.add(new EnColdSnap());
    turn23order.add(new EnColdSnap());
    turn23order.add(new EnBallLightning());
    turn23order.add(new EnBallLightning());
    Collections.shuffle(turn23order, AbstractDungeon.cardRandomRng.random);
}



    public void initialize() {

        //Gold plated cables
        if (AbstractDungeon.ascensionLevel >= 19){
            addRelic(new CBR_RunicCapacitor());
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
                    shuffleTurns();
                    if (extraUpgrades) //Fire Potion
                    addToList(cardsList, new EnBiasedCognition());
                    addToList(cardsList, new EnTemptation());
                    turn++;
                    break;
                case 1:
                    increasePretendFocus(-1);
                    addToList(cardsList, turn23order.get(0), false);
                    addToList(cardsList, turn23order.get(1), false);
                    turn++;
                    break;
                case 2:
                    increasePretendFocus(-1);
                    addToList(cardsList, new EnDefragment(), true);
                    increasePretendFocus(2);
                    addToList(cardsList, new EnDualcast());
                    cB.orbsAsEn().get(0).evokeOverride = true;
                    cB.orbsAsEn().get(0).evokeMult = 2;
                    turn++;
                    break;
                case 3:
                    increasePretendFocus(-1);
                    addToList(cardsList, turn23order.get(2), false);
                    addToList(cardsList, turn23order.get(3), false);
                    turn++;
                    break;
                case 4:
                    increasePretendFocus(-1);
                    addToList(cardsList, new EnThunderStrike(), false);
                    //Go for the Eyes
                    turn++;
                    break;
            }


        return cardsList;
    }

}