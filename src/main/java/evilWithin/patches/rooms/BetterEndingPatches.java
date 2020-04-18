package evilWithin.patches.rooms;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AnimatedNpc;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.RoomEventDialog;
import com.megacrit.cardcrawl.events.beyond.SpireHeart;
import com.megacrit.cardcrawl.localization.EventStrings;
import evilWithin.patches.EvilModeCharacterSelect;
import evilWithin.vfx.SoulStealEffect;
import javassist.CtBehavior;

public class BetterEndingPatches {
    @SpirePatch(clz = SpireHeart.class, method = SpirePatch.CONSTRUCTOR)
    public static class StringChanges {
        @SpirePrefixPatch
        public static void patch(SpireHeart __instance) {
            if(EvilModeCharacterSelect.evilMode) {
                ReflectionHacks.setPrivateStaticFinal(SpireHeart.class, "eventStrings", CardCrawlGame.languagePack.getEventString("evilWithin:BetterEnding"));
            } else {
                ReflectionHacks.setPrivateStaticFinal(SpireHeart.class, "eventStrings", CardCrawlGame.languagePack.getEventString(SpireHeart.ID));
            }

            EventStrings tmp = (EventStrings) ReflectionHacks.getPrivateStatic(SpireHeart.class, "eventStrings");

            for (int i = 0; i < SpireHeart.DESCRIPTIONS.length; i++) {
                SpireHeart.DESCRIPTIONS[i] = tmp.DESCRIPTIONS[i];
            }

            for (int i = 0; i < SpireHeart.OPTIONS.length; i++) {
                SpireHeart.OPTIONS[i] = tmp.OPTIONS[i];
            }
        }
    }

    @SpirePatch(clz = SpireHeart.class, method = "buttonEffect")
    public static class BetterEffect {
        @SpireInsertPatch(locator = Locator.class)
        public static SpireReturn<Void> Insert(SpireHeart __instance, int buttonPressed) {
            if(EvilModeCharacterSelect.evilMode) {
                AnimatedNpc heart = (AnimatedNpc) ReflectionHacks.getPrivate(__instance, SpireHeart.class, "npc");
                AbstractDungeon.effectsQueue.add(new SoulStealEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, heart.skeleton.getX(), heart.skeleton.getY()));

                return SpireReturn.Return(null);
            }

            return SpireReturn.Continue();
        }

        @SpireInsertPatch(locator =  Locator2.class)
        public static void patch(SpireHeart __instance, int buttonPressed) {
            __instance.roomEventText.updateBodyText("@.@ @.@ @.@");
        }

        public static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.FieldAccessMatcher(AbstractDungeon.class, "effectList");
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }

        public static class Locator2 extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.MethodCallMatcher(RoomEventDialog.class, "updateDialogOption");
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }
}
