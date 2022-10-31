package collector.patches.TorchHeadPatches;

import collector.CollectorChar;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.ui.panels.TopPanel;

public class AllyRenderHPPatch {
    @SpirePatch(clz = TopPanel.class, method = "renderHP")
    public static class DragonStartTurn {
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(TopPanel __instance, SpriteBatch sb, Hitbox ___hpHb, float ___hpIconX, float ___ICON_Y, float ___HP_NUM_OFFSET_X, float ___INFO_TEXT_Y) {
            if (AbstractDungeon.player instanceof CollectorChar) {
                sb.setColor(Color.WHITE);
                float offset = 16.0f * Settings.scale;
                float scale = ___hpHb.hovered ? Settings.scale * 1.2F : Settings.scale;
                sb.draw(
                        ImageMaster.TP_HP,
                        ___hpIconX - 32.0F + 32.0F * Settings.scale,
                        ___ICON_Y - 32.0F + 32.0F * Settings.scale,
                        32.0F, 32.0F, 64.0F, 64.0F,
                        scale, scale, 0.0F, 0, 0, 64, 64, false, false);

                FontHelper.renderFontLeftTopAligned(
                        sb, FontHelper.topPanelInfoFont,
                        AbstractDungeon.player.currentHealth + "/" + AbstractDungeon.player.maxHealth,
                        ___hpIconX + ___HP_NUM_OFFSET_X, ___INFO_TEXT_Y + offset, Color.SALMON);

                FontHelper.renderFontLeftTopAligned(
                        sb, FontHelper.topPanelInfoFont,
                        ((CollectorChar) AbstractDungeon.player).torch.currentHealth + "/" + ((CollectorChar) AbstractDungeon.player).torch.maxHealth,
                        ___hpIconX + ___HP_NUM_OFFSET_X, ___INFO_TEXT_Y - offset, Color.CHARTREUSE);

                ___hpHb.render(sb);
                return SpireReturn.Return(null);
            } else {
                return SpireReturn.Continue();
            }
        }
    }
}
