package gremlin.patches.relicpatches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.ui.buttons.SingingBowlButton;
import com.megacrit.cardcrawl.ui.buttons.SkipCardButton;
import gremlin.characters.GremlinCharacter;

@SpirePatch(
        clz= SingingBowlButton.class,
        method="render"
)
public class SingingBowlUIPatch {
    private static final RelicStrings strings = CardCrawlGame.languagePack.getRelicStrings("Gremlin:PatchFixes");

    public static SpireReturn<String> Prefix(SingingBowlButton __instance, SpriteBatch sb){
        if (AbstractDungeon.player instanceof GremlinCharacter) {
            boolean isHidden = ReflectionHacks.getPrivate(__instance, SingingBowlButton.class, "isHidden");
            if (!isHidden) {
                float current_x = ReflectionHacks.getPrivate(__instance, SingingBowlButton.class, "current_x");
                Color textColor = ReflectionHacks.getPrivate(__instance, SingingBowlButton.class, "textColor");
                ReflectionHacks.privateMethod(SingingBowlButton.class, "renderButton", SpriteBatch.class).invoke(__instance, sb);
                FontHelper.renderFontCentered(sb, FontHelper.buttonLabelFont, strings.DESCRIPTIONS[4], current_x, SkipCardButton.TAKE_Y, textColor);
                return SpireReturn.Return(null);
            }
        }
        return SpireReturn.Continue();
    }
}