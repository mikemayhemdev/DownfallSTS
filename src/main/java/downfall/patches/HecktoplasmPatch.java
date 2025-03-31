package downfall.patches;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import downfall.relics.Hecktoplasm;

//thorton code
@SpirePatch(clz = AbstractPlayer.class, method = "gainGold")
public class HecktoplasmPatch {
    public static void Prefix(AbstractPlayer __instance, @ByRef int[] amount) {
        if (AbstractDungeon.player.hasRelic(Hecktoplasm.ID)) {
            AbstractDungeon.player.getRelic(Hecktoplasm.ID).flash();
            amount[0] = (int)(amount[0] * 0);
        }
    }
}
