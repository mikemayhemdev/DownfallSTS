package charbosses.bosses.Defect.NewAge;

import basemod.ReflectionHacks;
import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Defect.ArchetypeBaseDefect;
import charbosses.bosses.Defect.CharBossDefect;
import charbosses.cards.blue.*;
import charbosses.cards.colorless.EnPanicButton;
import charbosses.cards.colorless.EnSwiftStrike;
import charbosses.cards.curses.EnClumsy;
import charbosses.cards.curses.EnMalfunctioning;
import charbosses.cards.curses.EnShame;
import charbosses.monsters.BronzeOrbWhoReallyLikesDefectForSomeReason;
import charbosses.orbs.AbstractEnemyOrb;
import charbosses.powers.bossmechanicpowers.DefectAncientConstructPower;
import charbosses.relics.*;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.NoBlockPower;

import java.lang.reflect.Method;
import java.util.ArrayList;

import static charbosses.bosses.Defect.NewAge.ArchetypeAct3OrbsNewAge.increasePretendFocus;

public class ArchetypeAct2ClawNewAge extends ArchetypeBaseDefect {

    private EnClaw c;
    private CharBossDefect cB;

    int frostOrbsChanneled = 0;

    public ArchetypeAct2ClawNewAge() {
        super("DF_ARCHETYPE_CLAW", "Claw");

        maxHPModifier += 192;
        actNum = 2;
        bossMechanicName = DefectAncientConstructPower.NAME;
        bossMechanicDesc = DefectAncientConstructPower.DESC[0];
    }

    @Override
    public void addedPreBattle() {
        super.addedPreBattle();
        AbstractCreature p = AbstractCharBoss.boss;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DefectAncientConstructPower(p, 1), 1));
        AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(new BronzeOrbWhoReallyLikesDefectForSomeReason(-450, 250, 0), true));
        AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(new BronzeOrbWhoReallyLikesDefectForSomeReason(-600, 0, 1), true));

    }

    public void initialize() {

        addRelic(new CBR_NeowsBlessing());
        addRelic(new CBR_IceCream());
        addRelic(new CBR_BagOfPreparation());
        addRelic(new CBR_NuclearBattery());


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

        if (!looped) {
            switch (turn) {
                case 0:
                    addToList(cardsList, new EnChill(), true);
                    frostOrbsChanneled += 1;
                    addToList(cardsList, new EnChill(), true);
                    frostOrbsChanneled += 1;
                    addToList(cardsList, new EnBootSequence(), true);  // removed
                    addToList(cardsList, new EnClaw(cB.clawsPlayed * 2), false); //1
                    addToList(cardsList, new EnMachineLearning(), true);  // removed
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new EnDefragment(), true);
                    increasePretendFocus(2);
                    addToList(cardsList, new EnChargeBattery());//2
                    addToList(cardsList, new EnDefendBlue(), false);//4
                    addToList(cardsList, new EnStrikeBlue(), true);//4
                    turn++;
                    break;
                case 2:
                    //Turn 3
                    addToList(cardsList, new EnCoreSurge(), false);//5
                    addToList(cardsList, new EnReprogram(), extraUpgrades);//6
                    addToList(cardsList, new EnRebound(), false);//7
                    addToList(cardsList, new EnClaw(cB.clawsPlayed * 2), false);//8
                    turn++;
                    break;
                case 3:
                    addToList(cardsList, new EnClaw(cB.clawsPlayed * 2), false);//8th
                    addToList(cardsList, new EnChargeBattery(), false);//9th
                    addToList(cardsList, new EnClumsy(), true);//removed
                    turn++;
                    break;
                case 4:
                    addToList(cardsList, new EnCoreSurge(), false);//10
                    addToList(cardsList, new EnHyperbeam(), false);//11
                    addToList(cardsList, new EnClumsy(), false);
                    addToList(cardsList, new EnDefendBlue(), false);//12
                    turn = 0;
                    looped = true;
                    break;
            }
        } else {
            //perfect 12 for a loop
            switch (turn) {
                case 0:
                    addToList(cardsList, new EnClaw(cB.clawsPlayed * 2), false); //1
                    addToList(cardsList, new EnChargeBattery(), false); //2
                    addToList(cardsList, new EnDefendBlue(), false);//4
                    addToList(cardsList, new EnStrikeBlue(), true);//4
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new EnCoreSurge(), false);//5
                    addToList(cardsList, new EnHyperbeam(), false);//6
                    addToList(cardsList, new EnChargeBattery(), false);//7
                    addToList(cardsList,  new EnRebound(), false);//8
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new EnCoreSurge(), false);//9
                    addToList(cardsList, new EnReprogram(), extraUpgrades);//10
                    addToList(cardsList, new EnClaw(cB.clawsPlayed * 2), false);//11
                    addToList(cardsList,  new EnDefendBlue(), false);//12
                    turn = 0;
                    break;
            }
        }

        return cardsList;
    }

    @Override
    public void initializeBonusRelic() {
        addRelic(new CBR_ClockworkSouvenir());
    }
}