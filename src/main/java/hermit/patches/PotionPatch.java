package hermit.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import hermit.relics.BartenderGlass;


public class PotionPatch {
    @SpirePatch(clz = AbstractPotion.class, method = "canUse")
    public static class MakeActuallyUsable
    {
        public static boolean Postfix(boolean __result, AbstractPotion __instance)
        {
            if (AbstractDungeon.player.hasRelic(BartenderGlass.ID))
            {
                if (AbstractDungeon.player.getRelic(BartenderGlass.ID).grayscale)
                    return false;
            }
            return (__result);
        }
    }

}
