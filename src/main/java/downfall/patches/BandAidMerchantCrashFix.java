package downfall.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BandAidMerchantCrashFix {

    @SpirePatch(
            clz = AbstractCard.class,
            method = "cardPlayable"
    )
    public static class RelicPatch {

        public static SpireReturn Prefix(AbstractCard __instance) {
            if (AbstractDungeon.getMonsters() == null) {
               return SpireReturn.Return(false);
            } else {
               return SpireReturn.Continue();
            }
        }
    }


}
