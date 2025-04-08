package charbosses.bosses.Defect.NewAge;

import basemod.ReflectionHacks;
import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Defect.ArchetypeBaseDefect;
import charbosses.cards.blue.*;
import charbosses.cards.curses.EnAged;
import charbosses.cards.curses.EnClumsy;
import charbosses.cards.curses.EnInjury;
import charbosses.cards.curses.EnShame;
import charbosses.orbs.AbstractEnemyOrb;
import charbosses.powers.bossmechanicpowers.DefectVoidPower;
import charbosses.relics.CBR_ClockworkSouvenir;
import charbosses.relics.CBR_FossilizedHelix;
import charbosses.relics.CBR_NeowsBlessing;
import charbosses.relics.CBR_SymbioticVirus;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import slimebound.SlimeboundMod;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class ArchetypeAct1TurboNewAge extends ArchetypeBaseDefect {

    int darkOrbsChanneled = 0;

    public ArchetypeAct1TurboNewAge() {
        super("DF_ARCHETYPE_STREAMLINE", "Streamline");
        maxHPModifier += 102;
        actNum = 1;
        SlimeboundMod.logger.info("Archetype act num: " + actNum);
        bossMechanicName = DefectVoidPower.NAME;
        bossMechanicDesc = DefectVoidPower.DESC[0];
    }

    @Override
    public void addedPreBattle() {
        super.addedPreBattle();
        AbstractCreature p = AbstractCharBoss.boss;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DefectVoidPower(p)));

        try {
            Method loadAnimationMethod = AbstractCreature.class.getDeclaredMethod("loadAnimation", new Class[] { String.class, String.class, float.class });
            loadAnimationMethod.setAccessible(true);
            loadAnimationMethod.invoke(AbstractCharBoss.boss, new Object[] { "expansioncontentResources/images/bosses/defect/1/Defect_thief.atlas", "expansioncontentResources/images/bosses/defect/1/Defect_thief.json", 1.0f });
            AnimationState.TrackEntry e = AbstractCharBoss.boss.state.setAnimation(0, "Idle", true);
            ((AnimationStateData) ReflectionHacks.getPrivate(AbstractCharBoss.boss, AbstractCreature.class, "stateData")).setMix("Hit", "Idle", 0.1f);
            e.setTimeScale(0.9f);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private int steamBarrierCasts;

    public void initialize() {
        steamBarrierCasts = 0;
        addRelic(new CBR_NeowsBlessing());

        //data disk
        addRelic(new CBR_FossilizedHelix());
        addRelic(new CBR_ClockworkSouvenir());
        //addRelic(new CBR_LetterOpener());
      //  addRelic(new CBR_Abacus());
        // addRelic(new CBR_Transmogrifier());  //Could be something else, no strong lean in any direction
    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;
        if (!looped) {
            switch (turn) {
                case 0:
                    addToList(cardsList, new EnBuffer(), false);
                    addToList(cardsList, new EnDoomAndGloom(), extraUpgrades);
                    addToList(cardsList, new EnInjury(), false);
                    turn++;
                    break;
                case 1:
                    //Turn 2
                    addToList(cardsList, new EnSteamBarrier(), false);
                    addToList(cardsList, new EnDefendBlue(), false);
                    addToList(cardsList, new EnClumsy(), false);
                    turn++;
                    steamBarrierCasts++;
                    break;
                case 2:
                    //Turn 3
                    addToList(cardsList, new EnTurbo(), true);
                    addToList(cardsList, new EnSunder(), false);
                    addToList(cardsList, new EnDefendBlue());
                    turn++;
                    break;
                case 3:
                    //Turn 4
                    addToList(cardsList, new EnStrikeBlue(), false);
                    addToList(cardsList, new EnCoreSurge(), false);
                    addToList(cardsList, new EnEquilibrium());
                    turn = 0;
                    looped = true;
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    addToList(cardsList, new EnDoomAndGloom(), extraUpgrades);
                    darkOrbsChanneled += 1;
                    if (darkOrbsChanneled > 3 && AbstractCharBoss.boss.orbs.get(0) instanceof AbstractEnemyOrb) {
                        ((AbstractEnemyOrb) AbstractCharBoss.boss.orbs.get(0)).evokeOverride = true;
                    }
                    addToList(cardsList, new EnDefendBlue());
                    addToList(cardsList, new EnInjury(), false);
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new EnTurbo(), true);
                    addToList(cardsList, new EnSunder(), false);
                    addToList(cardsList, new EnEquilibrium());
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new EnDefendBlue(), false);
                    addToList(cardsList, new EnStrikeBlue(), false);
                    addToList(cardsList, new EnSteamBarrier(steamBarrierCasts), false);
                    steamBarrierCasts++;
                    turn = 0;
                    break;

            }
        }

        return cardsList;
    }


    @Override
    public void initializeBonusRelic() {
        addRelic(new CBR_SymbioticVirus());
        darkOrbsChanneled += 1;
    }

}