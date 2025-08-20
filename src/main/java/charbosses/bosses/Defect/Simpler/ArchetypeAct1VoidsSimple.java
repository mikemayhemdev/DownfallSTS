package charbosses.bosses.Defect.Simpler;

import basemod.ReflectionHacks;
import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Defect.ArchetypeBaseDefect;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.blue.*;
import charbosses.cards.colorless.EnPowerUp;
import charbosses.cards.curses.EnDecay;
import charbosses.cards.curses.EnInjury;
import charbosses.cards.curses.EnVoid;
import charbosses.monsters.LouseTangerine;
import charbosses.monsters.VoidCore;
import charbosses.powers.bossmechanicpowers.DefectAttackVoidPower;
import charbosses.powers.bossmechanicpowers.DefectVoidPower;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class ArchetypeAct1VoidsSimple extends ArchetypeBaseDefect {

    int darkOrbsChanneled = 0;

    boolean doubleLooped = false;
    public boolean minionDestroyedThisTurn = false;

    public ArchetypeAct1VoidsSimple() {
        super("DF_ARCHETYPE_STREAMLINE", "Streamline");
        maxHPModifier += 85;
        maxHPModifierAsc = 10;
        actNum = 1;
    }

    @Override
    public void addedPreBattle() {

        AbstractCreature m = AbstractCharBoss.boss;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new DefectAttackVoidPower(m)));
        AbstractMonster voidCore = new VoidCore(-400F, 200F);
        AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(voidCore,true));
        voidCore.usePreBattleAction();

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

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;
       // boolean beamCells = AbstractDungeon.ascensionLevel >= 4;
            switch (turn) {
                case 0:
                    if (!looped || !doubleLooped){
                        addToList(cardsList, new EnConsume(), false);
                        addToList(cardsList, new EnDoomAndGloom(), extraUpgrades);
                    } else {
                        addToList(cardsList, new EnDoomAndGloom(), extraUpgrades);
                        addToList(cardsList, new EnStrikeBlue());
                    }
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new EnLeap());
                    addToList(cardsList, new EnLeap());
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new EnRipAndTear(), false);
                    addToList(cardsList, new EnDarkness(), extraUpgrades);
                    turn++;
                    break;
                case 3:
                    addToList(cardsList, new EnBuffer(), extraUpgrades);
                    addToList(cardsList, new EnLoop(), false);
                    turn = 0;
                    if (looped) doubleLooped = true;
                    looped = true;
                    break;
            }



        return cardsList;
    }




}