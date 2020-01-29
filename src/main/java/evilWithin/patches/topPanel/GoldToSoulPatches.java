package evilWithin.patches.topPanel;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.ui.panels.TopPanel;
import evilWithin.EvilWithinMod;
import evilWithin.patches.EvilModeCharacterSelect;
import evilWithin.util.ReplaceData;
import evilWithin.util.TextureLoader;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

import java.util.Map;

public class GoldToSoulPatches {
    private static Texture GOLD_ICON;
    private static Texture GOLD_UI_ICON;
    private static boolean triggered = false;
    private static final String[] GOLD_TEXT = {TopPanel.LABEL[4], TopPanel.MSG[4]};
    public static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(EvilWithinMod.makeID("SoulToGoldChanges"));


    public static void changeGoldToSouls(boolean revert) {
        if (!triggered) {
            triggered = true;
            GOLD_ICON = ImageMaster.TP_GOLD;
            GOLD_UI_ICON = ImageMaster.UI_GOLD;
        }

        if (!revert) {
            ImageMaster.TP_GOLD = TextureLoader.getTexture(EvilWithinMod.assetPath("images/ui/Souls_Icon.png"));
            ImageMaster.UI_GOLD = TextureLoader.getTexture(EvilWithinMod.assetPath("images/ui/Souls_UI_Icon.png"));
            TopPanel.LABEL[4] = uiStrings.TEXT[0];
            TopPanel.MSG[4] = uiStrings.TEXT[1];
            postLoadLocalizationStrings(true);
        } else {
            ImageMaster.TP_GOLD = GOLD_ICON;
            ImageMaster.UI_GOLD = GOLD_UI_ICON;
            TopPanel.LABEL[4] = GOLD_TEXT[0];
            TopPanel.MSG[4] = GOLD_TEXT[1];
            postLoadLocalizationStrings(false);
        }
    }

    @SpirePatch(clz = TopPanel.class, method = "renderGold")
    public static class BlueSoulsText {
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

    /*@SpirePatch(clz = CardCrawlGame.class, method = "create")
    public static class PostLoadLocalizationPatch {
        @SpireInsertPatch(locator = Locator.class, localvars = {"languagePack"})
        public static void PostLocalization(CardCrawlGame __instance, LocalizedStrings languagePack) {
            for (Settings.GameLanguage lang : EvilWithinMod.SupportedLanguages) {
                if (lang.equals(Settings.language)) {
                    postLoadLocalizationStrings();
                    return;
                }
            }
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                //Matcher finalMatcher = new Matcher.ConstructorCallMatcher(SingleCardViewPopup.class);
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(Settings.class, "IS_FULLSCREEN");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }*/

    public static void postLoadLocalizationStrings(boolean soulRename) {
        try {
            Map<String, CardStrings> cardStrings = (Map<String, CardStrings>) ReflectionHacks.getPrivateStatic(LocalizedStrings.class, "cards");
            if (cardStrings != null) {
                for (CardStrings cardString : cardStrings.values()) {
                    if (cardString.DESCRIPTION != null)
                        cardString.DESCRIPTION = filterString(cardString.DESCRIPTION);

                    if (cardString.UPGRADE_DESCRIPTION != null)
                        cardString.UPGRADE_DESCRIPTION = filterString(cardString.UPGRADE_DESCRIPTION);

                    if (cardString.EXTENDED_DESCRIPTION != null)
                        for (int i = 0; i < cardString.EXTENDED_DESCRIPTION.length; i++)
                            cardString.EXTENDED_DESCRIPTION[i] = filterString(cardString.EXTENDED_DESCRIPTION[i]);
                }

                ReflectionHacks.setPrivateStaticFinal(LocalizedStrings.class, "cards", cardStrings);
            }

            Map<String, EventStrings> eventStrings = (Map<String, EventStrings>) ReflectionHacks.getPrivateStatic(LocalizedStrings.class, "events");
            if (eventStrings != null) {
                for (EventStrings eventString : eventStrings.values()) {
                    if (eventString.DESCRIPTIONS != null)
                        eventString.DESCRIPTIONS = filterString(eventString.DESCRIPTIONS); //This might be too much, will catch the color gold being mentioned as well

                    if (eventString.OPTIONS != null)
                        eventString.OPTIONS = filterString(eventString.OPTIONS);
                }
                ReflectionHacks.setPrivateStaticFinal(LocalizedStrings.class, "events", eventStrings);
            }

            Map<String, RelicStrings> relicStrings = (Map<String, RelicStrings>) ReflectionHacks.getPrivateStatic(LocalizedStrings.class, "relics");
            if (relicStrings != null) {
                for (RelicStrings relicString : relicStrings.values()) {
                    if (relicString.DESCRIPTIONS != null)
                        relicString.DESCRIPTIONS = filterString(relicString.DESCRIPTIONS);
                }
                ReflectionHacks.setPrivateStaticFinal(LocalizedStrings.class, "relics", relicStrings);
            }

            Map<String, CharacterStrings> characterStrings = (Map<String, CharacterStrings>) ReflectionHacks.getPrivateStatic(LocalizedStrings.class, "characters");
            if (characterStrings != null) {
                for (CharacterStrings characterString : characterStrings.values()) {
                    if (characterString.TEXT != null)
                        characterString.TEXT = filterString(characterString.TEXT);

                    if (characterString.OPTIONS != null)
                        characterString.OPTIONS = filterString(characterString.OPTIONS);

                    if (characterString.NAMES != null)
                        characterString.NAMES = filterString(characterString.NAMES);
                }
                ReflectionHacks.setPrivateStaticFinal(LocalizedStrings.class, "characters", characterStrings);
            }

            Map<String, PowerStrings> powerStrings = (Map<String, PowerStrings>) ReflectionHacks.getPrivateStatic(LocalizedStrings.class, "powers");
            if (powerStrings != null) {
                for (PowerStrings powerString : powerStrings.values()) {
                    if (powerString.DESCRIPTIONS != null)
                        powerString.DESCRIPTIONS = filterString(powerString.DESCRIPTIONS);
                }
                ReflectionHacks.setPrivateStaticFinal(LocalizedStrings.class, "powers", powerStrings);
            }

            Map<String, UIStrings> uiStrings = (Map<String, UIStrings>) ReflectionHacks.getPrivateStatic(LocalizedStrings.class, "ui");
            if (uiStrings != null) {
                for (UIStrings uiString : uiStrings.values()) {
                    if (uiString.TEXT != null)
                        uiString.TEXT = filterString(uiString.TEXT);
                }
                ReflectionHacks.setPrivateStaticFinal(LocalizedStrings.class, "ui", uiStrings);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String[] filterString(String[] str) {
        for (int i = 0; i < str.length; i++) {
            str[i] = filterString(str[i]);
        }
        return str;
    }

    private static String filterString(String spireString) {
        String returnString = spireString;

        for (ReplaceData data : EvilWithinMod.wordReplacements) {
            for (String phrase : data.KEYS) {
                if (data.VALUE == null) {
                    data.VALUE = "";
                }
                String replacement = returnString.replaceAll(phrase, data.VALUE);
                returnString = replacement;
            }
        }

        return returnString;
    }
}
