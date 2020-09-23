package champ;

import champ.stances.BerserkerStance;
import champ.stances.DefensiveStance;
import champ.stances.GladiatorStance;
import champ.stances.UltimateStance;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class StanceHelper {

    public static float globalX = 25F * Settings.scale;
    public static float globalY = 900F * Settings.scale;

    private static Hitbox hitboxStance;
    private static Hitbox hitboxTechnique;
    private static Hitbox hitboxFinisher;

    public static void init() {
        hitboxStance = new Hitbox(globalX, globalY, 80 * Settings.scale, 80 * Settings.scale);
        hitboxTechnique = new Hitbox(globalX, globalY - (100F * Settings.scale), 80 * Settings.scale, 80 * Settings.scale);
        hitboxFinisher = new Hitbox(globalX, globalY - (200F * Settings.scale), 80 * Settings.scale, 80 * Settings.scale);
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
        } else if (AbstractDungeon.player.stance instanceof GladiatorStance) {
            return ChampChar.characterStrings.TEXT[26];
        } else if (AbstractDungeon.player.stance instanceof UltimateStance) {
            return ChampChar.characterStrings.TEXT[7];
        } else if (AbstractDungeon.player instanceof ChampChar) {
            return ChampChar.characterStrings.TEXT[6];
        }
        return null;
    }


    public static String getStanceTechnique() {
        if (AbstractDungeon.player.stance instanceof BerserkerStance) {
            return ChampChar.characterStrings.TEXT[10];
        } else if (AbstractDungeon.player.stance instanceof DefensiveStance) {
            return ChampChar.characterStrings.TEXT[12];
        } else if (AbstractDungeon.player.stance instanceof GladiatorStance) {
            return ChampChar.characterStrings.TEXT[14];
        } else if (AbstractDungeon.player.stance instanceof UltimateStance) {
            return ChampChar.characterStrings.TEXT[19];
        } else if (AbstractDungeon.player instanceof ChampChar) {
            return ChampChar.characterStrings.TEXT[6];
        }
        return null;
    }

    public static String getStanceFinisher() {
        if (AbstractDungeon.player.stance instanceof BerserkerStance) {
            return ChampChar.characterStrings.TEXT[11];
        } else if (AbstractDungeon.player.stance instanceof DefensiveStance) {
            return ChampChar.characterStrings.TEXT[13];
        } else if (AbstractDungeon.player.stance instanceof GladiatorStance) {
            return ChampChar.characterStrings.TEXT[15];
        } else if (AbstractDungeon.player.stance instanceof UltimateStance) {
            return ChampChar.characterStrings.TEXT[20];
        } else if (AbstractDungeon.player instanceof ChampChar) {
            return ChampChar.characterStrings.TEXT[6];
        }
        return null;
    }

    public static void render(SpriteBatch sb) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT)
            if (hitboxStance != null) {
                if (getStanceName() != null) {
                    TipHelperChamp1.renderGenericTip(hitboxStance.x, hitboxStance.y,
                            ChampChar.characterStrings.TEXT[23],
                            getStanceName());

                    TipHelperChamp1.render(sb);

                    TipHelperChamp2.renderGenericTip(hitboxTechnique.x, hitboxTechnique.y,
                            ChampChar.characterStrings.TEXT[8],
                            getStanceTechnique());

                    TipHelperChamp2.render(sb);

                    TipHelperChamp3.renderGenericTip(hitboxFinisher.x, hitboxFinisher.y,
                            ChampChar.characterStrings.TEXT[9],
                            getStanceFinisher());

                    TipHelperChamp3.render(sb);
                }
            }
    }
}
