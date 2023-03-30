package automaton;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;

import static com.megacrit.cardcrawl.core.Settings.TOP_PANEL_SHADOW_COLOR;

public class SuperTip {
    private static final Color BASE_COLOR = new Color(1.0F, 0.9725F, 0.8745F, 1.0F);
    private static final float SHADOW_DIST_Y = 14.0F * Settings.scale;
    private static final float SHADOW_DIST_X = 9.0F * Settings.scale;
    private static final float BOX_EDGE_H = 32.0F * Settings.scale;
    private static final float BOX_BODY_H = 64.0F * Settings.scale;
    private static final float TEXT_OFFSET_X = 20.0F * Settings.scale;
    private static final float HEADER_OFFSET_Y = 12.0F * Settings.scale;
    private static final float BODY_OFFSET_Y = -20.0F * Settings.scale;
    private static final float TIP_DESC_LINE_SPACING = 26.0F * Settings.scale;
    private static final float PADDING_WIDTH_WRAP = 20.0F * Settings.scale;

    public static void render(SpriteBatch sb, EasyInfoDisplayPanel.RENDER_TIMING t) {
        if (!AbstractDungeon.isScreenUp) {
            for (EasyInfoDisplayPanel d : EasyInfoDisplayPanel.specialDisplays) {
                String s = d.getDescription();
                if (d.getTiming() == t && s != null && !s.equals("") && !s.equals("NORENDER")) {
                    if (!Settings.hidePopupDetails) {
                        renderTipBox(d.x, d.y, d.width, sb, d.getTitle(), d.getDescription());
                    }
                }
            }
        }
    }


    private static void renderTipBox(float x, float y, float width, SpriteBatch sb, String title, String description) {
        float ourWidth = width - (15 * Settings.scale);
        float h = -FontHelper.getSmartHeight(FontHelper.tipBodyFont, description, ourWidth, TIP_DESC_LINE_SPACING) + 20.0F * Settings.scale;
        sb.setColor(TOP_PANEL_SHADOW_COLOR);
        sb.draw(ImageMaster.KEYWORD_TOP, x + SHADOW_DIST_X, y - SHADOW_DIST_Y, ourWidth, BOX_EDGE_H);
        sb.draw(ImageMaster.KEYWORD_BODY, x + SHADOW_DIST_X, y - h - BOX_EDGE_H - SHADOW_DIST_Y, ourWidth, h + BOX_EDGE_H);
        sb.draw(ImageMaster.KEYWORD_BOT, x + SHADOW_DIST_X, y - h - BOX_BODY_H - SHADOW_DIST_Y, ourWidth, BOX_EDGE_H);
        sb.setColor(Color.WHITE.cpy());
        sb.draw(ImageMaster.KEYWORD_TOP, x, y, ourWidth, BOX_EDGE_H);
        sb.draw(ImageMaster.KEYWORD_BODY, x, y - h - BOX_EDGE_H, ourWidth, h + BOX_EDGE_H);
        sb.draw(ImageMaster.KEYWORD_BOT, x, y - h - BOX_BODY_H, ourWidth, BOX_EDGE_H);
        FontHelper.renderFontLeftTopAligned(sb, FontHelper.tipHeaderFont, title, x + TEXT_OFFSET_X, y + HEADER_OFFSET_Y, Settings.GOLD_COLOR);
        FontHelper.renderSmartText(sb, FontHelper.tipBodyFont, description, x + TEXT_OFFSET_X, y + BODY_OFFSET_Y, ourWidth - (PADDING_WIDTH_WRAP + TEXT_OFFSET_X), TIP_DESC_LINE_SPACING, BASE_COLOR);
    }
}
