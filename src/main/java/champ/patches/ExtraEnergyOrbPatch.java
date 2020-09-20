package champ.patches;

import champ.ChampMod;
import champ.cards.AbstractChampCard;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;

public class ExtraEnergyOrbPatch {

    private static TextureAtlas.AtlasRegion healthBlob = ChampMod.UIAtlas.findRegion("heartOrb");

    @SpirePatch(clz = AbstractCard.class, method = "renderEnergy")
    public static class SecondEnergyRenderPatch {
        @SpirePostfixPatch
        public static void patch(AbstractCard __instance, SpriteBatch sb) {
            if (__instance instanceof AbstractChampCard) {
                if (CardCrawlGame.isInARun()) {
                    if (((AbstractChampCard) __instance).myHpLossCost > 0) {
                        FontHelper.cardEnergyFont_L.getData().setScale(__instance.drawScale * 0.75F);
                        renderHelper(sb, healthBlob, __instance.current_x, __instance.current_y, __instance);
                        FontHelper.renderRotatedText(sb, FontHelper.cardEnergyFont_L, Integer.toString(((AbstractChampCard) __instance).myHpLossCost), __instance.current_x, __instance.current_y, -135.0F * __instance.drawScale * Settings.scale, -50 * __instance.drawScale * Settings.scale, __instance.angle, false, Color.WHITE.cpy());
                    }
                }
            }
        }

        private static void renderHelper(SpriteBatch sb, TextureAtlas.AtlasRegion img, float drawX, float drawY, AbstractCard C) {
            sb.setColor(Color.WHITE);
            sb.draw(img, drawX + img.offsetX - (float) img.originalWidth / 2.0F, drawY + img.offsetY - (float) img.originalHeight / 2.0F, (float) img.originalWidth / 2.0F - img.offsetX, (float) img.originalHeight / 2.0F - img.offsetY, (float) img.packedWidth, (float) img.packedHeight, C.drawScale * Settings.scale, C.drawScale * Settings.scale, C.angle);
        }
    }
}