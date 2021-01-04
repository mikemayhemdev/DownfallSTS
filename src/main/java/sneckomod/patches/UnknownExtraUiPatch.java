package sneckomod.patches;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import sneckomod.SneckoMod;
import sneckomod.cards.unknowns.AbstractUnknownCard;

@SpirePatch(
        clz = AbstractCard.class,
        method = SpirePatch.CLASS
)
public class UnknownExtraUiPatch {
    public static SpireField<AbstractUnknownCard> parentCard = new SpireField<>(() -> null);

    @SpirePatch(clz = AbstractCard.class, method = "renderEnergy")
    public static class SecondEnergyRenderPatch {
        @SpirePostfixPatch
        public static void patch(AbstractCard __instance, SpriteBatch sb) {
            if (parentCard.get(__instance) != null) {
                renderHelper(sb, SneckoMod.overBannerAll, __instance.current_x, __instance.current_y, __instance);
                renderHelper(sb, parentCard.get(__instance).getOverBannerTex(), __instance.current_x, __instance.current_y, __instance);
            }
        }

        private static void renderHelper(SpriteBatch sb, TextureAtlas.AtlasRegion img, float drawX, float drawY, AbstractCard C) {
            Color color = Color.WHITE.cpy();
            color.a = C.transparency; // i wish i'd come up with this earlier. makes things so much less ugly
            sb.draw(img, drawX + img.offsetX - (float) img.originalWidth / 2.0F, drawY + img.offsetY - (float) img.originalHeight / 2.0F, (float) img.originalWidth / 2.0F - img.offsetX, (float) img.originalHeight / 2.0F - img.offsetY, (float) img.packedWidth, (float) img.packedHeight, C.drawScale * Settings.scale, C.drawScale * Settings.scale, C.angle);
        }
    }
}