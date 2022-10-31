package sneckomod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.random.Random;
import sneckomod.SneckoMod;

@SpirePatch(
        clz = AbstractDungeon.class,
        method = "generateSeeds"
)
public class GenerateSeedsPatch {
    @SpirePostfixPatch
    public static void Postfix() {
        SneckoMod.identifyRng = new Random(Settings.seed);
    }
}
