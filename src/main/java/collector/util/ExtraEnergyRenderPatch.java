package collector.util;


import collector.CollectorChar;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static charbosses.ui.EnemyEnergyPanel.ENERGY_TEXT_COLOR;

public class ExtraEnergyRenderPatch {
    @SpirePatch(clz = EnergyPanel.class, method = "render")
    public static class ShowTextForReserves {
        public static void Postfix(EnergyPanel __instance, SpriteBatch sb) {
            int found = NewReserves.reserveCount();
            if (AbstractDungeon.player.chosenClass.equals(CollectorChar.Enums.THE_COLLECTOR) || found > 0) {
                String toShow;
                if (found > 0) {
                    toShow = String.valueOf(found);
                } else {
                    toShow = "0";
                }
                AbstractDungeon.player.getEnergyNumFont().getData().setScale(1F); //TODO: See if this has issues
                FontHelper.renderFontCentered(sb, AbstractDungeon.player.getEnergyNumFont(), toShow, __instance.current_x + DoubleEnergyOrb.X_OFFSET, __instance.current_y + DoubleEnergyOrb.Y_OFFSET, ENERGY_TEXT_COLOR);
            }
        }
    }
}
