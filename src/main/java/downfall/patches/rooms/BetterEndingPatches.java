package downfall.patches.rooms;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AnimatedNpc;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.RoomEventDialog;
import com.megacrit.cardcrawl.events.beyond.SpireHeart;
import com.megacrit.cardcrawl.localization.EventStrings;
import downfall.patches.EvilModeCharacterSelect;
import downfall.patches.ui.campfire.AddBustKeyButtonPatches;
import downfall.vfx.SoulStealEffect;
import javassist.CtBehavior;

public class BetterEndingPatches {
    //Determine and overwrite strings on room creation
    @SpirePatch(clz = SpireHeart.class, method = SpirePatch.CONSTRUCTOR)
    public static class StringChanges {
        @SpirePrefixPatch
        public static void patch(SpireHeart __instance) {
            if (EvilModeCharacterSelect.evilMode) {
                ReflectionHacks.setPrivateStaticFinal(SpireHeart.class, "eventStrings", CardCrawlGame.languagePack.getEventString("downfall:BetterEnding"));
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
    public static class MoveToAct4 {
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(SpireHeart __instance) {

            if (EvilModeCharacterSelect.evilMode) {
                if (AbstractDungeon.actNum == 3 &&
                        AddBustKeyButtonPatches.KeyFields.bustedRuby.get(AbstractDungeon.player) &&
                        AddBustKeyButtonPatches.KeyFields.bustedSapphire.get(AbstractDungeon.player) &&
                        AddBustKeyButtonPatches.KeyFields.bustedEmerald.get(AbstractDungeon.player)
                ) {
                /*
                __instance.roomEventText.clear();
                __instance.hasFocus = false;
                __instance.roomEventText.hide();
                Color color = (Color)ReflectionHacks.getPrivate(__instance, SpireHeart.class, "fadeColor");
                ReflectionHacks.setPrivate(__instance, SpireHeart.class, "fadeColor", new Color(color.r, color.g, color.b, 0F));

                CardCrawlGame.mode = CardCrawlGame.GameMode.GAMEPLAY;
                CardCrawlGame.nextDungeon = "TheEnding";
                CardCrawlGame.music.fadeOutBGM();
                CardCrawlGame.music.fadeOutTempBGM();
                AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
                AbstractDungeon.fadeOut();
                AbstractDungeon.isDungeonBeaten = true;
                */
                    return SpireReturn.Continue();
                } else {
                    Settings.hasRubyKey = false;
                    Settings.hasEmeraldKey = false;
                    Settings.hasSapphireKey = false;
                }
                return SpireReturn.Continue();
            } else {
                return SpireReturn.Continue();
            }
        }

        //Change effect when showing the player the score
        @SpirePatch(clz = SpireHeart.class, method = "buttonEffect")
        public static class BetterEffect {
            @SpireInsertPatch(locator = Locator.class)
            public static SpireReturn<Void> Insert(SpireHeart __instance, int buttonPressed) {
                if (EvilModeCharacterSelect.evilMode) {
                    AnimatedNpc heart = (AnimatedNpc) ReflectionHacks.getPrivate(__instance, SpireHeart.class, "npc");
                    AbstractDungeon.effectsQueue.add(new SoulStealEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, heart.skeleton.getX(), heart.skeleton.getY() + 300F * Settings.scale));

                    return SpireReturn.Return(null);
                }

                return SpireReturn.Continue();
            }

            //Overwrite character specific dialogue vs the heart because it doesn't fit.
            @SpireInsertPatch(locator = Locator2.class)
            public static void patch(SpireHeart __instance, int buttonPressed) {
                if (EvilModeCharacterSelect.evilMode) {
                    __instance.roomEventText.updateBodyText(CardCrawlGame.languagePack.getEventString("downfall:BetterEnding").DESCRIPTIONS[16]);
                }
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
}
