package sneckomod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.random.Random;
import downfall.downfallMod;

@SpirePatch(
        clz = AbstractDungeon.class,
        method = "generateSeeds"
)
public class GenerateSeedsPatch {
    @SpirePostfixPatch
    public static void Postfix() {
        downfallMod.identifyRng = new Random(Settings.seed);
    }
}
