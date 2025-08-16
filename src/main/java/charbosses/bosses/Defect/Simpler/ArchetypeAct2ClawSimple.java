package charbosses.bosses.Defect.Simpler;

import basemod.ReflectionHacks;
import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Defect.ArchetypeBaseDefect;
import charbosses.bosses.Defect.CharBossDefect;
import charbosses.cards.blue.*;
import charbosses.cards.colorless.EnPanacea;
import charbosses.monsters.BronzeOrbWhoReallyLikesDefectForSomeReason;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.lang.reflect.Method;
import java.util.ArrayList;

import static charbosses.bosses.Defect.NewAge.ArchetypeAct3OrbsNewAge.increasePretendFocus;

public class ArchetypeAct2ClawSimple extends ArchetypeBaseDefect {

    private CharBossDefect cB;

    int frostOrbsChanneled = 0;

    public ArchetypeAct2ClawSimple() {
        super("DF_ARCHETYPE_CLAW", "Claw");

        maxHPModifier += 192;
        actNum = 2;
    }

    @Override
    public void addedPreBattle() {
        super.addedPreBattle();
        AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(new BronzeOrbWhoReallyLikesDefectForSomeReason(-450, 250, 0), true));
        AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(new BronzeOrbWhoReallyLikesDefectForSomeReason(-600, 0, 1), true));

    }

    public void initialize() {

        // animation
        try {
            Method loadAnimationMethod = AbstractCreature.class.getDeclaredMethod("loadAnimation", new Class[] { String.class, String.class, float.class });
            loadAnimationMethod.setAccessible(true);
            loadAnimationMethod.invoke(AbstractCharBoss.boss, new Object[] { "expansioncontentResources/images/bosses/defect/2/clockworkDefect.atlas", "expansioncontentResources/images/bosses/defect/2/clockworkDefect.json", 1.0f });
            AnimationState.TrackEntry e = AbstractCharBoss.boss.state.setAnimation(0, "Idle", true);
            ((AnimationStateData)ReflectionHacks.getPrivate(AbstractCharBoss.boss, AbstractCreature.class, "stateData")).setMix("Hit", "Idle", 0.1f);
            e.setTimeScale(0.9f);
        } catch (Exception e) {
            e.printStackTrace();
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
                    if (looped){
                        addToList(cardsList, new EnClaw(cB.clawsPlayed * 2), false);//8th
                        addToList(cardsList, new EnClaw((cB.clawsPlayed * 2+2)), false);//8th
                    } else {
                        addToList(cardsList, new EnChill(), true);
                        addToList(cardsList, new EnChill(), true);
                        addToList(cardsList, new EnClaw(cB.clawsPlayed * 2), false); //1
                    }
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new EnDefragment(), extraUpgrades);
                    addToList(cardsList, new EnPanacea(), true);
                    increasePretendFocus(2);
                    turn++;
                    break;
                case 2:
                    //Turn 3
                    addToList(cardsList, new EnReprogram(), true);//6
                    turn++;
                    break;
                case 3:
                    addToList(cardsList, new EnClaw(cB.clawsPlayed * 2), false);//8th
                    addToList(cardsList, new EnClaw((cB.clawsPlayed * 2+2)), false);//8th
                    turn++;
                    break;
                case 4:
                    addToList(cardsList, new EnCoreSurge(), false);//5
                    addToList(cardsList, new EnGeneticAlgorithm(14), true);  //removed
                    turn++;
                    break;
                case 5:
                    //addToList(cardsList, new EnCoreSurge(), false);//5
                    addToList(cardsList, new EnHyperbeam(), extraUpgrades);  //removed
                    turn = 0;
                    looped = true;
                    break;
            }


        return cardsList;
    }

}