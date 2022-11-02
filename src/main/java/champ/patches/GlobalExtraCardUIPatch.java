package champ.patches;

import champ.ChampMod;
import champ.cards.AbstractChampCard;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import sneckomod.SneckoMod;
import sneckomod.patches.UnknownExtraUiPatch;

public class GlobalExtraCardUIPatch {

    private static final TextureAtlas.AtlasRegion healthBlob = ChampMod.UIAtlas.findRegion("heartOrb");
    private static final TextureAtlas.AtlasRegion crown = ChampMod.UIAtlas.findRegion("crown");
    private static final TextureAtlas.AtlasRegion openerall = ChampMod.UIAtlas.findRegion("openerAll");
    private static final TextureAtlas.AtlasRegion finisher = ChampMod.UIAtlas.findRegion("finisher");
    private static final TextureAtlas.AtlasRegion openerR = ChampMod.UIAtlas.findRegion("openerR");
    private static final TextureAtlas.AtlasRegion openerB = ChampMod.UIAtlas.findRegion("openerB");
    private static final TextureAtlas.AtlasRegion openerY = ChampMod.UIAtlas.findRegion("openerY");
    private static final TextureAtlas.AtlasRegion openerYR = ChampMod.UIAtlas.findRegion("openerYR");
    private static final TextureAtlas.AtlasRegion openerYB = ChampMod.UIAtlas.findRegion("openerYB");
    private static final TextureAtlas.AtlasRegion openerRB = ChampMod.UIAtlas.findRegion("openerRB");

    @SpirePatch(clz = AbstractCard.class, method = "renderEnergy")
    public static class SecondEnergyRenderPatch {
        @SpirePrefixPatch
        public static void patch(AbstractCard __instance, SpriteBatch sb) {
            if (UnknownExtraUiPatch.parentCard.get(__instance) != null) {
                renderHelper(sb, SneckoMod.overBannerAll, __instance.current_x, __instance.current_y, __instance);
                renderHelper(sb, UnknownExtraUiPatch.parentCard.get(__instance).getOverBannerTex(), __instance.current_x, __instance.current_y, __instance);
            }

            /*
            if (__instance instanceof AbstractChampCard) {
                if (((AbstractChampCard) __instance).myHpLossCost > 0) { // Berserker draw stuff.
                    FontHelper.cardEnergyFont_L.getData().setScale(__instance.drawScale);
                    renderHelper(sb, healthBlob, __instance.current_x, __instance.current_y, __instance);
                    int x = ((AbstractChampCard) __instance).myHpLossCost;
                    FontHelper.renderRotatedText(sb, FontHelper.cardEnergyFont_L, Integer.toString(x), __instance.current_x, __instance.current_y, -133.0F * __instance.drawScale * Settings.scale, 133 * __instance.drawScale * Settings.scale, __instance.angle, false, Color.WHITE.cpy());
                }

            }

             */

            //Opener / Technique / Finisher draws
            /*
            if (__instance.hasTag(ChampMod.OPENER)) {
                if (__instance instanceof StanceDance || __instance instanceof Taunt || __instance instanceof WindUp) {
                    renderHelper(sb, openerall, __instance.current_x, __instance.current_y, __instance);
                } else if (__instance.hasTag(ChampMod.OPENERBERSERKER)) {
                    renderHelper(sb, openerR, __instance.current_x, __instance.current_y, __instance);
                } else {
                    renderHelper(sb, openerB, __instance.current_x, __instance.current_y, __instance);
                }

                FontHelper.cardEnergyFont_L.getData().setScale(__instance.drawScale);
            }

             */

            if (__instance instanceof AbstractChampCard) {
                FontHelper.cardEnergyFont_L.getData().setScale(__instance.drawScale);
                renderHelper(sb, crown, __instance.current_x, __instance.current_y, __instance);
            }


/*
            if (__instance.hasTag(ChampMod.FINISHER)) {
                FontHelper.cardEnergyFont_L.getData().setScale(__instance.drawScale);
                renderHelper(sb, finisher, __instance.current_x, __instance.current_y, __instance);
            }

             */
        }

        private static void renderHelper(SpriteBatch sb, TextureAtlas.AtlasRegion img, float drawX, float drawY, AbstractCard C) {
            Color color = Color.WHITE.cpy();
            color.a = C.transparency; // i wish i'd come up with this earlier. makes things so much less ugly
            sb.setColor(color);
            if (img == null) {
                System.out.println("HELP!");
                img = ChampMod.UIAtlas.findRegion("crown");
            }
            sb.draw(img, drawX + img.offsetX - (float) img.originalWidth / 2.0F, drawY + img.offsetY - (float) img.originalHeight / 2.0F, (float) img.originalWidth / 2.0F - img.offsetX, (float) img.originalHeight / 2.0F - img.offsetY, (float) img.packedWidth, (float) img.packedHeight, C.drawScale * Settings.scale, C.drawScale * Settings.scale, C.angle);
        }
    }
}