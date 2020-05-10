package downfall.patches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.shop.ShopScreen;
import downfall.downfallMod;

import java.util.ArrayList;

@SpirePatch(clz = ShopScreen.class, method = "init", paramtypez = {ArrayList.class, ArrayList.class})
public class HeartShopUIPatch {
    @SpirePostfixPatch
    public static void Postfix(ShopScreen __result, ArrayList<AbstractCard> coloredCards, ArrayList<AbstractCard> colorlessCards) {

        ReflectionHacks.setPrivate(__result,ShopScreen.class,"rugImg",ImageMaster.loadImage(downfallMod.assetPath("images/ui/heartRug.png")));
        ReflectionHacks.setPrivate(__result,ShopScreen.class,"handImg",ImageMaster.loadImage(downfallMod.assetPath("images/ui/heartHand.png")));
    }
}