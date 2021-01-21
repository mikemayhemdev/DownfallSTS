package charbosses.patches;

import charbosses.bosses.Crowbot.CharBossCrowbot;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;

import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import downfall.downfallMod;
import javassist.CtBehavior;

public class AmmoTypeTextPatch {
    private static UIStrings uiStrings = null;


    private static void prepStrings() {
        if (uiStrings == null) {
            uiStrings = CardCrawlGame.languagePack.getUIString(downfallMod.makeID("SingleCardViewPopup"));
        }
    }


    @SpirePatch(clz = AbstractCard.class, method = "renderType")
    public static class Card {
        @SpireInsertPatch(locator = Locator.class, localvars = {"text"})
        public static void Insert(AbstractCard __instance, SpriteBatch sb, @ByRef String[] text) {
            if (__instance.hasTag(CharBossCrowbot.Enums.AMMO)) {
                AmmoTypeTextPatch.prepStrings();
                text[0] = uiStrings.TEXT[0];
            }
        }


        private static class Locator
                extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(com.megacrit.cardcrawl.helpers.FontHelper.class, "renderRotatedText");
                return LineFinder.findInOrder(ctMethodToPatch, methodCallMatcher);
            }
        }
    }


    @SpirePatch(clz = SingleCardViewPopup.class, method = "renderCardTypeText")
    public static class SCV {
        @SpireInsertPatch(locator = Locator.class, localvars = {"card", "label"})
        public static void Insert(SingleCardViewPopup __instance, SpriteBatch sb, AbstractCard card, @ByRef String[] label) {
            if (card.hasTag(CharBossCrowbot.Enums.AMMO)) {
                AmmoTypeTextPatch.prepStrings();
                label[0] = uiStrings.TEXT[0];
            }
        }


        private static class Locator
                extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(com.megacrit.cardcrawl.helpers.FontHelper.class, "renderFontCentered");
                return LineFinder.findInOrder(ctMethodToPatch, methodCallMatcher);
            }
        }
    }
}


