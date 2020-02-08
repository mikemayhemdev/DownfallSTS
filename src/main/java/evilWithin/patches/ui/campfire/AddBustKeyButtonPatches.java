package evilWithin.patches.ui.campfire;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import evilWithin.patches.EvilModeCharacterSelect;
import evilWithin.ui.campfire.BustKeyOption;
import javassist.CtBehavior;

import java.util.ArrayList;

public class AddBustKeyButtonPatches {
    @SpirePatch(clz = AbstractPlayer.class, method = SpirePatch.CLASS)
    public static class KeyFields {
        public static SpireField<Boolean> bustedRuby = new SpireField<>(() -> false);
        public static SpireField<Boolean> bustedEmerald = new SpireField<>(() -> false);
        public static SpireField<Boolean> bustedSapphire = new SpireField<>(() -> false);
    }

    @SpirePatch(clz = CampfireUI.class, method = "initializeButtons")
    public static class AddKeys {
        @SpireInsertPatch(locator = Locator.class)
        public static void patch(CampfireUI __instance, ArrayList<AbstractCampfireOption> ___buttons) {
            if(EvilModeCharacterSelect.evilMode) {
                if (Settings.hasRubyKey && !KeyFields.bustedRuby.get(AbstractDungeon.player)) {
                    ___buttons.add(new BustKeyOption(BustKeyOption.Keys.RUBY));
                }
                if (Settings.hasEmeraldKey && !KeyFields.bustedEmerald.get(AbstractDungeon.player)) {
                    ___buttons.add(new BustKeyOption(BustKeyOption.Keys.EMERALD));
                }
                if (Settings.hasSapphireKey && !KeyFields.bustedSapphire.get(AbstractDungeon.player)) {
                    ___buttons.add(new BustKeyOption(BustKeyOption.Keys.SAPPHIRE));
                }
            }
        }

        public static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "relics");
                return LineFinder.findInOrder(ctBehavior, finalMatcher);
            }
        }
    }

    @SpirePatch(clz = AbstractPlayer.class, method = "applyStartOfCombatLogic")
    public static class KeyBustBuffs {
        private static final int STACK = 1;
        @SpirePostfixPatch
        public static void patch(AbstractPlayer __instance) {
            boolean allBusts = KeyFields.bustedRuby.get(__instance) && KeyFields.bustedEmerald.get(__instance) && KeyFields.bustedSapphire.get(__instance);
            int stacks = STACK * (allBusts?2:1);
            if(KeyFields.bustedRuby.get(__instance)) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(__instance, __instance, new StrengthPower(__instance, stacks), stacks));
            }
            if(KeyFields.bustedEmerald.get(__instance)) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(__instance, __instance, new DexterityPower(__instance, stacks), stacks));
            }
        }
    }
}
