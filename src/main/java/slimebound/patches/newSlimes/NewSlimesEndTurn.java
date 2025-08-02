package slimebound.patches.newSlimes;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import slimebound.patches.SlimeboundEnum;
import slimebound.slimes.SlimeHelper;

@SpirePatch(
        clz = GameActionManager.class,
        method = "callEndOfTurnActions"
)
public class NewSlimesEndTurn {
    public static void Postfix(GameActionManager __instance) {
        if (CardCrawlGame.chosenCharacter == SlimeboundEnum.SLIMEBOUND) {
            //SlimeHelper.AtEndOfTurn();
        }
    }
}