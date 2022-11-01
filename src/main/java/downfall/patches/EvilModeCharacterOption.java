package downfall.patches;

import automaton.AutomatonChar;
import champ.ChampChar;
import collector.CollectorChar;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import downfall.downfallMod;
import gremlin.patches.GremlinEnum;
import guardian.patches.GuardianEnum;
import sneckomod.TheSnecko;
import theHexaghost.TheHexaghost;

@SpirePatch(
        clz = CharacterOption.class,
        method = "updateHitbox"
)
public class EvilModeCharacterOption {
    public static final String[] TEXT = CardCrawlGame.languagePack.getCharacterString(downfallMod.makeID("Unlock")).TEXT;

    @SpirePostfixPatch
    public static void Prefix(CharacterOption __instance) {

        __instance.hb.update();

        if ((__instance.hb.hovered) && (__instance.locked)) {
            if (__instance.c.chosenClass == downfallMod.Enums.GUARDIAN) {
                TipHelper.renderGenericTip(InputHelper.mX + 70.0F * Settings.scale, InputHelper.mY - 10.0F * Settings.scale, __instance.TEXT[0], TEXT[1] + TEXT[0]);

            } else if (__instance.c.chosenClass == downfallMod.Enums.THE_SPIRIT) {

                if (UnlockTracker.isCharacterLocked("Guardian")) {
                    TipHelper.renderGenericTip(InputHelper.mX + 70.0F * Settings.scale, InputHelper.mY - 10.0F * Settings.scale, __instance.TEXT[0], TEXT[2] + TEXT[0]);
                } else {
                    TipHelper.renderGenericTip(InputHelper.mX + 70.0F * Settings.scale, InputHelper.mY - 10.0F * Settings.scale, __instance.TEXT[0], TEXT[3] + TEXT[0]);
                }
                //TODO - This should be fine if we move the Enums to DownfallMod.
            } else if (__instance.c.chosenClass == downfallMod.Enums.THE_CHAMP) {
                if (UnlockTracker.isCharacterLocked("Hexaghost")) {
                    TipHelper.renderGenericTip(InputHelper.mX + 70.0F * Settings.scale, InputHelper.mY - 10.0F * Settings.scale, __instance.TEXT[0], TEXT[4] + TEXT[0]);
                } else {
                    TipHelper.renderGenericTip(InputHelper.mX + 70.0F * Settings.scale, InputHelper.mY - 10.0F * Settings.scale, __instance.TEXT[0], TEXT[5] + TEXT[0]);
                }


            } else if (__instance.c.chosenClass == downfallMod.Enums.THE_AUTOMATON) {
                if (UnlockTracker.isCharacterLocked("Champ")) {
                    TipHelper.renderGenericTip(InputHelper.mX + 70.0F * Settings.scale, InputHelper.mY - 10.0F * Settings.scale, __instance.TEXT[0], TEXT[6] + TEXT[0]);
                } else {
                    TipHelper.renderGenericTip(InputHelper.mX + 70.0F * Settings.scale, InputHelper.mY - 10.0F * Settings.scale, __instance.TEXT[0], TEXT[7] + TEXT[0]);
                }
            } else if (__instance.c.chosenClass == downfallMod.Enums.THE_COLLECTOR) {
                if (UnlockTracker.isCharacterLocked("Automaton")) {
                    TipHelper.renderGenericTip(InputHelper.mX + 70.0F * Settings.scale, InputHelper.mY - 10.0F * Settings.scale, __instance.TEXT[0], TEXT[8] + TEXT[0]);
                } else {
                    TipHelper.renderGenericTip(InputHelper.mX + 70.0F * Settings.scale, InputHelper.mY - 10.0F * Settings.scale, __instance.TEXT[0], TEXT[9] + TEXT[0]);
                }
            } else if (__instance.c.chosenClass == downfallMod.Enums.GREMLIN) {
                if (UnlockTracker.isCharacterLocked("Collector")) {
                    TipHelper.renderGenericTip(InputHelper.mX + 70.0F * Settings.scale, InputHelper.mY - 10.0F * Settings.scale, __instance.TEXT[0], TEXT[10] + TEXT[0]);
                } else {
                    TipHelper.renderGenericTip(InputHelper.mX + 70.0F * Settings.scale, InputHelper.mY - 10.0F * Settings.scale, __instance.TEXT[0], TEXT[11] + TEXT[0]);
                }
            } else if (__instance.c.chosenClass == downfallMod.Enums.THE_SNECKO) {
                if (UnlockTracker.isCharacterLocked("Gremlin")) {
                    TipHelper.renderGenericTip(InputHelper.mX + 70.0F * Settings.scale, InputHelper.mY - 10.0F * Settings.scale, __instance.TEXT[0], TEXT[12] + TEXT[0]);
                } else {
                    TipHelper.renderGenericTip(InputHelper.mX + 70.0F * Settings.scale, InputHelper.mY - 10.0F * Settings.scale, __instance.TEXT[0], TEXT[13] + TEXT[0]);
                }
            }

        }
    }
}

