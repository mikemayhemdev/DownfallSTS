package downfall.patches.ui.ending;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.cutscenes.NeowNarrationScreen;
import downfall.downfallMod;
import downfall.patches.EvilModeCharacterSelect;

@SpirePatch(clz = NeowNarrationScreen.class, method = SpirePatch.CONSTRUCTOR)
public class EndingChangePatches {
    @SpirePostfixPatch
    public static void patch(NeowNarrationScreen _instance) {
        if (EvilModeCharacterSelect.evilMode) {
            ReflectionHacks.setPrivateStaticFinal(NeowNarrationScreen.class, "charStrings", CardCrawlGame.languagePack.getCharacterString(downfallMod.makeID("NeowEnding")));
        } else {
            ReflectionHacks.setPrivateStaticFinal(NeowNarrationScreen.class, "charStrings", CardCrawlGame.languagePack.getCharacterString("PostCreditsNeow"));
        }
    }
}
