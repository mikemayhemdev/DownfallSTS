package downfall.patches.ui.topPanel;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.HandOfGreed;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import com.megacrit.cardcrawl.ui.panels.TopPanel;
import downfall.downfallMod;
import downfall.patches.EvilModeCharacterSelect;
import downfall.util.ReplaceData;
import downfall.util.TextureLoader;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class GoldToSoulPatches {
    public static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(downfallMod.makeID("SoulToGoldChanges"));
    private static final String[] GOLD_TEXT = {TopPanel.LABEL[4], TopPanel.MSG[4]};
    public static Map<String, CardStrings[]> renameC = new HashMap<>();
    public static Map<String, EventStrings[]> renameE = new HashMap<>();
    public static Map<String, RelicStrings[]> renameR = new HashMap<>();
    public static Map<String, CharacterStrings[]> renameCh = new HashMap<>();
    public static Map<String, PowerStrings[]> renameP = new HashMap<>();
    public static Map<String, UIStrings[]> renameUI = new HashMap<>();

    //THE BELOW IS A MESS, YOU PROBABLY SHOULDN'T LOOK AT IT
    private static Texture GOLD_ICON;
    private static Texture GOLD_UI_ICON;
    private static boolean triggered = false;

    public static void changeGoldToSouls(boolean revert) {
        if (!triggered) {
            triggered = true;
            GOLD_ICON = ImageMaster.TP_GOLD;
            GOLD_UI_ICON = ImageMaster.UI_GOLD;
        }

        if (!revert) {
            ImageMaster.TP_GOLD = TextureLoader.getTexture(downfallMod.assetPath("images/ui/Souls_Icon.png"));
            ImageMaster.UI_GOLD = TextureLoader.getTexture(downfallMod.assetPath("images/ui/Souls_UI_Icon.png"));
            TopPanel.LABEL[4] = uiStrings.TEXT[0];
            TopPanel.MSG[4] = uiStrings.TEXT[1];
            replaceStrings(true);
        } else {
            ImageMaster.TP_GOLD = GOLD_ICON;
            ImageMaster.UI_GOLD = GOLD_UI_ICON;
            TopPanel.LABEL[4] = GOLD_TEXT[0];
            TopPanel.MSG[4] = GOLD_TEXT[1];
            replaceStrings(false);
        }
    }

    public static void postLoadLocalizationStrings() {
        try {
            Map<String, CardStrings> cardStrings = (Map<String, CardStrings>) ReflectionHacks.getPrivateStatic(LocalizedStrings.class, "cards");
            if (cardStrings != null) {
                CardStrings replacementString;
                for (Map.Entry<String, CardStrings> cardString : cardStrings.entrySet()) {
                    replacementString = new CardStrings();
                    replacementString.NAME = cardString.getValue().NAME;

                    if (cardString.getValue().DESCRIPTION != null) {
                        replacementString.DESCRIPTION = filterString(cardString.getValue().DESCRIPTION);
                    }

                    if (cardString.getValue().UPGRADE_DESCRIPTION != null) {
                        replacementString.UPGRADE_DESCRIPTION = filterString(cardString.getValue().UPGRADE_DESCRIPTION);
                    }

                    if (cardString.getValue().EXTENDED_DESCRIPTION != null) {
                        replacementString.EXTENDED_DESCRIPTION = filterString(cardString.getValue().EXTENDED_DESCRIPTION);
                    }
                    if ((replacementString.DESCRIPTION != null && !(replacementString.DESCRIPTION.equals(cardString.getValue().DESCRIPTION))) ||
                            (replacementString.EXTENDED_DESCRIPTION != null && !(Arrays.equals(replacementString.EXTENDED_DESCRIPTION, cardString.getValue().EXTENDED_DESCRIPTION))) ||
                            (replacementString.UPGRADE_DESCRIPTION != null && !(replacementString.UPGRADE_DESCRIPTION.equals(cardString.getValue().UPGRADE_DESCRIPTION)))) {
                        renameC.put(cardString.getKey(), new CardStrings[]{cardString.getValue(), replacementString});
                    }
                }
            }

            /*for(Map.Entry<String, CardStrings[]> e : renameC.entrySet()) {
                System.out.println(e.getKey() + ":");
                for(CardStrings s : e.getValue()) {
                    System.out.println("Desc: " + s.DESCRIPTION);
                }
                System.out.println("CurDesc: " + cardStrings.get(e.getKey()).DESCRIPTION);
            }*/

            Map<String, EventStrings> eventStrings = (Map<String, EventStrings>) ReflectionHacks.getPrivateStatic(LocalizedStrings.class, "events");
            if (eventStrings != null) {
                EventStrings replacementString;
                for (Map.Entry<String, EventStrings> eventString : eventStrings.entrySet()) {
                    replacementString = new EventStrings();
                    replacementString.NAME = eventString.getValue().NAME;

                    if (eventString.getValue().DESCRIPTIONS != null) {
                        replacementString.DESCRIPTIONS = filterString(eventString.getValue().DESCRIPTIONS);
                    }

                    if (eventString.getValue().OPTIONS != null) {
                        replacementString.OPTIONS = filterString(eventString.getValue().OPTIONS);
                    }

                    if ((replacementString.DESCRIPTIONS != null && !(Arrays.equals(replacementString.DESCRIPTIONS, eventString.getValue().DESCRIPTIONS))) ||
                            (replacementString.OPTIONS != null && !(Arrays.equals(replacementString.OPTIONS, eventString.getValue().OPTIONS)))) {
                        renameE.put(eventString.getKey(), new EventStrings[]{eventString.getValue(), replacementString});
                    }
                }
            }

            Map<String, RelicStrings> relicStrings = (Map<String, RelicStrings>) ReflectionHacks.getPrivateStatic(LocalizedStrings.class, "relics");
            if (relicStrings != null) {
                RelicStrings replacementString;
                for (Map.Entry<String, RelicStrings> relicString : relicStrings.entrySet()) {
                    replacementString = new RelicStrings();
                    replacementString.NAME = relicString.getValue().NAME;
                    replacementString.FLAVOR = relicString.getValue().FLAVOR;

                    if (relicString.getValue().DESCRIPTIONS != null) {
                        replacementString.DESCRIPTIONS = filterString(relicString.getValue().DESCRIPTIONS);
                    }

                    if ((replacementString.DESCRIPTIONS != null && !(Arrays.equals(replacementString.DESCRIPTIONS, relicString.getValue().DESCRIPTIONS)))) {
                        renameR.put(relicString.getKey(), new RelicStrings[]{relicString.getValue(), replacementString});
                    }
                }
            }

            Map<String, CharacterStrings> characterStrings = (Map<String, CharacterStrings>) ReflectionHacks.getPrivateStatic(LocalizedStrings.class, "characters");
            if (characterStrings != null) {
                CharacterStrings replacementString;
                for (Map.Entry<String, CharacterStrings> characterString : characterStrings.entrySet()) {
                    replacementString = new CharacterStrings();
                    replacementString.UNIQUE_REWARDS = characterString.getValue().UNIQUE_REWARDS;

                    if (characterString.getValue().NAMES != null) {
                        replacementString.NAMES = filterString(characterString.getValue().NAMES);
                    }

                    if (characterString.getValue().TEXT != null) {
                        replacementString.TEXT = filterString(characterString.getValue().TEXT);
                    }

                    if (characterString.getValue().OPTIONS != null) {
                        replacementString.OPTIONS = filterString(characterString.getValue().OPTIONS);
                    }

                    if ((replacementString.NAMES != null && !(Arrays.equals(replacementString.NAMES, characterString.getValue().NAMES))) ||
                            (replacementString.TEXT != null && !(Arrays.equals(replacementString.TEXT, characterString.getValue().TEXT))) ||
                            (replacementString.OPTIONS != null && !(Arrays.equals(replacementString.OPTIONS, characterString.getValue().OPTIONS)))) {
                        renameCh.put(characterString.getKey(), new CharacterStrings[]{characterString.getValue(), replacementString});
                    }
                }
            }

            Map<String, PowerStrings> powerStrings = (Map<String, PowerStrings>) ReflectionHacks.getPrivateStatic(LocalizedStrings.class, "powers");
            if (powerStrings != null) {
                PowerStrings replacementString;
                for (Map.Entry<String, PowerStrings> powerString : powerStrings.entrySet()) {
                    replacementString = new PowerStrings();
                    replacementString.NAME = powerString.getValue().NAME;

                    if (powerString.getValue().DESCRIPTIONS != null) {
                        replacementString.DESCRIPTIONS = filterString(powerString.getValue().DESCRIPTIONS);
                    }

                    if ((replacementString.DESCRIPTIONS != null && !(Arrays.equals(replacementString.DESCRIPTIONS, powerString.getValue().DESCRIPTIONS)))) {
                        renameP.put(powerString.getKey(), new PowerStrings[]{powerString.getValue(), replacementString});
                    }
                }
            }

            Map<String, UIStrings> uiStrings = (Map<String, UIStrings>) ReflectionHacks.getPrivateStatic(LocalizedStrings.class, "ui");
            if (uiStrings != null) {
                UIStrings replacementString;
                for (Map.Entry<String, UIStrings> uiString : uiStrings.entrySet()) {
                    replacementString = new UIStrings();
                    replacementString.EXTRA_TEXT = uiString.getValue().EXTRA_TEXT;
                    replacementString.TEXT_DICT = uiString.getValue().TEXT_DICT;

                    if (uiString.getValue().TEXT != null) {
                        replacementString.TEXT = filterString(uiString.getValue().TEXT);
                    }

                    if ((replacementString.TEXT != null && !(Arrays.equals(replacementString.TEXT, uiString.getValue().TEXT)))) {
                        renameUI.put(uiString.getKey(), new UIStrings[]{uiString.getValue(), replacementString});
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void replaceStrings(boolean toSouls) {
        int tmp = toSouls ? 1 : 0;
        Map<String, CardStrings> cardStrings = (Map<String, CardStrings>) ReflectionHacks.getPrivateStatic(LocalizedStrings.class, "cards");
        for (Map.Entry<String, CardStrings[]> val : renameC.entrySet()) {
            cardStrings.put(val.getKey(), val.getValue()[tmp]);
        }
        ReflectionHacks.setPrivateStaticFinal(LocalizedStrings.class, "cards", cardStrings);

        Map<String, EventStrings> eventStrings = (Map<String, EventStrings>) ReflectionHacks.getPrivateStatic(LocalizedStrings.class, "events");
        for (Map.Entry<String, EventStrings[]> val : renameE.entrySet()) {
            eventStrings.put(val.getKey(), val.getValue()[tmp]);
        }
        ReflectionHacks.setPrivateStaticFinal(LocalizedStrings.class, "events", eventStrings);

        Map<String, RelicStrings> relicStrings = (Map<String, RelicStrings>) ReflectionHacks.getPrivateStatic(LocalizedStrings.class, "relics");
        for (Map.Entry<String, RelicStrings[]> val : renameR.entrySet()) {
            relicStrings.put(val.getKey(), val.getValue()[tmp]);
        }
        ReflectionHacks.setPrivateStaticFinal(LocalizedStrings.class, "relics", relicStrings);

        Map<String, CharacterStrings> characterStrings = (Map<String, CharacterStrings>) ReflectionHacks.getPrivateStatic(LocalizedStrings.class, "characters");
        for (Map.Entry<String, CharacterStrings[]> val : renameCh.entrySet()) {
            characterStrings.put(val.getKey(), val.getValue()[tmp]);
        }
        ReflectionHacks.setPrivateStaticFinal(LocalizedStrings.class, "characters", characterStrings);

        Map<String, PowerStrings> powerStrings = (Map<String, PowerStrings>) ReflectionHacks.getPrivateStatic(LocalizedStrings.class, "powers");
        for (Map.Entry<String, PowerStrings[]> val : renameP.entrySet()) {
            powerStrings.put(val.getKey(), val.getValue()[tmp]);
        }
        ReflectionHacks.setPrivateStaticFinal(LocalizedStrings.class, "powers", powerStrings);

        Map<String, UIStrings> uiStrings = (Map<String, UIStrings>) ReflectionHacks.getPrivateStatic(LocalizedStrings.class, "ui");
        for (Map.Entry<String, UIStrings[]> val : renameUI.entrySet()) {
            uiStrings.put(val.getKey(), val.getValue()[tmp]);
        }
        ReflectionHacks.setPrivateStaticFinal(LocalizedStrings.class, "ui", uiStrings);

        //If changed once, will stop working
        String[] hack = CardCrawlGame.languagePack.getUIString("CharacterOption").TEXT;
        for (int i = 0; i < CharacterOption.TEXT.length; i++) {
            //System.out.println(CharacterOption.TEXT[i] + " " + hack[i]);
            CharacterOption.TEXT[i] = hack[i];
        }


    }

    private static void setFinalStatic(Field field, Object newValue) throws Exception {
        field.setAccessible(true);

        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        field.set(null, newValue);
    }

    private static String[] filterString(String[] str) {
        String[] plsNoReplace = new String[str.length];
        System.arraycopy(str, 0, plsNoReplace, 0, str.length);
        for (int i = 0; i < str.length; i++) {
            plsNoReplace[i] = filterString(plsNoReplace[i]);
        }
        return plsNoReplace;
    }

    private static String filterString(String spireString) {
        String replacementString = spireString;

        for (ReplaceData data : downfallMod.wordReplacements) {
            for (String phrase : data.KEYS) {
                if (data.VALUE == null) {
                    data.VALUE = "";
                }
                if (phrase != null) {
                    replacementString = replacementString.replaceAll(phrase, data.VALUE);
                }

            }
        }

        return replacementString;
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

    @SpirePatch(clz = CardCrawlGame.class, method = "create")
    public static class PostLoadLocalizationPatch {
        @SpireInsertPatch(locator = Locator.class, localvars = {"languagePack"})
        public static void PostLocalization(CardCrawlGame __instance, LocalizedStrings languagePack) {
            for (Settings.GameLanguage lang : downfallMod.SupportedLanguages) {
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
    }

    //Disabled this as it caused a crash with Unknown generation. MAY want to look at this later.
    /*
    downfall.patches.ui.topPanel.GoldToSoulPatches$PleaseChange.patch(GoldToSoulPatches.java:345) ~[downfall.jar:?]
    at com.megacrit.cardcrawl.cards.colorless.HandOfGreed.makeCopy(HandOfGreed.java:48) ~[?:?]
    at sneckomod.cards.unknowns.AbstractUnknownCard.replaceUnknown(AbstractUnknownCard.java:80) ~[downfall.jar:?]
    at sneckomod.cards.unknowns.AbstractUnknownCard$2.update(AbstractUnknownCard.java:63) ~[downfall.jar:?]
    at com.megacrit.cardcrawl.actions.GameActionManager.update(GameActionManager.java:179) ~[?:?]
    at com.megacrit.cardcrawl.rooms.AbstractRoom.update(AbstractRoom.java:325) ~[?:?]
    at com.megacrit.cardcrawl.dungeons.AbstractDungeon.update(AbstractDungeon.java:2512) ~[?:?]
    at com.megacrit.cardcrawl.core.CardCrawlGame.update(CardCrawlGame.java:879) ~[?:?]
    at com.megacrit.cardcrawl.core.CardCrawlGame.render(CardCrawlGame.java:427) [?:?]
    at com.badlogic.gdx.backends.lwjgl.LwjglApplication.mainLoop(LwjglApplication.java:225) [?:?]
    at com.badlogic.gdx.backends.lwjgl.LwjglApplication$1.run(LwjglApplication.java:126) [desktop-1.0.jar:?]
    */

    /*
    //Fix Hand of Greed
    @SpirePatch(clz = HandOfGreed.class, method = "makeCopy")
    public static class PleaseChange {
        @SpirePostfixPatch
        public static AbstractCard patch(AbstractCard __result, HandOfGreed __instance) {
            __result.rawDescription = renameC.get(HandOfGreed.ID)[EvilModeCharacterSelect.evilMode?1:0].DESCRIPTION;
            return __result;
        }
    }
    */
}