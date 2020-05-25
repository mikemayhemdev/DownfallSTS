package downfall.patches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.shop.ShopScreen;
import downfall.downfallMod;

import java.util.ArrayList;

public class HeartShopUIPatches {

    @SpirePatch(clz = ShopScreen.class, method = "init", paramtypez = {ArrayList.class, ArrayList.class})
    public static class HeartShopUIPatch {
        @SpirePostfixPatch
        public static void Postfix(ShopScreen __result, ArrayList<AbstractCard> coloredCards, ArrayList<AbstractCard> colorlessCards) {

            if (EvilModeCharacterSelect.evilMode) {
                ReflectionHacks.setPrivate(__result, ShopScreen.class, "rugImg", ImageMaster.loadImage(downfallMod.assetPath("images/ui/heartRug.png")));
                ReflectionHacks.setPrivate(__result, ShopScreen.class, "handImg", ImageMaster.loadImage(downfallMod.assetPath("images/ui/heartHand.png")));


                ReflectionHacks.setPrivateStaticFinal(ShopScreen.class, "characterStrings", CardCrawlGame.languagePack.getCharacterString( downfallMod.makeID("HeartMerchantScreen")));
                ReflectionHacks.setPrivateStaticFinal(ShopScreen.class, "WELCOME_MSG", CardCrawlGame.languagePack.getCharacterString( downfallMod.makeID("HeartMerchantScreen")).NAMES[0]);
                ReflectionHacks.setPrivateStaticFinal(ShopScreen.class, "NAMES", CardCrawlGame.languagePack.getCharacterString( downfallMod.makeID("HeartMerchantScreen")).NAMES);
                ReflectionHacks.setPrivateStaticFinal(ShopScreen.class, "TEXT", CardCrawlGame.languagePack.getCharacterString( downfallMod.makeID("HeartMerchantScreen")).TEXT);


            } else {
                    ReflectionHacks.setPrivateStaticFinal(ShopScreen.class, "characterStrings", CardCrawlGame.languagePack.getCharacterString("Shop Screen"));
                    ReflectionHacks.setPrivateStaticFinal(ShopScreen.class, "WELCOME_MSG", CardCrawlGame.languagePack.getCharacterString("Shop Screen").NAMES[0]);
                    ReflectionHacks.setPrivateStaticFinal(ShopScreen.class, "NAMES", CardCrawlGame.languagePack.getCharacterString("Shop Screen").NAMES);
                    ReflectionHacks.setPrivateStaticFinal(ShopScreen.class, "TEXT", CardCrawlGame.languagePack.getCharacterString("Shop Screen").TEXT);


                }
            }
        }


    @SpirePatch(clz = ShopScreen.class, method = "playCantBuySfx")
    public static class DisableAudio1 {
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix() {

            if (EvilModeCharacterSelect.evilMode) {
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = ShopScreen.class, method = "playBuySfx")
    public static class DisableAudio2 {
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix() {

            if (EvilModeCharacterSelect.evilMode) {
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = ShopScreen.class, method = "playMiscSfx")
    public static class DisableAudio3 {
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix() {

            if (EvilModeCharacterSelect.evilMode) {
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = ShopScreen.class, method = "welcomeSfx")
    public static class DisableAudio4 {
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix() {

            if (EvilModeCharacterSelect.evilMode) {
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }
}