package charbosses.patches;

import basemod.BaseMod;
import charbosses.cards.AbstractBossCard;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@SuppressWarnings("unused")
// Copied from Basemod
public class AbstractBossCardPatches {
    @SpirePatch(clz = AbstractCard.class, method = "renderCardBg")
    public static class RenderCardBgPatch {

        public static SpireReturn<?> Prefix(AbstractCard __instance, SpriteBatch sb, float xPos, float yPos, Color ___renderColor) {
            if (!(__instance instanceof AbstractBossCard) || BaseMod.isBaseGameCardColor(__instance.color)) {
                return SpireReturn.Continue();
            }

            AbstractCard.CardColor color = __instance.color;
            Texture texture = null;
            TextureAtlas.AtlasRegion region = null;

            if (texture == null && region == null) {
                switch (__instance.type) {
                    case POWER:
                        if (BaseMod.getPowerBgTexture(color) == null) {
                            BaseMod.savePowerBgTexture(color, ImageMaster.loadImage(BaseMod.getPowerBg(color)));
                        }
                        texture = BaseMod.getPowerBgTexture(color);
                        break;
                    case ATTACK:
                        if (BaseMod.getAttackBgTexture(color) == null) {
                            BaseMod.saveAttackBgTexture(color, ImageMaster.loadImage(BaseMod.getAttackBg(color)));
                        }
                        texture = BaseMod.getAttackBgTexture(color);
                        break;
                    case SKILL:
                        if (BaseMod.getSkillBgTexture(color) == null) {
                            BaseMod.saveSkillBgTexture(color, ImageMaster.loadImage(BaseMod.getSkillBg(color)));
                        }
                        texture = BaseMod.getSkillBgTexture(color);
                        break;
                    default:
                        region = ImageMaster.CARD_SKILL_BG_BLACK;
                        break;
                }
            }

            if (texture != null) {
                region = new TextureAtlas.AtlasRegion(texture, 0, 0, texture.getWidth(), texture.getHeight());
            }

            if (region == null) {
                return SpireReturn.Continue();
            }

            try {
                Method renderHelper = AbstractCard.class.getDeclaredMethod("renderHelper", SpriteBatch.class, Color.class, TextureAtlas.AtlasRegion.class, float.class, float.class);
                boolean oldAccessible = renderHelper.isAccessible();
                renderHelper.setAccessible(true);
                renderHelper.invoke(__instance, sb, ___renderColor, region, xPos, yPos);
                renderHelper.setAccessible(oldAccessible);
                return SpireReturn.Return(null);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                return SpireReturn.Continue();
            }
        }
    }

    @SpirePatch(clz = AbstractCard.class, method = "renderEnergy")
    public static class RenderEnergyPatch {
        public static ExprEditor Instrument() {
            return new ExprEditor() {
                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getClassName().equals(AbstractCard.class.getName()) && m.getMethodName().equals("renderHelper")) {
                        m.replace("{" +
                                "$3 = " + RenderEnergyPatch.class.getName() + ".getEnergyOrb(this, $3);" +
                                "$_ = $proceed($$);" +
                                "}");
                    }
                }
            };
        }

        public static TextureAtlas.AtlasRegion getEnergyOrb(AbstractCard card, TextureAtlas.AtlasRegion orb) {
            if (!(card instanceof AbstractBossCard)) {
                return orb;
            }

            Texture baseModTexture = BaseMod.getEnergyOrbTexture(card.color);
            if (baseModTexture != null) {
                return new TextureAtlas.AtlasRegion(baseModTexture, 0, 0, baseModTexture.getWidth(), baseModTexture.getHeight());
            }

            switch (card.color) {
                case BLUE:
                    return ImageMaster.CARD_BLUE_ORB;
                case GREEN:
                    return ImageMaster.CARD_GREEN_ORB;
                case RED:
                    return ImageMaster.CARD_RED_ORB;
                case PURPLE:
                    return ImageMaster.CARD_PURPLE_ORB;
                case COLORLESS:
                case CURSE:
                    return ImageMaster.CARD_COLORLESS_ORB;
            }

            Texture texture = ImageMaster.loadImage(BaseMod.getEnergyOrb(card.color));
            BaseMod.saveEnergyOrbTexture(card.color, texture);
            return new TextureAtlas.AtlasRegion(texture, 0, 0, texture.getWidth(), texture.getHeight());
        }
    }
}
