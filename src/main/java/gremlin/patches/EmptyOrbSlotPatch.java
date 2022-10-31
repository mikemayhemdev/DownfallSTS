package gremlin.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;

@SpirePatch(clz = EmptyOrbSlot.class, method = "render")
public class EmptyOrbSlotPatch {
    public static SpireReturn Prefix(EmptyOrbSlot __instance, SpriteBatch sb) {
        if (AbstractDungeon.player.chosenClass == GremlinEnum.GREMLIN) {
            return SpireReturn.Return(null);
        }
        return SpireReturn.Continue();
    }
}


