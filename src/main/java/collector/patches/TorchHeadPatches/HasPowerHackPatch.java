package collector.patches.TorchHeadPatches;

import collector.CollectorChar;
import collector.TorchChar;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.beyond.SpireGrowth;
import com.megacrit.cardcrawl.powers.ConstrictedPower;
import com.megacrit.cardcrawl.powers.EntanglePower;
import com.megacrit.cardcrawl.powers.SurroundedPower;

public class HasPowerHackPatch {
    public static boolean shouldPatchBarricade = false;
    public static boolean shouldPatchEntangled = false;
    public static boolean shouldPatchConstricted = false;

    @SpirePatch(clz = GameActionManager.class, method = "getNextAction")
    public static class BarricadeHackChecker {
        @SpirePrefixPatch
        public static void Prefix(GameActionManager __instance) {
            shouldPatchBarricade = true;
        }

        @SpirePostfixPatch
        public static void Postfix(GameActionManager __instance) {
            shouldPatchBarricade = false;
        }
    }

    @SpirePatch(clz = AbstractCard.class, method = "hasEnoughEnergy")
    public static class EntangledHackChecker {
        @SpirePrefixPatch
        public static void Prefix(AbstractCard __instance) {
            shouldPatchEntangled = true;
        }

        @SpirePostfixPatch
        public static void Postfix(AbstractCard __instance) {
            shouldPatchEntangled = false;
        }
    }

    @SpirePatch(clz = SpireGrowth.class, method = "getMove")
    public static class ConstrictedChecker {
        @SpirePrefixPatch
        public static void Prefix(SpireGrowth __instance) {
            shouldPatchConstricted = true;
        }

        @SpirePostfixPatch
        public static void Postfix(SpireGrowth __instance) {
            shouldPatchConstricted = false;
        }
    }

    @SpirePatch(clz = AbstractCreature.class, method = "hasPower")
    public static class HasPowerPatch {
        @SpirePostfixPatch
        public static boolean Postfix(boolean __result, AbstractCreature __instance, String targetID) {
             if (shouldPatchEntangled && targetID.equals(EntanglePower.POWER_ID)) {
                TorchChar dragon = CollectorChar.getLivingDragon();
                if (dragon != null && __instance != dragon && dragon.hasPower(EntanglePower.POWER_ID)) {
                    return true;
                } else {
                    return __result;
                }
            } else if (shouldPatchConstricted && targetID.equals(ConstrictedPower.POWER_ID)) {
                 TorchChar dragon = CollectorChar.getLivingDragon();
                if (dragon != null && __instance != dragon && dragon.hasPower(ConstrictedPower.POWER_ID)) {
                    return true;
                } else {
                    return __result;
                }
            } else if (targetID.equals(SurroundedPower.POWER_ID)) {
                if (__instance instanceof TorchChar) {
                    return __result || ((TorchChar) __instance).master.hasPower(SurroundedPower.POWER_ID);
                } else {
                    return __result;
                }
            } else {
                return __result;
            }
        }
    }
}
