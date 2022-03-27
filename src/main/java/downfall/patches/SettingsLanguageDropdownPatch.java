package downfall.patches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.screens.options.DropdownMenu;
import com.megacrit.cardcrawl.screens.options.OptionsPanel;
import downfall.downfallMod;

import java.util.ArrayList;
import java.util.Arrays;

@SpirePatch(clz = OptionsPanel.class,
        method = SpirePatch.CONSTRUCTOR)
public class SettingsLanguageDropdownPatch {
    public static void Postfix(OptionsPanel __instance) {
        DropdownMenu ourMenu = new DropdownMenu(__instance, validLangs(__instance), FontHelper.tipBodyFont, Settings.CREAM_COLOR, 9);
        ReflectionHacks.setPrivate(__instance, OptionsPanel.class, "languageDropdown", ourMenu);
    }

    private static String[] validLangs(OptionsPanel panel) {
        System.out.println("VEX LOOK HERE! -> -> ->");
        String[] orig = panel.languageLabels();
        ArrayList<String> ours = new ArrayList<>();
        System.out.println(downfallMod.SupportedLanguagesStrings);
        for (String s : orig) {
            System.out.println(s);
            if (Arrays.stream(downfallMod.SupportedLanguagesStrings).anyMatch(q -> q.equals(s))) {
                ours.add(s);
            }
        }
        return ours.toArray(new String[ours.size()]);
    }
}
