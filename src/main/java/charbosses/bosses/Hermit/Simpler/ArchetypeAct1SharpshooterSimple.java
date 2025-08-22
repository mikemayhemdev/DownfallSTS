package charbosses.bosses.Hermit.Simpler;

import basemod.ReflectionHacks;
import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Hermit.CharBossHermit;
import charbosses.bosses.Ironclad.ArchetypeBaseIronclad;
import charbosses.cards.hermit.*;
import charbosses.cards.purple.EnDefendPurple;
import charbosses.monsters.LouseTangerine;
import charbosses.powers.bossmechanicpowers.HermitConcentrationPower;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.Skeleton;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class ArchetypeAct1SharpshooterSimple extends ArchetypeBaseIronclad {
    public static final int damageThreshold = 10;

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("hermit:SpecialFriend");

    public ArchetypeAct1SharpshooterSimple() {
        super("HERMIT_SHARPSHOOTER_ARCHETYPE", "Dead On");

        maxHPModifier += 88;
        maxHPModifierAsc = 10;
        actNum = 1;
    }

    @Override
    public void addedPreBattle() {
        super.addedPreBattle();

        AbstractCreature p = AbstractCharBoss.boss;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new HermitConcentrationPower(p), damageThreshold));

        AbstractMonster tangerine = new LouseTangerine(-400F, 0);
        AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(tangerine,true));
        tangerine.usePreBattleAction();

        try {
            Method loadAnimationMethod = AbstractCreature.class.getDeclaredMethod("loadAnimation", new Class[] { String.class, String.class, float.class });
            loadAnimationMethod.setAccessible(true);
            loadAnimationMethod.invoke(AbstractCharBoss.boss, new Object[] { "expansioncontentResources/images/bosses/hermit/1/Hermit_Sharp.atlas", "expansioncontentResources/images/bosses/hermit/1/Hermit_Sharp.json", 1.0f });

            AnimationState.TrackEntry e = AbstractCharBoss.boss.state.setAnimation(0, "Idle", true);
            ((AnimationStateData)ReflectionHacks.getPrivate(AbstractCharBoss.boss, AbstractCreature.class, "stateData")).setMix("Hit", "Idle", 0.1f);
            e.setTimeScale(0.9f);
            ((CharBossHermit) AbstractCharBoss.boss).eye = ((Skeleton)ReflectionHacks.getPrivate(AbstractCharBoss.boss, AbstractCreature.class, "skeleton")).findSlot("Eye1");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void initialize() {

    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;

            switch (turn) {
                case 0:
                    addToList(cardsList, new EnDeadeye(), extraUpgrades);
                    addToList(cardsList, new EnDefendHermit());
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new EnMemento());
                    addToList(cardsList, new EnSnapshot());
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new EnDive(), extraUpgrades);
                    addToList(cardsList, new EnDefendHermit());
                    turn++;
                    break;
                case 3:
                    addToList(cardsList, new EnHeadshot(), extraUpgrades);
                    addToList(cardsList, new EnRicochet());
                    turn++;
                    break;
                case 4:
                    addToList(cardsList, new EnSmokingBarrel());
                    addToList(cardsList, new EnGhostlyPresence(), extraUpgrades);
                    turn = 1;
                    looped = true;
                    break;
            }


        return cardsList;
    }

}
