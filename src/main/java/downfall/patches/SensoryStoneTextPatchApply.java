/*

package downfall.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import downfall.events.SensoryStone_Evil;
import slimebound.SlimeboundMod;
import slimebound.characters.SlimeboundCharacter;


@SpirePatch(clz = CardRewardScreen.class, method = "takeReward")

public class SensoryStoneTextPatchApply {

    @SpirePostfixPatch
    public static void Postfix(CardRewardScreen _instance) {
        if (_instance.rItem.cards.size() > 0){
            AbstractEvent e = AbstractDungeon.getCurrRoom().event;
            if (e != null) {
                if (AbstractDungeon.combatRewardScreen.hasTakenAll = true){
                    //SlimeboundMod.logger.info("has taken all is true");
                    if (e instanceof SensoryStone_Evil) {
                        SensoryStone_Evil ee = (SensoryStone_Evil) e;
                        ee.getMemoryText();
                    }
                }

            }
        }

    }

}

*/