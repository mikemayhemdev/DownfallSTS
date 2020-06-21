package theHexaghost.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theHexaghost.HexaMod;

@SpirePatch(
        clz = AbstractDungeon.class,
        method = "nextRoomTransitionStart"
)
public class NextRoomTransitionPatch {
    public static void Postfix() {
        if (HexaMod.renderFlames) {
            HexaMod.renderFlames = false;
        }
    }
}