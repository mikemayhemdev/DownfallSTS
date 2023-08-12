package collector.patches;

import basemod.ReflectionHacks;
import collector.CollectorChar;
import collector.patches.EssencePatches.TopPanelEssence;
import collector.util.EssenceSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;

@SpirePatch(clz=CharacterOption.class, method="renderInfo")
public class EssenceOnCharacterSelect {
    private static final String[] TEXT = CardCrawlGame.languagePack.getUIString("collector:CharacterOption").TEXT;
    private static final Color COLOR = new Color(0.55f, 1.00f, 0.26f, 1f);

    public static void Prefix(CharacterOption __instance, SpriteBatch sb) {
        if (__instance.c instanceof CollectorChar) {
            sb.draw(TopPanelEssence.ICON, getF(__instance, "infoX") + 390f * Settings.scale - 24f, getF(__instance, "infoY") + 95f * Settings.scale - 24f, 24f, 24f, 48f, 48f, Settings.scale, Settings.scale, 0f, 0, 0, 64, 64, false, false);
            FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, TEXT[0] + Integer.toString(EssenceSystem.STARTING_ESSENCE), getF(__instance, "infoX") + 420f * Settings.scale, getF(__instance, "infoY") + 102f * Settings.scale, 10000f, 10000f, COLOR);
        }
    }

    private static float getF(CharacterOption __instance, String fieldName) {
        return (float)ReflectionHacks.getPrivate(__instance, CharacterOption.class, fieldName);
    }
}