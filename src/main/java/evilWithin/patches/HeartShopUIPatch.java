package evilWithin.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.audio.Music;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.audio.MainMusic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.shop.ShopScreen;
import evilWithin.EvilWithinMod;
import sun.reflect.Reflection;

import java.util.ArrayList;

@SpirePatch(clz = ShopScreen.class, method = "init", paramtypez = {ArrayList.class, ArrayList.class})
public class HeartShopUIPatch {
    @SpirePostfixPatch
    public static void Postfix(ShopScreen __result, ArrayList<AbstractCard> coloredCards, ArrayList<AbstractCard> colorlessCards) {

        ReflectionHacks.setPrivate(__result,ShopScreen.class,"rugImg",ImageMaster.loadImage(EvilWithinMod.assetPath("images/ui/heartRug.png")));
        ReflectionHacks.setPrivate(__result,ShopScreen.class,"handImg",ImageMaster.loadImage(EvilWithinMod.assetPath("images/ui/heartHand.png")));
    }
}