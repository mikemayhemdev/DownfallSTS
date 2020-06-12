package downfall.patches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import com.megacrit.cardcrawl.ui.campfire.RecallOption;
import downfall.downfallMod;

@SpirePatch(
        clz = RecallOption.class,
        method = SpirePatch.CONSTRUCTOR
)
public class CampfireOptionTextPatch {

    private static UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(downfallMod.makeID("AscensionText"));

    public static void Postfix(RecallOption __instance) {
        if (EvilModeCharacterSelect.evilMode) {
            ReflectionHacks.setPrivate(__instance, AbstractCampfireOption.class, "description", uiStrings.TEXT[2]);
        }
    }
}
