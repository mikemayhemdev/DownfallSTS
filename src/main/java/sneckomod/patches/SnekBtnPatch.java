package sneckomod.patches;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import sneckomod.SneckoMod;

import java.util.ArrayList;

public class SnekBtnPatch {
    public static final Hitbox challengeDownHitbox = new Hitbox(40.0f * Settings.scale * (0.01f + (1.0f - 0.019f)), 40.0f * Settings.scale);

    public static final ArrayList<PowerTip> challengeTips = new ArrayList<>();
    public static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("sneckomod:ChallengeMode");

    @SpirePatch(clz = CharacterOption.class, method = "renderRelics")
    public static class RenderBtn {
        public static void Postfix(CharacterOption obj, SpriteBatch sb) {
            if (obj.name.toLowerCase().contains("snecko") && obj.selected) {
                challengeDownHitbox.move(190.0f * Settings.scale, Settings.HEIGHT / 2.0f - 190.0f * Settings.scale);
                challengeDownHitbox.render(sb);

                sb.setColor(Color.WHITE);
                sb.draw(ImageMaster.CHECKBOX, challengeDownHitbox.cX - 32.0f, challengeDownHitbox.cY - 32.0f, 32.0f, 32.0f, 64.0f, 64.0f, Settings.scale * (0.01f + (1.0f - 0.019f)), Settings.scale * (0.01f + (1.0f - 0.019f)), 0.0f, 0, 0, 64, 64, false, false);
                if (SneckoMod.pureSneckoMode) {
                    sb.draw(ImageMaster.TICK, challengeDownHitbox.cX - 32.0f, challengeDownHitbox.cY - 32.0f, 32.0f, 32.0f, 64.0f, 64.0f, Settings.scale * (0.01f + (1.0f - 0.019f)), Settings.scale * (0.01f + (1.0f - 0.019f)), 0.0f, 0, 0, 64, 64, false, false);
                }
                FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, uiStrings.TEXT[0], challengeDownHitbox.cX + 25f * Settings.scale, challengeDownHitbox.cY, Settings.BLUE_TEXT_COLOR);
            }
        }
    }

    @SpirePatch(clz = CharacterOption.class, method = "updateHitbox")
    public static class UpdateHitbox {
        public static void Postfix(CharacterOption obj) {
            if (obj.name.toLowerCase().contains("snecko") && obj.selected) {
                challengeDownHitbox.update();
                if (challengeDownHitbox.hovered) {
                    if (challengeTips.isEmpty()) {
                        challengeTips.add(new PowerTip( uiStrings.TEXT[0],  uiStrings.TEXT[1]));
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
                    }
                }
            }
        }
    }
}