package champ.patches;

import champ.powers.ResolvePower;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.lib.Matcher.MethodCallMatcher;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import javassist.CtBehavior;

public class RenderBonusHealthPatch {

    public RenderBonusHealthPatch() {
    }// 15

    @SpirePatch(
            clz = AbstractCreature.class,
            method = "renderHealth"
    )
    public static class RenderPowerHealthBar {
        public RenderPowerHealthBar() {
        }// 24

        @SpireInsertPatch(
                locator = RenderBonusHealthPatch.RenderPowerHealthBar.Locator.class,
                localvars = {"x", "y", "targetHealthBarWidth", "HEALTH_BAR_HEIGHT", "HEALTH_BAR_OFFSET_Y"}
        )
        public static void Insert(AbstractCreature __instance, SpriteBatch sb, float x, float y, float targetHealthBarWidth, float HEALTH_BAR_HEIGHT, float HEALTH_BAR_OFFSET_Y) {
            for (AbstractPower power : __instance.powers) {
                if (power instanceof ResolvePower) {// 44
                    sb.setColor(Color.WHITE.cpy());// 45
                    int amt = Math.min(AbstractDungeon.player.maxHealth, power.amount);
                    float w = amt;
                    sb.draw(ImageMaster.HEALTH_BAR_L, x - HEALTH_BAR_HEIGHT, y + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);// 56
                    sb.draw(ImageMaster.HEALTH_BAR_B, x, y + HEALTH_BAR_OFFSET_Y, w, HEALTH_BAR_HEIGHT);// 58
                    sb.draw(ImageMaster.HEALTH_BAR_R, x + targetHealthBarWidth + w, y + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);// 59
                }
            }

        }// 70

        private static class Locator extends SpireInsertLocator {
            private Locator() {
            }// 72

            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new MethodCallMatcher(AbstractCreature.class, "renderRedHealthBar");// 77
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);// 78
            }
        }
    }
}
