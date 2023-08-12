package collector.patches.ExtraDeckButtonPatches;

import basemod.ReflectionHacks;
import collector.CollectorCollection;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.ExhaustPileViewScreen;
import javassist.CtBehavior;

import static collector.CollectorMod.makeID;

public class ExhaustPileViewScreenPatches {

    public static boolean showCollection = false;
    private static boolean showingCollection = false;
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(makeID("CombatCollectionViewScreen"));

    @SpirePatch(
            clz = ExhaustPileViewScreen.class,
            method = "open"
    )
    public static class OpenExhaustPileViewScreenPatch {
        @SpireInsertPatch(locator = OpenExhaustPileViewScreenPatchLocator.class)
        public static void Insert(ExhaustPileViewScreen _instance) {
            if (showCollection) {
                CardGroup group = ReflectionHacks.getPrivate(_instance, ExhaustPileViewScreen.class, "exhaustPileCopy");
                group.clear();
                group.group.addAll(CollectorCollection.combatCollection.group);
                showCollection = false;
                showingCollection = true;
            } else {
                showingCollection = false;
            }
        }
    }

    private static class OpenExhaustPileViewScreenPatchLocator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher matcher = new Matcher.MethodCallMatcher(ExhaustPileViewScreen.class, "hideCards");
            return LineFinder.findInOrder(ctBehavior, matcher);
        }
    }

    @SpirePatch(
            clz = ExhaustPileViewScreen.class,
            method = "render"
    )
    public static class RenderExhaustPileViewScreenPatch {
        @SpireInsertPatch(locator = RenderExhaustPileViewScreenPatchLocator.class)
        public static SpireReturn<Void> Insert(ExhaustPileViewScreen _instance, SpriteBatch sb) {
            if (showingCollection) {
                FontHelper.renderDeckViewTip(sb, uiStrings.TEXT[0], 96.0F * Settings.scale, Settings.CREAM_COLOR);// 311
                return SpireReturn.Return(null);
            } else {
                return SpireReturn.Continue();
            }
        }
    }

    private static class RenderExhaustPileViewScreenPatchLocator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher matcher = new Matcher.MethodCallMatcher(FontHelper.class, "renderDeckViewTip");
            return LineFinder.findInOrder(ctBehavior, matcher);
        }
    }
}
