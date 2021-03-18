package automaton;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;

import static com.megacrit.cardcrawl.core.Settings.TOP_PANEL_SHADOW_COLOR;

public class SuperTip {
    private static final Color BASE_COLOR = new Color(1.0F, 0.9725F, 0.8745F, 1.0F);
    private static final float SHADOW_DIST_Y = 14.0F * Settings.scale;
    private static final float SHADOW_DIST_X = 9.0F * Settings.scale;
    private static final float BOX_EDGE_H = 32.0F * Settings.scale;
    private static final float BOX_BODY_H = 64.0F * Settings.scale;
    private static final float TEXT_OFFSET_X = 22.0F * Settings.scale;
    private static final float HEADER_OFFSET_Y = 12.0F * Settings.scale;
    private static final float BODY_OFFSET_Y = -20.0F * Settings.scale;
    private static final float TIP_DESC_LINE_SPACING = 26.0F * Settings.scale;
    private static final float PADDING_WIDTH_WRAP = 25.0F * Settings.scale;

    public static void render(SpriteBatch sb, EasyInfoDisplayPanel.RENDER_TIMING t) {
        for (EasyInfoDisplayPanel d : EasyInfoDisplayPanel.specialDisplays) {
            String s = d.getDescription();
            if (d.getTiming() == t && s != null && !s.equals("")) {
                if (!Settings.hidePopupDetails) {
                    renderTipBox(d.x, d.y, d.width, sb, d.getTitle(), d.getDescription());
                }
            }
        }
    }


    private static void renderTipBox(float x, float y, float width, SpriteBatch sb, String title, String description) {
        float h = -FontHelper.getSmartHeight(FontHelper.tipBodyFont, description, width, TIP_DESC_LINE_SPACING) - 7.0F * Settings.scale;
        sb.setColor(TOP_PANEL_SHADOW_COLOR);
        sb.draw(ImageMaster.KEYWORD_TOP, x + SHADOW_DIST_X, y - SHADOW_DIST_Y, width, BOX_EDGE_H);
        sb.draw(ImageMaster.KEYWORD_BODY, x + SHADOW_DIST_X, y - h - BOX_EDGE_H - SHADOW_DIST_Y, width, h + BOX_EDGE_H);
        sb.draw(ImageMaster.KEYWORD_BOT, x + SHADOW_DIST_X, y - h - BOX_BODY_H - SHADOW_DIST_Y, width, BOX_EDGE_H);
        sb.setColor(Color.WHITE.cpy());
        sb.draw(ImageMaster.KEYWORD_TOP, x, y, width, BOX_EDGE_H);
        sb.draw(ImageMaster.KEYWORD_BODY, x, y - h - BOX_EDGE_H, width, h + BOX_EDGE_H);
        sb.draw(ImageMaster.KEYWORD_BOT, x, y - h - BOX_BODY_H, width, BOX_EDGE_H);
        FontHelper.renderFontLeftTopAligned(sb, FontHelper.tipHeaderFont, title, x + TEXT_OFFSET_X, y + HEADER_OFFSET_Y, Settings.GOLD_COLOR);
        FontHelper.renderSmartText(sb, FontHelper.tipBodyFont, description, x + TEXT_OFFSET_X, y + BODY_OFFSET_Y, width - PADDING_WIDTH_WRAP, TIP_DESC_LINE_SPACING, BASE_COLOR);
    }
}
