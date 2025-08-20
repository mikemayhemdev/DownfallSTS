package charbosses.bosses.Silent.Simpler;

import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Silent.ArchetypeBaseSilent;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.green.*;
import charbosses.cards.other.Antidote;
import charbosses.cards.other.AntidoteAsPower;
import charbosses.cards.other.AntidoteAsPowerExpensive;
import charbosses.powers.bossmechanicpowers.SilentPoisonPower;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class ArchetypeAct1PoisonSimple extends ArchetypeBaseSilent {

    private int glassKnives;
    public ArchetypeAct1PoisonSimple() {
        super("SI_POISON_ARCHETYPE", "Poison");


        maxHPModifier += 100;
        maxHPModifierAsc = 10;
        actNum = 1;
    }



    @Override
    public void addedPreBattle() {
        super.addedPreBattle();

        boolean hardmode = AbstractDungeon.ascensionLevel >= 19;
        if (hardmode){
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new AntidoteAsPowerExpensive(), 1));
        } else {
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new AntidoteAsPower(), 1));
        }
    }

    public void initialize() {


        // animation
        try {
            Method loadAnimationMethod = AbstractCreature.class.getDeclaredMethod("loadAnimation", String.class, String.class, float.class);
            loadAnimationMethod.setAccessible(true);
            loadAnimationMethod.invoke(AbstractCharBoss.boss, "expansioncontentResources/images/bosses/silent/1/Poison_Silent.atlas", "expansioncontentResources/images/bosses/silent/1/Poison_Silent.json", 1.0f);
            AnimationState.TrackEntry e = AbstractCharBoss.boss.state.setAnimation(0, "Idle", true);
            e.setTimeScale(0.9f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;
        //boolean WhetstoneUpgrades = AbstractDungeon.ascensionLevel >= 19;
            switch (turn) {
                case 0: //Turn 1
                    if (looped){
                        addToList(cardsList, new EnBouncingFlask(), extraUpgrades);
                        addToList(cardsList, new EnNoxiousFumes(), extraUpgrades);
                    } else {
                        addToList(cardsList, new EnNewToxins());
                        addToList(cardsList, new EnBouncingFlask(), extraUpgrades);
                    }

                    turn++;
                    break;

                case 1: //Turn 2

                    addToList(cardsList, new EnDodgeAndRoll());
                    addToList(cardsList, new EnDodgeAndRoll());
                    turn++;
                    break;

                case 2: //Turn 3

                    addToList(cardsList, new EnCripplingCloud(),false);
                    addToList(cardsList, new EnGlassKnife(glassKnives));
                    glassKnives++;
                    turn++;
                    break;

                case 3: //Turn 4

                    addToList(cardsList, new EnBane(), false);
                    addToList(cardsList, new EnBane(), extraUpgrades);

                    turn++;
                    break;

                case 4: //Turn 5
                    addToList(cardsList, new EnDeadlyPoison(), extraUpgrades);
                    addToList(cardsList, new EnCatalyst(), true);
                    turn++;

                    turn = 3;
                    break;


        }
        return cardsList;
    }

}