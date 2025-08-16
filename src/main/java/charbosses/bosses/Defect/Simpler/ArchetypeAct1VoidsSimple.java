package charbosses.bosses.Defect.Simpler;

import basemod.ReflectionHacks;
import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Defect.ArchetypeBaseDefect;
import charbosses.cards.blue.*;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class ArchetypeAct1VoidsSimple extends ArchetypeBaseDefect {

    int darkOrbsChanneled = 0;

    public ArchetypeAct1VoidsSimple() {
        super("DF_ARCHETYPE_STREAMLINE", "Streamline");
        maxHPModifier += 102;
        actNum = 1;
    }

    @Override
    public void addedPreBattle() {

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
            switch (turn) {
                case 0:
                    addToList(cardsList, new EnMurderbot(), false);
                    turn++;
                    break;
                case 1:
                    //Turn 2
                    addToList(cardsList, new EnDoomAndGloom(), extraUpgrades);
                    turn++;
                    break;
                case 2:
                    //Turn 3
                    addToList(cardsList, new EnLeap());
                    addToList(cardsList, new EnLeap());
                    turn++;
                    break;
                case 3:
                    //Turn 4
                    addToList(cardsList, new EnRipAndTear(), false);

                    turn++;
                    break;
                case 4:
                    //Turn 4
                    addToList(cardsList, new EnBuffer(), false);
                    addToList(cardsList, new EnLoop(), false);

                    turn++;
                    break;
                case 5:
                    //Turn 4
                    addToList(cardsList, new EnSunder(), false);
                    turn = 1;
                    looped = true;
                    break;
            }



        return cardsList;
    }


}