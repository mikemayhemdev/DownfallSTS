
/*
package slimebound.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.actions.defect.EvokeSpecificOrbAction;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import slimebound.powers.DuplicatedFormNoHealPower;

@SpirePatch(clz= AbstractCreature.class,method="renderHealthText",
        paramtypez = {SpriteBatch.class,
                float.class})
                
public class RenderHealthTextPatch {

    public static SpireReturn<Void> Prefix(AbstractCreature obj, SpriteBatch sb, float y) {
        if (obj instanceof AbstractPlayer && obj.hasPower(DuplicatedFormNoHealPower.POWER_ID)) {
            float healthBarWidth = (float) ReflectionHacks.getPrivate(obj, AbstractCreature.class, "targetHealthBarWidth");
            float healthHideTimer = (float) ReflectionHacks.getPrivate(obj, AbstractCreature.class, "healthHideTimer");
            Color textColor = (Color) ReflectionHacks.getPrivate(obj, AbstractCreature.class, "hbTextColor");
            UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("AbstractCreature");

            String[] TEXT = uiStrings.TEXT;


            if (healthBarWidth != 0.0F) {
                float tmp = textColor.a;
                Color var10000 = textColor;
                var10000.a *= healthHideTimer;
                int currentHealth = obj.currentHealth;
                int maxHealth = (obj.maxHealth - AbstractDungeon.player.getPower(DuplicatedFormNoHealPower.POWER_ID).amount);
                FontHelper.renderFontCentered(sb, FontHelper.healthInfoFont, currentHealth + "/" + maxHealth, obj.hb.cX, y + -28.0F * Settings.scale + 6.0F * Settings.scale + 5.0F * Settings.scale, textColor);
                textColor.a = tmp;
            } else {
                FontHelper.renderFontCentered(sb, FontHelper.healthInfoFont, TEXT[0], obj.hb.cX, y + -28.0F * Settings.scale + 6.0F * Settings.scale - 1.0F * Settings.scale, textColor);
            }
            return SpireReturn.Return(null);
        } else {

            return SpireReturn.Continue();
        }
    }
}
*/