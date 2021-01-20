package champ;

import basemod.BaseMod;
import champ.patches.SymbolDescriptionPatch;
import champ.stances.AbstractChampStance;
import champ.stances.BerserkerStance;
import champ.stances.DefensiveStance;
import champ.stances.UltimateStance;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import downfall.downfallMod;

public class StanceHelper {

    public static float globalX = 15F * Settings.scale;
    public static float globalY = 900F * Settings.scale;

    public static Hitbox hitboxStance;
    private static Hitbox hitboxTechnique;
    private static Hitbox hitboxFinisher;

    /*
            hitboxStance = new Hitbox(globalX, globalY, 200 * Settings.scale, 100 * Settings.scale);
        hitboxTechnique = new Hitbox(globalX, globalY - (100F * Settings.scale), 200 * Settings.scale, 100 * Settings.scale);
        hitboxFinisher = new Hitbox(globalX, globalY - (200F * Settings.scale), 200 * Settings.scale, 100 * Settings.scale);
     */

    public static Vector2 dx1 = new Vector2(globalX, globalY);
    public static Vector2 dx2 = new Vector2(globalX, globalY - (100F * Settings.scale));
    public static Vector2 dx3 = new Vector2(globalX, globalY - (200F * Settings.scale));

    public static void init() {
        hitboxStance = new Hitbox(globalX, globalY - (50F * Settings.scale), 300 * Settings.scale, 100 * Settings.scale);
        hitboxTechnique = new Hitbox(globalX, globalY - (150F * Settings.scale), 300 * Settings.scale, 100 * Settings.scale);
        hitboxFinisher = new Hitbox(globalX, globalY - (250F * Settings.scale), 300 * Settings.scale, 100 * Settings.scale);
    }

    public static void update() {
        hitboxStance.update();
        hitboxTechnique.update();
        hitboxFinisher.update();
    }

    public static String getStanceName() {
        if (AbstractDungeon.player.stance instanceof BerserkerStance) {
            return ChampChar.characterStrings.TEXT[24];
        } else if (AbstractDungeon.player.stance instanceof DefensiveStance) {
            return ChampChar.characterStrings.TEXT[25];
        } else if (AbstractDungeon.player.stance instanceof UltimateStance) {
            return ChampChar.characterStrings.TEXT[7];
        } else if (AbstractDungeon.player instanceof ChampChar) {
            return ChampChar.characterStrings.TEXT[6];
        }
        return null;
    }


    public static String getStanceTechnique() {
        if (AbstractDungeon.player.stance instanceof BerserkerStance) {
            return ChampChar.characterStrings.TEXT[10] + BerserkerStance.amount() + ChampChar.characterStrings.TEXT[55];
        } else if (AbstractDungeon.player.stance instanceof DefensiveStance) {
            return ChampChar.characterStrings.TEXT[12] + DefensiveStance.amount() + ChampChar.characterStrings.TEXT[47];
        } else if (AbstractDungeon.player.stance instanceof UltimateStance) {
            return ChampChar.characterStrings.TEXT[19] + BerserkerStance.amount() + ChampChar.characterStrings.TEXT[58] + DefensiveStance.amount() + ChampChar.characterStrings.TEXT[59];
        } else if (AbstractDungeon.player instanceof ChampChar) {
            return ChampChar.characterStrings.TEXT[6];
        }
        return null;
    }

    public static String getStanceFinisher() {
        if (AbstractDungeon.player.stance instanceof BerserkerStance) {
            return ChampChar.characterStrings.TEXT[11];
        } else if (AbstractDungeon.player.stance instanceof DefensiveStance) {
            return ChampChar.characterStrings.TEXT[12] + DefensiveStance.finisherAmount() + ChampChar.characterStrings.TEXT[56];
        } else if (AbstractDungeon.player.stance instanceof UltimateStance) {
            return ChampChar.characterStrings.TEXT[20] + DefensiveStance.finisherAmount() + ChampChar.characterStrings.TEXT[60];
        } else if (AbstractDungeon.player instanceof ChampChar) {
            return ChampChar.characterStrings.TEXT[6];
        }
        return null;
    }

    public static void render(SpriteBatch sb) {
        if (!downfallMod.champDisableStanceHelper) {
            if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT)
                if (hitboxStance != null) {
                    if (getStanceName() != null) {
                        TipHelperChamp1.renderGenericTip(dx1.x, dx1.y,
                                ChampChar.characterStrings.TEXT[23],
                                getStanceName());

                        TipHelperChamp1.render(sb);
                        if (hitboxStance.hovered && AbstractDungeon.player.stance instanceof AbstractChampStance) {
                            if ((float) InputHelper.mX < 1400.0F * Settings.scale) {
                                TipHelper.renderGenericTip(
                                        (float) InputHelper.mX + 60.0F * Settings.scale, (float) InputHelper.mY - 50.0F * Settings.scale,
                                        BaseMod.getKeywordTitle(((AbstractChampStance) AbstractDungeon.player.stance).getKeywordString()),
                                        BaseMod.getKeywordDescription(((AbstractChampStance) AbstractDungeon.player.stance).getKeywordString()));
                            } else {
                                TipHelper.renderGenericTip((float) InputHelper.mX - 350.0F * Settings.scale, (float) InputHelper.mY - 50.0F * Settings.scale,
                                        BaseMod.getKeywordTitle(((AbstractChampStance) AbstractDungeon.player.stance).getKeywordString()),
                                        BaseMod.getKeywordDescription(((AbstractChampStance) AbstractDungeon.player.stance).getKeywordString()));
                            }
                        }

                        TipHelperChamp2.renderGenericTip(dx2.x, dx2.y,
                                ChampChar.characterStrings.TEXT[8],
                                getStanceTechnique());

                        TipHelperChamp2.render(sb);
                        if (hitboxTechnique.hovered && AbstractDungeon.player.stance instanceof AbstractChampStance) {
                            String t = "ERROR";
                            String d = "ERROR2";
                            if (AbstractDungeon.player.stance instanceof DefensiveStance) {
                                t = BaseMod.getKeywordTitle("champ:counter");
                                d = BaseMod.getKeywordDescription("champ:counter");
                            } else if (AbstractDungeon.player.stance instanceof BerserkerStance) {
                                t = BaseMod.getKeywordTitle("champ:fatigue");
                                d = BaseMod.getKeywordDescription("champ:fatigue");
                            }
                            if (!t.equals("ERROR")) {
                                if ((float) InputHelper.mX < 1400.0F * Settings.scale) {
                                    TipHelper.renderGenericTip(
                                            (float) InputHelper.mX + 60.0F * Settings.scale, (float) InputHelper.mY - 50.0F * Settings.scale,
                                            t,
                                            d);
                                } else {
                                    TipHelper.renderGenericTip((float) InputHelper.mX - 350.0F * Settings.scale, (float) InputHelper.mY - 50.0F * Settings.scale,
                                            t, d);
                                }
                            }
                        }

                        TipHelperChamp3.renderGenericTip(dx3.x, dx3.y,
                                ChampChar.characterStrings.TEXT[9],
                                getStanceFinisher());

                        TipHelperChamp3.render(sb);
                        if (hitboxFinisher.hovered && AbstractDungeon.player.stance instanceof AbstractChampStance) {
                            String t = "ERROR";
                            String d = "ERROR2";
                            if (AbstractDungeon.player.stance instanceof BerserkerStance) {
                                t = BaseMod.getKeywordTitle("champ:fatigue");
                                d = BaseMod.getKeywordDescription("champ:fatigue");
                            }
                            if (!t.equals("ERROR")) {
                                if ((float) InputHelper.mX < 1400.0F * Settings.scale) {
                                    TipHelper.renderGenericTip(
                                            (float) InputHelper.mX + 60.0F * Settings.scale, (float) InputHelper.mY - 50.0F * Settings.scale,
                                            t,
                                            d);
                                } else {
                                    TipHelper.renderGenericTip((float) InputHelper.mX - 350.0F * Settings.scale, (float) InputHelper.mY - 50.0F * Settings.scale,
                                            t, d);
                                }
                            }
                        }

                        // Icons in front of the thing are here.
                        renderHelper(sb, SymbolDescriptionPatch.ICONCROWN, Settings.scale * 310F, Settings.scale * 810F);
                        renderHelper(sb, SymbolDescriptionPatch.ICONFIST, Settings.scale * 310F, Settings.scale * 710F);
                    }
                }
        }
    }

    private static void renderHelper(SpriteBatch sb, TextureAtlas.AtlasRegion img, float drawX, float drawY) {
        sb.draw(img, drawX + img.offsetX - (float) img.originalWidth / 2.0F, drawY + img.offsetY - (float) img.originalHeight / 2.0F, (float) img.originalWidth / 2.0F - img.offsetX, (float) img.originalHeight / 2.0F - img.offsetY, (float) img.packedWidth, (float) img.packedHeight, Settings.scale, Settings.scale, 0F);
    }
}

