//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package champ;

import champ.powers.CalledShotPower;
import champ.stances.BerserkerStance;
import champ.stances.DefensiveStance;
import champ.stances.UltimateStance;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.UIStrings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;

public class TipHelperChamp3 {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private static final Logger logger;
    //private static boolean renderedTipThisFrame;
    private static boolean isCard;
    private static float drawX;
    private static float drawY;
    private static ArrayList<String> KEYWORDS;
    private static ArrayList<PowerTip> POWER_TIPS;
    private static String HEADER;
    private static String BODY;
    private static AbstractCard card;
    private static final Color BASE_COLOR;
    private static final float CARD_TIP_PAD;
    private static final float SHADOW_DIST_Y;
    private static final float SHADOW_DIST_X;
    private static final float BOX_EDGE_H;
    private static final float BOX_BODY_H;
    private static final float BOX_W;
    private static GlyphLayout gl;
    private static float textHeight;
    private static final float TEXT_OFFSET_X;
    private static final float HEADER_OFFSET_Y;
    private static final float ORB_OFFSET_Y;
    private static final float BODY_OFFSET_Y;
    private static final float BODY_TEXT_WIDTH;
    private static final float TIP_DESC_LINE_SPACING;
    private static final float POWER_ICON_OFFSET_X;

    private static Color currentColor;

    public static AbstractCard rememberedCard = null;

    public static Texture KEYWORD_TOP;
    public static Texture KEYWORD_BODY;
    public static Texture KEYWORD_BOT;

    public TipHelperChamp3() {
    }

    private static void initalize() {
        /*
        KEYWORD_TOP = ImageMaster.loadImage("champResources/images/ui/tipTop.png");
        KEYWORD_BODY = ImageMaster.loadImage("champResources/images/ui/tipMid.png");
        KEYWORD_BOT = ImageMaster.loadImage("champResources/images/ui/tipBot.png");
        */
    }

    public static void render(SpriteBatch sb) {

        if (!Settings.hidePopupDetails) {

            sb.setColor(Color.WHITE.cpy());
            currentColor = Color.WHITE.cpy();
            if (rememberedCard == null) {
                if (AbstractDungeon.player != null) {
                    rememberedCard = AbstractDungeon.player.hoveredCard;
                }
            } else if (AbstractDungeon.player.hoveredCard == null) {
                rememberedCard = null;
            }

            if (rememberedCard != null) {
                if (rememberedCard.hasTag(ChampMod.FINISHER)) {
                    if ((AbstractDungeon.player.stance instanceof BerserkerStance ||
                            AbstractDungeon.player.stance instanceof DefensiveStance || AbstractDungeon.player.stance instanceof UltimateStance) &&
                            !AbstractDungeon.player.hasPower(CalledShotPower.POWER_ID)
                    ) {
                        currentColor = new Color(0.5F, TipHelperChamp2.greenValue, 0.5F, 1F);
                    }
                }
            }


            if (isCard && card != null) {
                if (card.current_x > (float) Settings.WIDTH * 0.75F) {
                    renderKeywords(card.current_x - AbstractCard.IMG_WIDTH / 2.0F - CARD_TIP_PAD - BOX_W, card.current_y + AbstractCard.IMG_HEIGHT / 2.0F - BOX_EDGE_H, sb, KEYWORDS);
                } else {
                    renderKeywords(card.current_x + AbstractCard.IMG_WIDTH / 2.0F + CARD_TIP_PAD, card.current_y + AbstractCard.IMG_HEIGHT / 2.0F - BOX_EDGE_H, sb, KEYWORDS);
                }

                card = null;
                isCard = false;
            } else if (HEADER != null) {
                textHeight = -FontHelper.getSmartHeight(FontHelper.tipBodyFont, BODY, BODY_TEXT_WIDTH, TIP_DESC_LINE_SPACING) - 7.0F * Settings.scale;
                renderTipBox(drawX, drawY, sb, HEADER, BODY);
                HEADER = null;
            } else {
                renderPowerTips(drawX, drawY, sb, POWER_TIPS);
            }

        }

    }

    public static void renderGenericTip(float x, float y, String header, String body) {
        if (!Settings.hidePopupDetails) {
            HEADER = header;
            BODY = body;
            drawX = x;
            drawY = y;
        }

    }

    private static void renderPowerTips(float x, float y, SpriteBatch sb, ArrayList<PowerTip> powerTips) {
        float originalY = y;
        boolean offsetLeft = false;
        if (x > (float) Settings.WIDTH / 2.0F) {
            offsetLeft = true;
        }

        float offset = 0.0F;

        float offsetChange;
        for (Iterator var7 = powerTips.iterator(); var7.hasNext(); offset += offsetChange) {
            PowerTip tip = (PowerTip) var7.next();
            textHeight = getPowerTipHeight(tip);
            offsetChange = textHeight + BOX_EDGE_H * 3.15F;
            if (offset + offsetChange >= (float) Settings.HEIGHT * 0.7F) {
                y = originalY;
                offset = 0.0F;
                if (offsetLeft) {
                    x -= 324.0F * Settings.scale;
                } else {
                    x += 324.0F * Settings.scale;
                }
            }

            renderTipBox(x, y, sb, tip.header, tip.body);
            gl.setText(FontHelper.tipHeaderFont, tip.header, Color.WHITE, 0.0F, -1, false);
            if (tip.img != null) {
                sb.setColor(Color.WHITE);
                sb.draw(tip.img, x + TEXT_OFFSET_X + gl.width + 5.0F * Settings.scale, y - 10.0F * Settings.scale, 32.0F * Settings.scale, 32.0F * Settings.scale);
            } else if (tip.imgRegion != null) {
                sb.setColor(Color.WHITE);
                sb.draw(tip.imgRegion, x + gl.width + POWER_ICON_OFFSET_X - (float) tip.imgRegion.packedWidth / 2.0F, y + 5.0F * Settings.scale - (float) tip.imgRegion.packedHeight / 2.0F, (float) tip.imgRegion.packedWidth / 2.0F, (float) tip.imgRegion.packedHeight / 2.0F, (float) tip.imgRegion.packedWidth, (float) tip.imgRegion.packedHeight, Settings.scale * 0.75F, Settings.scale * 0.75F, 0.0F);
            }

            y -= offsetChange;
        }

    }

    private static float getPowerTipHeight(PowerTip powerTip) {
        return -FontHelper.getSmartHeight(FontHelper.tipBodyFont, powerTip.body, BODY_TEXT_WIDTH, TIP_DESC_LINE_SPACING) - 7.0F * Settings.scale;
    }

    public static float calculateAdditionalOffset(ArrayList<PowerTip> powerTips, float hBcY) {
        return powerTips.isEmpty() ? 0.0F : (1.0F - hBcY / (float) Settings.HEIGHT) * getTallestOffset(powerTips) - (getPowerTipHeight((PowerTip) powerTips.get(0)) + BOX_EDGE_H * 3.15F) / 2.0F;
    }

    public static float calculateToAvoidOffscreen(ArrayList<PowerTip> powerTips, float hBcY) {
        return powerTips.isEmpty() ? 0.0F : Math.max(0.0F, getTallestOffset(powerTips) - hBcY);
    }

    private static float getTallestOffset(ArrayList<PowerTip> powerTips) {
        float currentOffset = 0.0F;
        float maxOffset = 0.0F;

        for (PowerTip p : powerTips) {
            float offsetChange = getPowerTipHeight(p) + BOX_EDGE_H * 3.15F;
            if (currentOffset + offsetChange >= (float) Settings.HEIGHT * 0.7F) {
                currentOffset = 0.0F;
            }

            currentOffset += offsetChange;
            if (currentOffset > maxOffset) {
                maxOffset = currentOffset;
            }
        }

        return maxOffset;
    }

    private static void renderKeywords(float x, float y, SpriteBatch sb, ArrayList<String> keywords) {
        if (keywords.size() >= 4) {
            y += (float) (keywords.size() - 1) * 62.0F * Settings.scale;
        }

        for (String s : keywords) {
            if (!GameDictionary.keywords.containsKey(s)) {
                logger.info("MISSING: " + s + " in Dictionary!");
            } else {
                textHeight = -FontHelper.getSmartHeight(FontHelper.tipBodyFont, (String) GameDictionary.keywords.get(s), BODY_TEXT_WIDTH, TIP_DESC_LINE_SPACING) - 7.0F * Settings.scale;
                renderBox(sb, s, x, y);
                y -= textHeight + BOX_EDGE_H * 3.15F;
            }
        }

    }

    private static void renderTipBox(float x, float y, SpriteBatch sb, String title, String description) {
        float h = textHeight;
        sb.setColor(Settings.TOP_PANEL_SHADOW_COLOR);
        sb.draw(ImageMaster.KEYWORD_TOP, x + SHADOW_DIST_X, y - SHADOW_DIST_Y, BOX_W, BOX_EDGE_H);
        sb.draw(ImageMaster.KEYWORD_BODY, x + SHADOW_DIST_X, y - h - BOX_EDGE_H - SHADOW_DIST_Y, BOX_W, h + BOX_EDGE_H);
        sb.draw(ImageMaster.KEYWORD_BOT, x + SHADOW_DIST_X, y - h - BOX_BODY_H - SHADOW_DIST_Y, BOX_W, BOX_EDGE_H);
        sb.setColor(currentColor);
        sb.draw(ImageMaster.KEYWORD_TOP, x, y, BOX_W, BOX_EDGE_H);
        sb.draw(ImageMaster.KEYWORD_BODY, x, y - h - BOX_EDGE_H, BOX_W, h + BOX_EDGE_H);
        sb.draw(ImageMaster.KEYWORD_BOT, x, y - h - BOX_BODY_H, BOX_W, BOX_EDGE_H);
        /*
        if (rememberedCard == null){
        else {
            sb.draw(KEYWORD_TOP, x, y, BOX_W, BOX_EDGE_H);
            sb.draw(KEYWORD_BODY, x, y - h - BOX_EDGE_H, BOX_W, h + BOX_EDGE_H);
            sb.draw(KEYWORD_BOT, x, y - h - BOX_BODY_H, BOX_W, BOX_EDGE_H);}
            */

        FontHelper.renderFontLeftTopAligned(sb, FontHelper.tipHeaderFont, title, x + TEXT_OFFSET_X, y + HEADER_OFFSET_Y, Settings.GOLD_COLOR);
        FontHelper.renderSmartText(sb, FontHelper.tipBodyFont, description, x + TEXT_OFFSET_X, y + BODY_OFFSET_Y, BODY_TEXT_WIDTH, TIP_DESC_LINE_SPACING, BASE_COLOR);
    }

    public static void renderTipEnergy(SpriteBatch sb, AtlasRegion region, float x, float y) {
        sb.setColor(Color.WHITE);
        sb.draw(region.getTexture(), x + region.offsetX * Settings.scale, y + region.offsetY * Settings.scale, 0.0F, 0.0F, (float) region.packedWidth, (float) region.packedHeight, Settings.scale, Settings.scale, 0.0F, region.getRegionX(), region.getRegionY(), region.getRegionWidth(), region.getRegionHeight(), false, false);
    }

    private static void renderBox(SpriteBatch sb, String word, float x, float y) {
        float h = textHeight;
        sb.setColor(Settings.TOP_PANEL_SHADOW_COLOR);
        sb.draw(ImageMaster.KEYWORD_TOP, x + SHADOW_DIST_X, y - SHADOW_DIST_Y, BOX_W, BOX_EDGE_H);
        sb.draw(ImageMaster.KEYWORD_BODY, x + SHADOW_DIST_X, y - h - BOX_EDGE_H - SHADOW_DIST_Y, BOX_W, h + BOX_EDGE_H);
        sb.draw(ImageMaster.KEYWORD_BOT, x + SHADOW_DIST_X, y - h - BOX_BODY_H - SHADOW_DIST_Y, BOX_W, BOX_EDGE_H);
        sb.setColor(currentColor);
        sb.draw(ImageMaster.KEYWORD_TOP, x, y, BOX_W, BOX_EDGE_H);
        sb.draw(ImageMaster.KEYWORD_BODY, x, y - h - BOX_EDGE_H, BOX_W, h + BOX_EDGE_H);
        sb.draw(ImageMaster.KEYWORD_BOT, x, y - h - BOX_BODY_H, BOX_W, BOX_EDGE_H);
        AtlasRegion currentOrb = AbstractDungeon.player != null ? AbstractDungeon.player.getOrb() : AbstractCard.orb_red;
        if (!word.equals("[R]") && !word.equals("[G]") && !word.equals("[B]") && !word.equals("[W]") && !word.equals("[E]")) {
            FontHelper.renderFontLeftTopAligned(sb, FontHelper.tipHeaderFont, capitalize(word), x + TEXT_OFFSET_X, y + HEADER_OFFSET_Y, Settings.GOLD_COLOR);
        } else {
            if (word.equals("[R]")) {
                renderTipEnergy(sb, AbstractCard.orb_red, x + TEXT_OFFSET_X, y + ORB_OFFSET_Y);
            } else if (word.equals("[G]")) {
                renderTipEnergy(sb, AbstractCard.orb_green, x + TEXT_OFFSET_X, y + ORB_OFFSET_Y);
            } else if (word.equals("[B]")) {
                renderTipEnergy(sb, AbstractCard.orb_blue, x + TEXT_OFFSET_X, y + ORB_OFFSET_Y);
            } else if (word.equals("[W]")) {
                renderTipEnergy(sb, AbstractCard.orb_purple, x + TEXT_OFFSET_X, y + ORB_OFFSET_Y);
            } else if (word.equals("[E]")) {
                renderTipEnergy(sb, currentOrb, x + TEXT_OFFSET_X, y + ORB_OFFSET_Y);
            }

            FontHelper.renderFontLeftTopAligned(sb, FontHelper.tipHeaderFont, capitalize(TEXT[0]), x + TEXT_OFFSET_X * 2.5F, y + HEADER_OFFSET_Y, Settings.GOLD_COLOR);
        }

        FontHelper.renderSmartText(sb, FontHelper.tipBodyFont, (String) GameDictionary.keywords.get(word), x + TEXT_OFFSET_X, y + BODY_OFFSET_Y, BODY_TEXT_WIDTH, TIP_DESC_LINE_SPACING, BASE_COLOR);
    }

    public static String capitalize(String input) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < input.length(); ++i) {
            char tmp = input.charAt(i);
            if (i == 0) {
                tmp = Character.toUpperCase(tmp);
            } else {
                tmp = Character.toLowerCase(tmp);
            }

            sb.append(tmp);
        }

        return sb.toString();
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("TipHelper");
        TEXT = uiStrings.TEXT;
        logger = LogManager.getLogger(TipHelperChamp3.class.getName());
        isCard = false;
        KEYWORDS = new ArrayList();
        POWER_TIPS = new ArrayList();
        HEADER = null;
        BODY = null;
        BASE_COLOR = new Color(1.0F, 0.9725F, 0.8745F, 1.0F);
        CARD_TIP_PAD = 12.0F * Settings.scale;
        SHADOW_DIST_Y = 14.0F * Settings.scale;
        SHADOW_DIST_X = 9.0F * Settings.scale;
        BOX_EDGE_H = 32.0F * Settings.scale;
        BOX_BODY_H = 64.0F * Settings.scale;
        BOX_W = 320.0F * Settings.scale;
        gl = new GlyphLayout();
        TEXT_OFFSET_X = 22.0F * Settings.scale;
        HEADER_OFFSET_Y = 12.0F * Settings.scale;
        ORB_OFFSET_Y = -8.0F * Settings.scale;
        BODY_OFFSET_Y = -20.0F * Settings.scale;
        BODY_TEXT_WIDTH = 280F * Settings.scale;
        TIP_DESC_LINE_SPACING = 26.0F * Settings.scale;
        POWER_ICON_OFFSET_X = 40.0F * Settings.scale;
    }

    private static void renderHelper(SpriteBatch sb, TextureAtlas.AtlasRegion img, float drawX, float drawY) {
        sb.draw(img, drawX + img.offsetX - (float) img.originalWidth / 2.0F, drawY + img.offsetY - (float) img.originalHeight / 2.0F, (float) img.originalWidth / 2.0F - img.offsetX, (float) img.originalHeight / 2.0F - img.offsetY, (float) img.packedWidth, (float) img.packedHeight, Settings.scale, Settings.scale, 0F);
    }
}
