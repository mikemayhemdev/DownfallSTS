package collector.patches.ExtraDeckButtonPatches;

import collector.CollectorMod;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.ui.panels.DrawPilePanel;

@SpirePatch(
        clz = DrawPilePanel.class,
        method = "render"
)
public class RenderFrozenButtonHook {
    public static void Postfix(DrawPilePanel __instance, SpriteBatch spriteBatch) {
        CollectorMod.renderCombatUiElements(spriteBatch);
    }
}
