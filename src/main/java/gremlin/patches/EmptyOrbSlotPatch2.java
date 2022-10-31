package gremlin.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;

@SpirePatch(clz = AbstractOrb.class, method = "update")
public class EmptyOrbSlotPatch2 {
    public static SpireReturn Prefix(AbstractOrb __instance) {
        if (AbstractDungeon.player.chosenClass == GremlinEnum.GREMLIN && __instance instanceof EmptyOrbSlot) {
            return SpireReturn.Return(null);
        }
        return SpireReturn.Continue();
    }
}


