package charbosses.bosses.Silent.Simpler;

import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Silent.ArchetypeBaseSilent;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.green.*;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class ArchetypeAct1PoisonSimple extends ArchetypeBaseSilent {

    public ArchetypeAct1PoisonSimple() {
        super("SI_POISON_ARCHETYPE", "Poison");


        maxHPModifier += 100;
        actNum = 1;
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
                        addToList(cardsList, new EnBouncingFlask());
                        addToList(cardsList, new EnCatalyst());
                        addToList(cardsList, new EnGiveAntidote());
                    } else {
                        addToList(cardsList, new EnNewToxins());
                        addToList(cardsList, new EnBouncingFlask());
                        addToList(cardsList, new EnGiveAntidote());
                    }

                    turn++;
                    break;

                case 1: //Turn 2

                    addToList(cardsList, new EnPoisonedStab(), false);
                    addToList(cardsList, new EnPoisonedStab(), false);
                    turn++;
                    break;

                case 2: //Turn 3

                    addToList(cardsList, new EnFootwork(),extraUpgrades);
                    addToList(cardsList, new EnDodgeAndRoll());
                    turn++;
                    break;

                case 3: //Turn 4

                    addToList(cardsList, new EnNoxiousFumes());

                    turn++;
                    break;

                case 4: //Turn 5

                    addToList(cardsList, new EnBane());
                    addToList(cardsList, new EnBane());

                    turn++;

                    break;

                case 5: //Turn 6

                    if (looped){
                        addToList(cardsList, new EnLegSweep());
                    } else {
                        addToList(cardsList, new EnTerror());
                    }
                turn = 0;
                looped = true;

                    break;


        }
        return cardsList;
    }

}