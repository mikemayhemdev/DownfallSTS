package downfall.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.daily.DailyScreen;
import com.megacrit.cardcrawl.daily.TimeHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.integrations.DistributorFactory;
import com.megacrit.cardcrawl.screens.mainMenu.MenuCancelButton;
import downfall.downfallMod;

public class DailyModeEvilPatch {

    public static boolean todaysRunIsEvil;

    public static String campaignText = CardCrawlGame.languagePack.getUIString(downfallMod.makeID("EvilMenuPanel")).TEXT[3];
    public static String downfallText = CardCrawlGame.languagePack.getUIString(downfallMod.makeID("EvilMenuPanel")).TEXT[0];
    public static String standardText = CardCrawlGame.languagePack.getUIString("MenuPanels").TEXT[0];

    private static float header_x = 186.0F * Settings.scale;
    private static float char_x = 304.0F * Settings.scale;

    private static float center_mod_offset_x = 500.0F * Settings.scale;

    private static float mode_offset_x = 400.0F * Settings.scale;

    @SpirePatch(clz = DailyScreen.class, method = "determineLoadout")
    public static class Init {
        @SpirePostfixPatch
        public static void Postfix(DailyScreen __result) {
            if (Settings.specialSeed % 2 != 0) {
                todaysRunIsEvil = true;
            }

            if (!DistributorFactory.isLeaderboardEnabled()) {
                header_x += center_mod_offset_x;
                char_x += center_mod_offset_x;
            }
            //Uncomment to re-randomize dailies whenever you enter the screen.
           // long todaySeed = MathUtils.random(0, TimeHelper.daySince1970());
           // ModHelper.setTodaysMods(todaySeed, __result.todaysChar.chosenClass);// 315
        }
    }

    @SpirePatch(clz = DailyScreen.class, method = "update")
    public static class Proceed {
        @SpirePostfixPatch
        public static void Prefix(DailyScreen __result) {
            MenuCancelButton cancelButton = (MenuCancelButton) ReflectionHacks.getPrivate(__result, DailyScreen.class, "cancelButton");
            if (cancelButton.hb.clicked) {
                EvilModeCharacterSelect.evilMode = todaysRunIsEvil;
            }

        }

    }

    @SpirePatch(clz = DailyScreen.class, method = "render", paramtypez = {SpriteBatch.class})
    public static class renderPatch {
        @SpirePostfixPatch
        public static void Postfix(DailyScreen __result, SpriteBatch sb) {
            if (TimeHelper.isTimeSet) {
                com.badlogic.gdx.graphics.Texture img = null;
                StringBuilder builder = new StringBuilder("#y");
                builder.append(campaignText);
                builder.append(" NL ");
                if (todaysRunIsEvil) {
                    builder.append(downfallText);
                    img = ImageMaster.loadImage(downfallMod.assetPath("/images/ui/dailyEvil.png"));
                } else {
                    builder.append(standardText);
                    img = ImageMaster.loadImage(downfallMod.assetPath("/images/ui/dailyStandard.png"));
                }


                sb.setColor(Color.WHITE);
                if (img != null) {
                    sb.draw(img, header_x + mode_offset_x * Settings.scale, Settings.HEIGHT / 2.0F + 160.0F * Settings.scale, 128.0F * Settings.scale, 128.0F * Settings.scale);
                }

                FontHelper.renderSmartText(sb, FontHelper.charDescFont, builder

                        .toString(), char_x + mode_offset_x * Settings.scale, Settings.HEIGHT / 2.0F + 250F * Settings.scale, 9999.0F, 30.0F * Settings.scale, Settings.CREAM_COLOR);
            }
        }
    }

}


