//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package slimebound.patches;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import slimebound.powers.DuplicatedFormNoHealPower;

@SpirePatch(
        clz = AbstractCreature.class,
        method = "renderHealthBg",
        paramtypez = {
                SpriteBatch.class,
                float.class,
                float.class
        })

public class HealthBarRenderBackPatch {

    @SpirePostfixPatch
    public static void Postfix(AbstractCreature __instance, SpriteBatch sb, float x, float y) {

        if (__instance.hasPower(DuplicatedFormNoHealPower.POWER_ID)) {

            sb.setColor(new Color(0.0F, 0.75F, 0.0F, 1F));
            int amt = __instance.getPower(DuplicatedFormNoHealPower.POWER_ID).amount;

            float HEALTH_BAR_HEIGHT = 20.0F * Settings.scale;
            float HEALTH_BAR_OFFSET_Y = -28.0F * Settings.scale;

            float w = (1.0F - ((__instance.maxHealth - amt) / (float) __instance.maxHealth)) * __instance.hb.width;
            x = __instance.hb.cX + __instance.hb.width / 2F - w;
            ////SlimeboundMod.logger.info("Health render: " + amt + " " + HEALTH_BAR_HEIGHT + " " + x + " " + (x + w) + " " + w);



                        /*
                        if (__instance.currentHealth > 0) {
                            sb.draw(ImageMaster.HEALTH_BAR_L, x - HEALTH_BAR_HEIGHT, y + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);
                        }
                        */

            sb.draw(ImageMaster.loadImage("slimeboundResources/SlimeboundImages/ui/body7.png"), x, y + HEALTH_BAR_OFFSET_Y, w, HEALTH_BAR_HEIGHT);
            sb.draw(ImageMaster.HEALTH_BAR_R, x + w, y + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);


        }


    }
}



