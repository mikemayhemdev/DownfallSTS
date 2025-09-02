package charbosses.bosses.Watcher.Simpler;

import basemod.ReflectionHacks;
import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Watcher.ArchetypeBaseWatcher;
import charbosses.bosses.Watcher.CharBossWatcher;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.colorless.EnApparition;
import charbosses.cards.colorless.EnBlind;
import charbosses.cards.purple.*;
import charbosses.monsters.VoidCore;
import charbosses.powers.bossmechanicpowers.DefectAttackVoidPower;
import charbosses.powers.bossmechanicpowers.WatcherDivinityNoRemovePower;
import charbosses.powers.bossmechanicpowers.WatcherDivinityPower;
import charbosses.stances.EnDivinityStance;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePower;
import com.megacrit.cardcrawl.vfx.combat.DevotionEffect;
import hermit.util.Wiz;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class ArchetypeAct3DivinitySimple extends ArchetypeBaseWatcher {

    public boolean halfHealthTrigger = false;
    public boolean hasHalfHealthTriggered = false;

    public ArchetypeAct3DivinitySimple() {
        super("WA_ARCHETYPE_DIVINITY", "Divinity");

        maxHPModifier += 348;
        maxHPModifierAsc = 30;
        actNum = 3;

    }



    @Override
    public void addedPreBattle() {

        AbstractCreature m = AbstractCharBoss.boss;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new WatcherDivinityPower(m)));

    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;
        if (halfHealthTrigger && AbstractCharBoss.boss.stance.ID != EnDivinityStance.STANCE_ID){
            //Lose all Mantra. Restore 10 HP and gain 1 Strength per Mantra lost.
            addToList(cardsList, new EnCrusade(), extraUpgrades);
            addToList(cardsList, new EnApparition(), false);
            ((CharBossWatcher)AbstractCharBoss.boss).playCrusaderSpeech();
            turn++;
            if (turn >= 4){
                turn = 0;
                looped = true;
            }
        } else {

            switch (turn) {
                case 0:
                    //turn 1
                    addToList(cardsList, new EnWish(), extraUpgrades);
                    addToList(cardsList, new EnWallop(), true);
                    turn++;
                    break;
                case 1:
                    //turn 2
                    addToList(cardsList, new EnBrilliance(), true);
                    addToList(cardsList, new EnFleetingFaith(), extraUpgrades);
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new EnProtect(), true);
                    addToList(cardsList, new EnBlind());
                    turn++;
                    break;
                case 3:
                    //turn 4
                    addToList(cardsList, new EnProstrate());
                    AbstractBossCard c = new EnBrilliance();
                    //c.manualCustomDamageModifier = 2;
                    addToList(cardsList, c, true);
                    turn = 0;
                    looped = true;
                    break;


            }
        }

        return cardsList;
    }


}