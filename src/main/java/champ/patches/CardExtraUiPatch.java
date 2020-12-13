package champ.patches;

import champ.ChampMod;
import champ.cards.*;
import champ.stances.BerserkerStance;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class CardExtraUiPatch {

    private static TextureAtlas.AtlasRegion healthBlob = ChampMod.UIAtlas.findRegion("heartOrb");
    private static TextureAtlas.AtlasRegion crown = ChampMod.UIAtlas.findRegion("crown");
    private static TextureAtlas.AtlasRegion openerall = ChampMod.UIAtlas.findRegion("openerAll");
    private static TextureAtlas.AtlasRegion finisher = ChampMod.UIAtlas.findRegion("finisher");
    private static TextureAtlas.AtlasRegion openerR = ChampMod.UIAtlas.findRegion("openerR");
    private static TextureAtlas.AtlasRegion openerB = ChampMod.UIAtlas.findRegion("openerB");
    private static TextureAtlas.AtlasRegion openerY = ChampMod.UIAtlas.findRegion("openerY");
    private static TextureAtlas.AtlasRegion openerYR = ChampMod.UIAtlas.findRegion("openerYR");
    private static TextureAtlas.AtlasRegion openerYB = ChampMod.UIAtlas.findRegion("openerYB");
    private static TextureAtlas.AtlasRegion openerRB = ChampMod.UIAtlas.findRegion("openerRB");

    @SpirePatch(clz = AbstractCard.class, method = "renderEnergy")
    public static class SecondEnergyRenderPatch {
        @SpirePostfixPatch
        public static void patch(AbstractCard __instance, SpriteBatch sb) {
            if (__instance instanceof AbstractChampCard) {
                if (CardCrawlGame.isInARun()) {
                    if (((AbstractChampCard) __instance).myHpLossCost > 0) { // Berserker draw stuff.
                        FontHelper.cardEnergyFont_L.getData().setScale(__instance.drawScale);
                        renderHelper(sb, healthBlob, __instance.current_x, __instance.current_y, __instance);
                        int x = ((AbstractChampCard) __instance).myHpLossCost;
                        if (__instance.hasTag(ChampMod.TECHNIQUE) && AbstractDungeon.player.stance.ID.equals(BerserkerStance.STANCE_ID))
                            x += 3;
                        FontHelper.renderRotatedText(sb, FontHelper.cardEnergyFont_L, Integer.toString(x), __instance.current_x, __instance.current_y, -133.0F * __instance.drawScale * Settings.scale, 133 * __instance.drawScale * Settings.scale, __instance.angle, false, Color.WHITE.cpy());
                    } else if ((__instance.hasTag(ChampMod.TECHNIQUE) && AbstractDungeon.player.stance.ID.equals(BerserkerStance.STANCE_ID)) && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
                        FontHelper.cardEnergyFont_L.getData().setScale(__instance.drawScale);
                        renderHelper(sb, healthBlob, __instance.current_x, __instance.current_y, __instance);
                        FontHelper.renderRotatedText(sb, FontHelper.cardEnergyFont_L, "3", __instance.current_x, __instance.current_y, -133.0F * __instance.drawScale * Settings.scale, 133 * __instance.drawScale * Settings.scale, __instance.angle, false, Color.WHITE.cpy());
                    }
                    //Opener / Technique / Finisher draws
                    if (__instance.hasTag(ChampMod.OPENER)) {
                        if (__instance instanceof Aggression){
                            renderHelper(sb, openerYR, __instance.current_x, __instance.current_y, __instance);
                        }
                        else if (__instance instanceof Balance){
                            renderHelper(sb, openerYB, __instance.current_x, __instance.current_y, __instance);
                        }
                        else if (__instance instanceof Control){
                            renderHelper(sb, openerRB, __instance.current_x, __instance.current_y, __instance);
                        }
                        else if (__instance instanceof StanceDance || __instance instanceof Taunt){
                            renderHelper(sb, openerall, __instance.current_x, __instance.current_y, __instance);
                        }
                        else if (__instance.hasTag(ChampMod.OPENERBERSERKER)){
                            renderHelper(sb, openerR, __instance.current_x, __instance.current_y, __instance);
                        }
                        else if (__instance.hasTag(ChampMod.OPENERGLADIATOR)){
                            renderHelper(sb, openerY, __instance.current_x, __instance.current_y, __instance);
                        }
                        else {
                            renderHelper(sb, openerB, __instance.current_x, __instance.current_y, __instance);
                        }

                        FontHelper.cardEnergyFont_L.getData().setScale(__instance.drawScale);
                    }
                    if (__instance.hasTag(ChampMod.TECHNIQUE)) {
                        FontHelper.cardEnergyFont_L.getData().setScale(__instance.drawScale);
                        renderHelper(sb, crown, __instance.current_x, __instance.current_y, __instance);
                    }
                    if (__instance.hasTag(ChampMod.FINISHER)) {
                        FontHelper.cardEnergyFont_L.getData().setScale(__instance.drawScale);
                        renderHelper(sb, finisher, __instance.current_x, __instance.current_y, __instance);
                    }
                }
            }
        }

        private static void renderHelper(SpriteBatch sb, TextureAtlas.AtlasRegion img, float drawX, float drawY, AbstractCard C) {
            Color color = Color.WHITE.cpy();
            color.a = C.transparency; // i wish i'd come up with this earlier. makes things so much less ugly
            sb.draw(img, drawX + img.offsetX - (float) img.originalWidth / 2.0F, drawY + img.offsetY - (float) img.originalHeight / 2.0F, (float) img.originalWidth / 2.0F - img.offsetX, (float) img.originalHeight / 2.0F - img.offsetY, (float) img.packedWidth, (float) img.packedHeight, C.drawScale * Settings.scale, C.drawScale * Settings.scale, C.angle);
        }
    }
}