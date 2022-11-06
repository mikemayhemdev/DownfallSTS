package downfall.patches;

import basemod.helpers.CardModifierManager;
import collector.cardmods.PyreMod;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import downfall.downfallMod;

public class GlobalExtraCardUIPatch {

    private static final TextureAtlas.AtlasRegion healthBlob = downfallMod.UIAtlas.findRegion("heartOrb");
    private static final TextureAtlas.AtlasRegion crown = downfallMod.UIAtlas.findRegion("crown");

    @SpirePatch(clz = AbstractCard.class, method = "renderEnergy")
    public static class SecondEnergyRenderPatch {
        @SpirePrefixPatch
        public static void patch(AbstractCard __instance, SpriteBatch sb) {
            // Commented out for now. TODO: Fix
//            if (UnknownExtraUiPatch.parentCard.get(__instance) != null) {
//                renderHelper(sb, SneckoMod.overBannerAll, __instance.current_x, __instance.current_y, __instance);
//                renderHelper(sb, UnknownExtraUiPatch.parentCard.get(__instance).getOverBannerTex(), __instance.current_x, __instance.current_y, __instance);
//            }

            if (CardModifierManager.hasModifier(__instance, PyreMod.ID)) {
                int x = ((PyreMod) CardModifierManager.getModifiers(__instance, PyreMod.ID).get(0)).exhaustAmt;
                FontHelper.cardEnergyFont_L.getData().setScale(__instance.drawScale);
                renderHelper(sb, healthBlob, __instance.current_x, __instance.current_y, __instance);
                FontHelper.renderRotatedText(sb, FontHelper.cardEnergyFont_L, Integer.toString(x), __instance.current_x, __instance.current_y, -133.0F * __instance.drawScale * Settings.scale, 133 * __instance.drawScale * Settings.scale, __instance.angle, false, Color.WHITE.cpy());
            }


            if (__instance.getClass().getName().startsWith("champ.cards")) {
                FontHelper.cardEnergyFont_L.getData().setScale(__instance.drawScale);
                renderHelper(sb, crown, __instance.current_x, __instance.current_y, __instance);
            }
        }

        private static void renderHelper(SpriteBatch sb, TextureAtlas.AtlasRegion img, float drawX, float drawY, AbstractCard C) {
            Color color = Color.WHITE.cpy();
            color.a = C.transparency;
            sb.setColor(color);
            sb.draw(img, drawX + img.offsetX - (float) img.originalWidth / 2.0F, drawY + img.offsetY - (float) img.originalHeight / 2.0F, (float) img.originalWidth / 2.0F - img.offsetX, (float) img.originalHeight / 2.0F - img.offsetY, (float) img.packedWidth, (float) img.packedHeight, C.drawScale * Settings.scale, C.drawScale * Settings.scale, C.angle);
        }
    }
}