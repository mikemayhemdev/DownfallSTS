package charbosses.bosses.Defect.NewAge;

import basemod.ReflectionHacks;
import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Defect.ArchetypeBaseDefect;
import charbosses.bosses.Defect.CharBossDefect;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.blue.*;
import charbosses.cards.colorless.EnPanicButton;
import charbosses.cards.colorless.EnSwiftStrike;
import charbosses.cards.curses.EnClumsy;
import charbosses.cards.curses.EnMalfunctioning;
import charbosses.cards.curses.EnShame;
import charbosses.monsters.BronzeOrbWhoReallyLikesDefectForSomeReason;
import charbosses.orbs.AbstractEnemyOrb;
import charbosses.powers.bossmechanicpowers.DefectAncientConstructPower;
import charbosses.powers.bossmechanicpowers.DefectVoidPower;
import charbosses.relics.*;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.NoBlockPower;
import guardian.powers.ConstructPower;

import java.lang.reflect.Method;
import java.util.ArrayList;

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
        addRelic(new CBR_Kunai());
        addRelic(new CBR_RedMask());
        addRelic(new CBR_Turnip());
        //addRelic(new CBR_BlueCandle());

        // animation
        try {
            Method loadAnimationMethod = AbstractCreature.class.getDeclaredMethod("loadAnimation", new Class[] { String.class, String.class, float.class });
            loadAnimationMethod.setAccessible(true);
            loadAnimationMethod.invoke(AbstractCharBoss.boss, new Object[] { "expansioncontentResources/images/bosses/defect/2/clockworkDefect.atlas", "expansioncontentResources/images/bosses/defect/2/clockworkDefect.json", 1.0f });
            //ReflectionHacks.privateMethod(AbstractCharBoss.class, "loadAnimation", String.class, String.class, float.class).invoke(AbstractCharBoss.boss, "expansioncontentResources/images/bosses/ironclad/2/mushroomclad.atlas", "expansioncontentResources/images/bosses/ironclad/2/mushroomclad.json", 1.0f);
            AnimationState.TrackEntry e = AbstractCharBoss.boss.state.setAnimation(0, "Idle", true);
            ((AnimationStateData)ReflectionHacks.getPrivate(AbstractCharBoss.boss, AbstractCharBoss.class, "stateData")).setMix("Hit", "Idle", 0.1f);
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
                    // NO Orbs
                    addToList(cardsList, new EnBootSequence(), false);  // removed
                    addToList(cardsList, new EnClaw(cB.clawsPlayed * 2), false);
                    addToList(cardsList, new EnMachineLearning(), true);  // removed
                    turn++;
                    // No Orbs
                    break;
                case 1:
                    //Turn 2
                    // No Orbs
                    addToList(cardsList, new EnLeap());
                    addToList(cardsList, new EnColdSnap(), true);
                    frostOrbsChanneled += 1;
                    addToList(cardsList, new EnPanicButton());  // removed
                    addToList(cardsList, new EnStrikeBlue(), true);
                    // Frost
                    turn++;
                    break;
                case 2:
                    //Turn 3
                    // Frost
                    addToList(cardsList, new EnReprogram(), false);
                    ArchetypeAct3OrbsNewAge.increasePretendFocus(-1);
                    addToList(cardsList, new EnSwiftStrike(), false);
                    addToList(cardsList, new EnRebound(), false);
                    addToList(cardsList, new EnClaw(cB.clawsPlayed * 2));
                    // Frost
                    //Kunai Proc
                    turn++;
                    break;
                case 3:
                    //Turn 4
                    // Frost
                    addToList(cardsList, new EnClaw(cB.clawsPlayed * 2));
                    addToList(cardsList, new EnChargeBattery(), false);
                    addToList(cardsList, new EnGeneticAlgorithm(14), true);  //removed
                    addToList(cardsList, new EnShame(), false);
                    // Frost
                    turn++;
                    break;
                case 4:
                    //Turn 5
                    // Frost
                    addToList(cardsList, new EnHyperbeam(), extraUpgrades);
                    ArchetypeAct3OrbsNewAge.increasePretendFocus(-3);
                    addToList(cardsList, new EnMalfunctioning(), false);
                    addToList(cardsList, new EnDefendBlue(), false);
                    addToList(cardsList, new EnClumsy(), false);  //removed
                    //Frost, but it's useless
                    turn = 0;
                    looped = true;
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    addToList(cardsList, new EnClaw(cB.clawsPlayed * 2), false);
                    //Play Leap if Block can be gained, otherwise play Stirike
                    if (AbstractCharBoss.boss.hasPower(NoBlockPower.POWER_ID)){
                        addToList(cardsList, new EnColdSnap(), true);
                        frostOrbsChanneled += 1;
                        if (frostOrbsChanneled > 3 && AbstractCharBoss.boss.orbs.get(0) instanceof AbstractEnemyOrb) {
                            ((AbstractEnemyOrb) AbstractCharBoss.boss.orbs.get(0)).evokeOverride = true;
                        }
                        addToList(cardsList, new EnStrikeBlue(), true);
                        addToList(cardsList, new EnLeap());
                    } else {
                        addToList(cardsList, new EnColdSnap(), true);
                        frostOrbsChanneled += 1;
                        if (frostOrbsChanneled > 3 && AbstractCharBoss.boss.orbs.get(0) instanceof AbstractEnemyOrb) {
                            ((AbstractEnemyOrb) AbstractCharBoss.boss.orbs.get(0)).evokeOverride = true;
                        }
                        addToList(cardsList, new EnLeap());
                        addToList(cardsList, new EnStrikeBlue(), true);
                    }
                    //Kunai Proc
                    turn++;
                    break;
                case 1:
                    //Turn 3
                    addToList(cardsList, new EnHyperbeam(), extraUpgrades);
                    ArchetypeAct3OrbsNewAge.increasePretendFocus(-3);
                    addToList(cardsList, new EnChargeBattery(), false);
                    addToList(cardsList, new EnShame(), false);
                    addToList(cardsList,  new EnRebound(), false);
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new EnReprogram(), false);
                    ArchetypeAct3OrbsNewAge.increasePretendFocus(-1);
                    addToList(cardsList, new EnClaw(cB.clawsPlayed * 2), false);
                    addToList(cardsList,  new EnDefendBlue(), false);
                    addToList(cardsList, new EnSwiftStrike(), false);
                    //Kunai Proc
                    turn = 0;
                    break;
            }

        }

        return cardsList;
    }

    @Override
    public void initializeBonusRelic() {
        addRelic(new CBR_Ginger());
    }
}