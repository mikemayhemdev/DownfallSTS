package downfall.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import downfall.events.SensoryStone_Evil;


@SpirePatch(clz = CardRewardScreen.class, method = "acquireCard")

public class SensoryStoneTextPatch {

    @SpirePostfixPatch
    public static void Postfix(CardRewardScreen _instance, AbstractCard c) {
        AbstractEvent e = AbstractDungeon.getCurrRoom().event;
       // //SlimeboundMod.logger.info(AbstractDungeon.getCurrRoom().rewards.size());
        if (e != null) {
         //   //SlimeboundMod.logger.info("e is not null");
            if (e instanceof SensoryStone_Evil) {
               // //SlimeboundMod.logger.info("setting memory card from patch");
                SensoryStone_Evil ee = (SensoryStone_Evil) e;
                ee.setMemoryCard(c);
                ee.getMemoryText();
            }
        }
    }

}

