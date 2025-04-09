package sneckomod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
import sneckomod.SneckoMod;

public class StartOverPatch {
    @SpirePatch(
            clz = MainMenuScreen.class,
            method = SpirePatch.CONSTRUCTOR,
            paramtypez = {}
    )
    public static class OnRemoveDefendDoAThing {
        public static void Postfix() {
            SneckoMod.incomingPicks.clear();
        }
    }
}
