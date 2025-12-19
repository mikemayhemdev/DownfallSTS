package downfall.patches;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import downfall.downfallMod;
import gremlin.GremlinMod;
import slimebound.SlimeboundMod;
import sneckomod.SneckoMod;
import theHexaghost.HexaMod;

import java.util.ArrayList;

import static reskinContent.patches.CharacterSelectScreenPatches.allTextInfoX;

public class CharSelectCustomModePatches {
    public static final Hitbox challengeDownHitbox = new Hitbox(40.0f * Settings.scale * (0.01f + (1.0f - 0.019f)), 40.0f * Settings.scale);
    public static final Hitbox showCharModesHitbox = new Hitbox(50.0f * Settings.scale * (0.01f + (1.0f - 0.019f)), 50.0f * Settings.scale);

    public static final ArrayList<PowerTip> challengeTips = new ArrayList<>();
    public static final UIStrings uiStringsSneckoMode = CardCrawlGame.languagePack.getUIString("sneckomod:ChallengeMode");
    public static final UIStrings uiStringsHexaghostSealMode = CardCrawlGame.languagePack.getUIString("hexamod:SealMode");
    public static final UIStrings uiStringsGrems = CardCrawlGame.languagePack.getUIString("Gremlin:ArtMode");

    @SpirePatch(clz = CharacterOption.class, method = "renderRelics")
    public static class RenderBtn {
        public static void Postfix(CharacterOption obj, SpriteBatch sb) {

            if (isOnAnyOverride(obj)) {
                showCharModesHitbox.move(200.0f * Settings.scale, Settings.HEIGHT / 2.0f - 190.0f * Settings.scale);

                sb.draw(ImageMaster.SETTINGS_ICON, showCharModesHitbox.cX - 26F + allTextInfoX, showCharModesHitbox.cY - 24.0f * Settings.scale, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0F, 0, 0, 64, 64, false, false);
                if (showCharModesHitbox.hovered) {
                    sb.setBlendFunction(770, 1);
                    sb.setColor(new Color(1.0F, 1.0F, 1.0F, 0.25F));
                    sb.draw(ImageMaster.SETTINGS_ICON, showCharModesHitbox.cX - 26F + allTextInfoX, showCharModesHitbox.cY - 24.0f * Settings.scale, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0F, 0, 0, 64, 64, false, false);
                    sb.setBlendFunction(770, 771);
                }
                showCharModesHitbox.render(sb);
            }

            if (downfallMod.ShowCharModes) {
                if (isOnHexaghost(obj)) {
                    challengeDownHitbox.move(260.0f * Settings.scale, Settings.HEIGHT / 2.0f - 190.0f * Settings.scale);
                    challengeDownHitbox.render(sb);

                    sb.setColor(Color.WHITE);
                    sb.draw(ImageMaster.CHECKBOX, challengeDownHitbox.cX - 26F  + allTextInfoX, challengeDownHitbox.cY - 32.0f, 32.0f, 32.0f, 64.0f, 64.0f, Settings.scale * (0.01f + (1.0f - 0.019f)), Settings.scale * (0.01f + (1.0f - 0.019f)), 0.0f, 0, 0, 64, 64, false, false);
                    if (downfallMod.SixSealsQuest) {
                        sb.draw(ImageMaster.TICK, challengeDownHitbox.cX - 26F  + allTextInfoX, challengeDownHitbox.cY - 32.0f, 32.0f, 32.0f, 64.0f, 64.0f, Settings.scale * (0.01f + (1.0f - 0.019f)), Settings.scale * (0.01f + (1.0f - 0.019f)), 0.0f, 0, 0, 64, 64, false, false);
                    }
                    FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, uiStringsHexaghostSealMode.TEXT[0], challengeDownHitbox.cX + 55f * Settings.scale + allTextInfoX, challengeDownHitbox.cY, Settings.BLUE_TEXT_COLOR);
                }
                else if (isOnSnecko(obj)) {
                    challengeDownHitbox.move(260.0f * Settings.scale, Settings.HEIGHT / 2.0f - 190.0f * Settings.scale);
                    challengeDownHitbox.render(sb);

                    sb.setColor(Color.WHITE);
                    sb.draw(ImageMaster.CHECKBOX, challengeDownHitbox.cX - 26F  + allTextInfoX, challengeDownHitbox.cY - 32.0f, 32.0f, 32.0f, 64.0f, 64.0f, Settings.scale * (0.01f + (1.0f - 0.019f)), Settings.scale * (0.01f + (1.0f - 0.019f)), 0.0f, 0, 0, 64, 64, false, false);
                    if (SneckoMod.pureSneckoMode) {
                        sb.draw(ImageMaster.TICK, challengeDownHitbox.cX - 26F  + allTextInfoX, challengeDownHitbox.cY - 32.0f, 32.0f, 32.0f, 64.0f, 64.0f, Settings.scale * (0.01f + (1.0f - 0.019f)), Settings.scale * (0.01f + (1.0f - 0.019f)), 0.0f, 0, 0, 64, 64, false, false);
                    }
                    FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, uiStringsSneckoMode.TEXT[0], challengeDownHitbox.cX + 55f * Settings.scale + allTextInfoX, challengeDownHitbox.cY, Settings.BLUE_TEXT_COLOR);
                }
                else if (isOnGrems(obj)) {
                    challengeDownHitbox.move(260.0f * Settings.scale, Settings.HEIGHT / 2.0f - 190.0f * Settings.scale);
                    challengeDownHitbox.render(sb);

                    sb.setColor(Color.WHITE);
                    sb.draw(ImageMaster.CHECKBOX, challengeDownHitbox.cX - 26F  + allTextInfoX, challengeDownHitbox.cY - 32.0f, 32.0f, 32.0f, 64.0f, 64.0f, Settings.scale * (0.01f + (1.0f - 0.019f)), Settings.scale * (0.01f + (1.0f - 0.019f)), 0.0f, 0, 0, 64, 64, false, false);
                    if (GremlinMod.gremlinArtMode) {
                        sb.draw(ImageMaster.TICK, challengeDownHitbox.cX - 26F  + allTextInfoX, challengeDownHitbox.cY - 32.0f, 32.0f, 32.0f, 64.0f, 64.0f, Settings.scale * (0.01f + (1.0f - 0.019f)), Settings.scale * (0.01f + (1.0f - 0.019f)), 0.0f, 0, 0, 64, 64, false, false);
                    }
                    FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, uiStringsGrems.TEXT[0], challengeDownHitbox.cX + 55f * Settings.scale + allTextInfoX, challengeDownHitbox.cY, Settings.BLUE_TEXT_COLOR);
                }
            }
        }
    }

    private static boolean isOnHexaghost(CharacterOption obj){
        if ( (obj.name.toLowerCase().contains("hexaghost") || obj.name.contains("hexaplasme") || obj.name.contains("ヘクサゴースト") || obj.name.contains("육각령") || obj.name.contains("Гексадух") || obj.name.contains("Hexafantasma") || obj.name.contains("六火亡魂")) && obj.selected) {
            return true;
        }
        return false;
    }

    private static boolean isOnSnecko(CharacterOption obj){
        if ( (obj.name.toLowerCase().contains("snecko") || obj.name.contains("蛇") || obj.name.contains("스네코") || obj.name.contains("Змекко") || obj.name.contains("Geckobra") ) && obj.selected) {
            return true;
        }
        return false;
    }

    private static boolean isOnGrems(CharacterOption obj){
        if ( (obj.name.toLowerCase().contains("gremlin") || obj.name.contains("地精") || obj.name.contains("군단") || obj.name.contains("Гремлины") || obj.name.contains("Les Diablotins") ) && obj.selected) {
            return true;
        }
        return false;
    }

    private static boolean isOnAnyOverride(CharacterOption obj){
        return isOnSnecko(obj) || isOnHexaghost(obj) || isOnGrems(obj);
    }

    @SpirePatch(clz = CharacterOption.class, method = "updateHitbox")
    public static class UpdateHitbox {
        public static void Postfix(CharacterOption obj) {

            if (isOnAnyOverride(obj)) {
                showCharModesHitbox.update();
                if (showCharModesHitbox.hovered) {
                    if (InputHelper.justClickedLeft) {
                        CardCrawlGame.sound.playA("UI_CLICK_1", -0.4f);
                        showCharModesHitbox.clickStarted = true;
                    }
                    if (showCharModesHitbox.clicked) {
                        downfallMod.ShowCharModes = !downfallMod.ShowCharModes;
                        showCharModesHitbox.clicked = false;
                        downfallMod.saveData();
                    }
                }
            }


            if (downfallMod.ShowCharModes) {
                if (isOnHexaghost(obj)) {
                    challengeDownHitbox.update();
                    if (challengeDownHitbox.hovered) {
                        if (challengeTips.isEmpty()) {
                            challengeTips.add(new PowerTip(uiStringsHexaghostSealMode.TEXT[0], uiStringsHexaghostSealMode.TEXT[1]));
                        }
                        if (InputHelper.mX < 1400.0f * Settings.scale) {
                            TipHelper.queuePowerTips(InputHelper.mX + 60.0f * Settings.scale, InputHelper.mY - 50.0f * Settings.scale, challengeTips);
                        } else {
                            TipHelper.queuePowerTips(InputHelper.mX - 350.0f * Settings.scale, InputHelper.mY - 50.0f * Settings.scale, challengeTips);
                        }

                        if (InputHelper.justClickedLeft) {
                            CardCrawlGame.sound.playA("UI_CLICK_1", -0.4f);
                            challengeDownHitbox.clickStarted = true;
                        }
                        if (challengeDownHitbox.clicked) {
                            downfallMod.SixSealsQuest = !downfallMod.SixSealsQuest;
                            challengeDownHitbox.clicked = false;
                            downfallMod.saveData();
                        }
                    }
                }
                else if (isOnSnecko(obj)) {
                    challengeDownHitbox.update();
                    if (challengeDownHitbox.hovered) {
                        if (challengeTips.isEmpty()) {
                            challengeTips.add(new PowerTip(uiStringsSneckoMode.TEXT[0], uiStringsSneckoMode.TEXT[1]));
                        }
                        if (InputHelper.mX < 1400.0f * Settings.scale) {
                            TipHelper.queuePowerTips(InputHelper.mX + 60.0f * Settings.scale, InputHelper.mY - 50.0f * Settings.scale, challengeTips);
                        } else {
                            TipHelper.queuePowerTips(InputHelper.mX - 350.0f * Settings.scale, InputHelper.mY - 50.0f * Settings.scale, challengeTips);
                        }

                        if (InputHelper.justClickedLeft) {
                            CardCrawlGame.sound.playA("UI_CLICK_1", -0.4f);
                            challengeDownHitbox.clickStarted = true;
                        }
                        if (challengeDownHitbox.clicked) {
                            SneckoMod.pureSneckoMode = !SneckoMod.pureSneckoMode;
                            challengeDownHitbox.clicked = false;
                            downfallMod.saveData();
                        }
                    }
                } else if (isOnGrems(obj)) {
                    challengeDownHitbox.update();
                    if (challengeDownHitbox.hovered) {
                        if (challengeTips.isEmpty()) {
                            challengeTips.add(new PowerTip(uiStringsGrems.TEXT[0], uiStringsGrems.TEXT[1]));
                        }
                        if (InputHelper.mX < 1400.0f * Settings.scale) {
                            TipHelper.queuePowerTips(InputHelper.mX + 60.0f * Settings.scale, InputHelper.mY - 50.0f * Settings.scale, challengeTips);
                        } else {
                            TipHelper.queuePowerTips(InputHelper.mX - 350.0f * Settings.scale, InputHelper.mY - 50.0f * Settings.scale, challengeTips);
                        }

                        if (InputHelper.justClickedLeft) {
                            CardCrawlGame.sound.playA("UI_CLICK_1", -0.4f);
                            challengeDownHitbox.clickStarted = true;
                        }
                        if (challengeDownHitbox.clicked) {
                            GremlinMod.gremlinArtMode = !GremlinMod.gremlinArtMode;
                            challengeDownHitbox.clicked = false;
                            downfallMod.saveData();
                        }
                    }
                }
            }

        }


    }
}
