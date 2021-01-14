package champ.patches;

import champ.ChampMod;
import downfall.util.TextureLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class SymbolDescriptionPatch {
    private static final String crown_icon_NAME = "crown_icon";
    public static final TextureAtlas.AtlasRegion ICONCROWN = TextureLoader.getTextureAsAtlasRegion(ChampMod.makeUIPath("crowntext.png"));
    private static final String fist_icon_NAME = "fist_icon";
    public static final TextureAtlas.AtlasRegion ICONFIST = TextureLoader.getTextureAsAtlasRegion(ChampMod.makeUIPath("fisttext.png"));
    private static final String card_icon_NAME = "card_icon";
    private static final TextureAtlas.AtlasRegion ICONCARD = TextureLoader.getTextureAsAtlasRegion(ChampMod.makeUIPath("cardtext.png"));

    @SpirePatch(clz = AbstractCard.class, method = "renderDescription")
    @SpirePatch(clz = AbstractCard.class, method = "renderDescriptionCN")
    public static class RenderSmallIcon {
        private static final float CARD_ENERGY_IMG_WIDTH = 24.0f * Settings.scale;

        @SpireInsertPatch(locator = Locator.class, localvars = {"spacing", "i", "start_x", "draw_y", "font", "textColor", "tmp", "gl"})
        public static void Insert(AbstractCard __instance, SpriteBatch sb,
                                  float spacing, int i, @ByRef float[] start_x, float draw_y,
                                  BitmapFont font, Color textColor, @ByRef String[] tmp, GlyphLayout gl) {
            if (tmp[0].length() > 0 && tmp[0].charAt(0) == '[') {
                String key = tmp[0].trim();
                if (key.equals("[" + crown_icon_NAME + "]")) {
                    gl.width = CARD_ENERGY_IMG_WIDTH * __instance.drawScale;
                    renderMPIcon(__instance, sb, ICONCROWN,
                            (start_x[0] - __instance.current_x) / Settings.scale / __instance.drawScale,
                            (-98.0f - ((__instance.description.size() - 4.0f) / 2.0f - i + 1.0f) * spacing));
                    start_x[0] += gl.width;
                    tmp[0] = "";
                }
                if (key.equals("[" + fist_icon_NAME + "]")) {
                    gl.width = CARD_ENERGY_IMG_WIDTH * __instance.drawScale;
                    renderMPIcon(__instance, sb, ICONFIST,
                            (start_x[0] - __instance.current_x) / Settings.scale / __instance.drawScale,
                            (-98.0f - ((__instance.description.size() - 4.0f) / 2.0f - i + 1.0f) * spacing));
                    start_x[0] += gl.width;
                    tmp[0] = "";
                }
                if (key.equals("[" + card_icon_NAME + "]")) {
                    gl.width = CARD_ENERGY_IMG_WIDTH * __instance.drawScale;
                    renderMPIcon(__instance, sb, ICONCARD,
                            (start_x[0] - __instance.current_x) / Settings.scale / __instance.drawScale,
                            (-98.0f - ((__instance.description.size() - 4.0f) / 2.0f - i + 1.0f) * spacing));
                    start_x[0] += gl.width;
                    tmp[0] = "";
                }
            }
        }

        public static void renderMPIcon(AbstractCard card, SpriteBatch sb, TextureAtlas.AtlasRegion region, float offsetX, float offsetY) {
            Affine2 aff = new Affine2();
            aff.setToTrnRotScl(
                    card.current_x + offsetX * card.drawScale * Settings.scale,
                    card.current_y + offsetY * card.drawScale * Settings.scale,
                    MathUtils.degreesToRadians * card.angle,
                    card.drawScale * Settings.scale,
                    card.drawScale * Settings.scale
            );
            sb.draw(
                    region,
                    region.packedWidth,
                    region.packedHeight,
                    aff
            );
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.MethodCallMatcher(GlyphLayout.class, "setText");
                int[] lines = LineFinder.findAllInOrder(ctBehavior, matcher);
                return new int[]{lines[lines.length - 1]}; // Only last occurrence
            }
        }
    }

    @SpirePatch(clz = SingleCardViewPopup.class, method = "renderDescription")
    @SpirePatch(clz = SingleCardViewPopup.class, method = "renderDescriptionCN")
    public static class RenderSmallIconSingleCardView {
        @SpireInsertPatch(locator = Locator.class, localvars = {
                "spacing", "i", "start_x", "tmp", "gl",
                "card_energy_w", "drawScale", "current_x", "card"
        }
        )
        public static void Insert(SingleCardViewPopup __instance, SpriteBatch sb,
                                  float spacing, int i, @ByRef float[] start_x,
                                  @ByRef String[] tmp, GlyphLayout gl,
                                  float card_energy_w, float drawScale, float current_x, AbstractCard card) {
            if (tmp[0].length() > 0 && tmp[0].charAt(0) == '[') {
                String key = tmp[0].trim();
                if (key.equals("[" + crown_icon_NAME + "]")) {
                    gl.width = card_energy_w * drawScale;
                    try {
                        Method renderSmallEnergy = SingleCardViewPopup.class.getDeclaredMethod("renderSmallEnergy", SpriteBatch.class, TextureAtlas.AtlasRegion.class, float.class, float.class);
                        renderSmallEnergy.setAccessible(true);

                        renderSmallEnergy.invoke(__instance, sb, ICONCROWN,
                                (start_x[0] - current_x) / Settings.scale / drawScale,
                                -86.0f - ((card.description.size() - 4.0f) / 2.0f - i + 1.0f) * spacing);
                        sb.flush();
                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    start_x[0] += gl.width;
                    tmp[0] = "";
                }
                if (key.equals("[" + fist_icon_NAME + "]")) {
                    gl.width = card_energy_w * drawScale;
                    try {
                        Method renderSmallEnergy = SingleCardViewPopup.class.getDeclaredMethod("renderSmallEnergy", SpriteBatch.class, TextureAtlas.AtlasRegion.class, float.class, float.class);
                        renderSmallEnergy.setAccessible(true);

                        renderSmallEnergy.invoke(__instance, sb, ICONFIST,
                                (start_x[0] - current_x) / Settings.scale / drawScale,
                                -86.0f - ((card.description.size() - 4.0f) / 2.0f - i + 1.0f) * spacing);
                        sb.flush();
                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    start_x[0] += gl.width;
                    tmp[0] = "";
                }
                if (key.equals("[" + card_icon_NAME + "]")) {
                    gl.width = card_energy_w * drawScale;
                    try {
                        Method renderSmallEnergy = SingleCardViewPopup.class.getDeclaredMethod("renderSmallEnergy", SpriteBatch.class, TextureAtlas.AtlasRegion.class, float.class, float.class);
                        renderSmallEnergy.setAccessible(true);

                        renderSmallEnergy.invoke(__instance, sb, ICONCARD,
                                (start_x[0] - current_x) / Settings.scale / drawScale,
                                -86.0f - ((card.description.size() - 4.0f) / 2.0f - i + 1.0f) * spacing);
                        sb.flush();
                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    start_x[0] += gl.width;
                    tmp[0] = "";
                }
            }
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.MethodCallMatcher(GlyphLayout.class, "setText");
                int[] lines = LineFinder.findAllInOrder(ctBehavior, matcher);
                return new int[]{lines[lines.length - 1]}; // Only last occurrence
            }
        }
    }

    @SpirePatch(clz = AbstractCard.class, method = "initializeDescription")
    public static class AlterIconDescriptionSize {
        private static final float CARD_ENERGY_IMG_WIDTH = 16.0f * Settings.scale;

        @SpireInsertPatch(locator = Locator.class, localvars = {"gl", "word"})
        public static void Insert(AbstractCard __instance, @ByRef GlyphLayout[] gl, String word) {
            if (word.length() > 0 && word.charAt(0) == '[') {
                if (word.equals("[" + crown_icon_NAME + "]") ||
                        word.equals("[" + fist_icon_NAME + "]") ||
                word.equals("[" + card_icon_NAME + "]")) {
                    gl[0].setText(FontHelper.cardDescFont_N, " ");
                    gl[0].width = CARD_ENERGY_IMG_WIDTH;
                }
            }
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "DESC_BOX_WIDTH");
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }

    /*
    private static final String ICON_KEYWORD_CROWN = "champ:[crown_icon]";
    private static final String ICON_KEYWORD_FIST = "champ:[fist_icon]";
    private static final String ICON_KEYWORD_CARD = "champ:[card_icon]";

    @SpirePatch(clz = TipHelper.class, method = "renderTipForCard")
    public static class RenderMPIconKeyword {
        @SpireInsertPatch(locator = Locator.class)
        public static void patch(AbstractCard c, SpriteBatch sb, ArrayList<String> keywords) {
            if (c.rawDescription.toLowerCase().contains("[crown_icon]") && (keywords.isEmpty() || !keywords.get(0).equals(ICON_KEYWORD_CROWN))) {
                keywords.add(0, ICON_KEYWORD_CROWN);
            }
            if (c.rawDescription.toLowerCase().contains("[fist_icon]") && (keywords.isEmpty() || !keywords.get(0).equals(ICON_KEYWORD_FIST))) {
                keywords.add(0, ICON_KEYWORD_FIST);
            }
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(TipHelper.class, "KEYWORDS");
                return LineFinder.findInOrder(ctBehavior, finalMatcher);
            }
        }
    }
    */

    public static TextureAtlas.AtlasRegion workaround() {
        return ICONCROWN;
    }

    //May need to add this patch for ZHS: https://github.com/kiooeht/Bard/blob/master/src/main/java/com/evacipated/cardcrawl/mod/bard/patches/CardDescriptionNoteSymbolsCN.java#L79
}