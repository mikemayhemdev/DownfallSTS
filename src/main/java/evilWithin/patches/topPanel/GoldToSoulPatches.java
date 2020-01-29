package evilWithin.patches.topPanel;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.panels.TopPanel;
import evilWithin.EvilWithinMod;
import evilWithin.patches.EvilModeCharacterSelect;
import evilWithin.util.TextureLoader;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

public class GoldToSoulPatches {
    private static final Texture GOLD_ICON = ImageMaster.TP_GOLD;
    private static final Texture GOLD_UI_ICON = ImageMaster.UI_GOLD;
    private static final String[] GOLD_TEXT= {TopPanel.LABEL[4], TopPanel.MSG[4]};
    public static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(EvilWithinMod.makeID("SoulToGoldChanges"));


    public static void changeGoldToSouls(boolean revert) {
        if(!revert) {
            ImageMaster.TP_GOLD = TextureLoader.getTexture(EvilWithinMod.assetPath("images/ui/Souls_Icon.png"));
            ImageMaster.UI_GOLD = TextureLoader.getTexture(EvilWithinMod.assetPath("images/ui/Souls_UI_Icon.png"));
            TopPanel.LABEL[4] = uiStrings.TEXT[0];
            TopPanel.MSG[4] = uiStrings.TEXT[1];
        } else {
            ImageMaster.TP_GOLD = GOLD_ICON;
            ImageMaster.UI_GOLD = GOLD_UI_ICON;
            TopPanel.LABEL[4] = GOLD_TEXT[0];
            TopPanel.MSG[4] = GOLD_TEXT[1];
        }
    }

    @SpirePatch(clz = TopPanel.class, method = "renderGold")
    public static class ManaRender {
        public static boolean firstInstance = true;
        public static ExprEditor Instrument() {
            return new ExprEditor() {
                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getMethodName().equals("renderFontLeftTopAligned") && firstInstance) {
                        firstInstance = false;
                        m.replace("{" +
                                "if(" + EvilModeCharacterSelect.class.getName() + ".evilMode) {" +
                                "$proceed($1, $2, $3, $4, $5, " + Color.class.getName() + ".SKY);" +
                                "} else {" +
                                "$proceed($$);" +
                                "}" +
                                "}"
                        );
                    }
                }
            };
        }
    }

    //TODO: Replace all instances of "Gold" in the strings with "Souls" https://github.com/Alchyr/ShortenTheSpire/blob/master/src/main/java/ShortenTheSpire/ShortenTheSpireMod.java#L55
}
