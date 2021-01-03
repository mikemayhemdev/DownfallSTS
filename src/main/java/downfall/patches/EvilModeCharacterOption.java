package downfall.patches;

import automaton.AutomatonChar;
import champ.ChampChar;
import champ.ChampMod;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import downfall.downfallMod;
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
            if (__instance.c.chosenClass == GuardianEnum.GUARDIAN) {
                TipHelper.renderGenericTip(InputHelper.mX + 70.0F * Settings.scale, InputHelper.mY - 10.0F * Settings.scale, __instance.TEXT[0], TEXT[0] + TEXT[9]);

            } else if (__instance.c.chosenClass == TheHexaghost.Enums.THE_SPIRIT) {

                if (UnlockTracker.isCharacterLocked("Guardian")) {
                    TipHelper.renderGenericTip(InputHelper.mX + 70.0F * Settings.scale, InputHelper.mY - 10.0F * Settings.scale, __instance.TEXT[0], TEXT[1] + TEXT[9]);
                } else {
                    TipHelper.renderGenericTip(InputHelper.mX + 70.0F * Settings.scale, InputHelper.mY - 10.0F * Settings.scale, __instance.TEXT[0], TEXT[2] + TEXT[9]);
                }
            } else if (__instance.c.chosenClass == ChampChar.Enums.THE_CHAMP) {
                if (UnlockTracker.isCharacterLocked("Hexaghost")) {
                    TipHelper.renderGenericTip(InputHelper.mX + 70.0F * Settings.scale, InputHelper.mY - 10.0F * Settings.scale, __instance.TEXT[0], TEXT[3] + TEXT[9]);
                } else {
                    TipHelper.renderGenericTip(InputHelper.mX + 70.0F * Settings.scale, InputHelper.mY - 10.0F * Settings.scale, __instance.TEXT[0], TEXT[4] + TEXT[9]);
                }
            } else if (__instance.c.chosenClass == AutomatonChar.Enums.THE_AUTOMATON) {
                if (UnlockTracker.isCharacterLocked("Champ")) {
                    TipHelper.renderGenericTip(InputHelper.mX + 70.0F * Settings.scale, InputHelper.mY - 10.0F * Settings.scale, __instance.TEXT[0], TEXT[5] + TEXT[9]);
                } else {
                    TipHelper.renderGenericTip(InputHelper.mX + 70.0F * Settings.scale, InputHelper.mY - 10.0F * Settings.scale, __instance.TEXT[0], TEXT[6] + TEXT[9]);
                }
            } else if (__instance.c.chosenClass == TheSnecko.Enums.THE_SNECKO) {
                if (UnlockTracker.isCharacterLocked("Automaton")) {
                    TipHelper.renderGenericTip(InputHelper.mX + 70.0F * Settings.scale, InputHelper.mY - 10.0F * Settings.scale, __instance.TEXT[0], TEXT[7] + TEXT[9]);
                } else {
                    TipHelper.renderGenericTip(InputHelper.mX + 70.0F * Settings.scale, InputHelper.mY - 10.0F * Settings.scale, __instance.TEXT[0], TEXT[8] + TEXT[9]);
                }
            }
        }
    }
}