package downfall.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import downfall.relics.Hecktoplasm;

@SpirePatch(
        clz = AbstractPlayer.class,
        method = "gainGold"
)
public class NoGoldWithHectoPatch {
    public static SpireReturn Prefix(AbstractPlayer __instance, int amount) {
        if (AbstractDungeon.player.hasRelic(Hecktoplasm.ID)) {
            AbstractDungeon.player.getRelic(Hecktoplasm.ID).flash();
            return SpireReturn.Return(null);
        }
        return SpireReturn.Continue();
    }
}