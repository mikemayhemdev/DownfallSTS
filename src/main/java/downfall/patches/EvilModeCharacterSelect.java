package downfall.patches;

import automaton.AutomatonChar;
import basemod.CustomCharacterSelectScreen;
import basemod.ReflectionHacks;
import champ.ChampChar;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import downfall.patches.ui.topPanel.GoldToSoulPatches;
import guardian.patches.GuardianEnum;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;
import slimebound.patches.SlimeboundEnum;
import sneckomod.TheSnecko;
import theHexaghost.TheHexaghost;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class EvilModeCharacterSelect {
    public static boolean villainsInNormalAndNormalInVillains = false;

    public static boolean evilMode = false;

    private static int maxEvilSelectIndex = 0;
    private static final List<CharacterOption> villains = new ArrayList<>();
    private static final List<CharacterOption> standard = new ArrayList<>();

    @SpirePatch(
            clz = CustomCharacterSelectScreen.class,
            method = "initialize"
    )
    public static class InitializeCharacterOptions {
        @SpireInsertPatch(
                locator = Locator.class
        )
        public static void Insert(CustomCharacterSelectScreen __instance) {
            villains.clear();
            standard.clear();

            Iterator<CharacterOption> options = __instance.options.iterator();

            ArrayList<CharacterOption> basegameOptions = new ArrayList<>(), moddedOptions = new ArrayList<>();
            CharacterOption[] villainOptions = new CharacterOption[6];

            while (options.hasNext()) {
                CharacterOption o = options.next();

                switch (o.c.chosenClass) {
                    case IRONCLAD:
                    case THE_SILENT:
                    case DEFECT:
                    case WATCHER:
                        if (villainsInNormalAndNormalInVillains)
                            basegameOptions.add(o);
                        break;
                    default:
                        boolean isVillain = true;
                        if (o.c.chosenClass == SlimeboundEnum.SLIMEBOUND) {
                            villainOptions[0] = o;
                        } else if (o.c.chosenClass == GuardianEnum.GUARDIAN) {
                            if (UnlockTracker.isCharacterLocked("Guardian")) {
                                o.locked = true;
                                ReflectionHacks.setPrivate(o, CharacterOption.class, "buttonImg", ImageMaster.CHAR_SELECT_LOCKED);
                            }
                            villainOptions[1] = o;
                        } else if (o.c.chosenClass == TheHexaghost.Enums.THE_SPIRIT) {
                            if (UnlockTracker.isCharacterLocked("Hexaghost")) {
                                o.locked = true;
                                ReflectionHacks.setPrivate(o, CharacterOption.class, "buttonImg", ImageMaster.CHAR_SELECT_LOCKED);
                            }
                            villainOptions[2] = o;
                        } else if (o.c.chosenClass == ChampChar.Enums.THE_CHAMP) {
                            if (UnlockTracker.isCharacterLocked("Champ")) {
                                o.locked = true;
                                ReflectionHacks.setPrivate(o, CharacterOption.class, "buttonImg", ImageMaster.CHAR_SELECT_LOCKED);
                            }
                            villainOptions[3] = o;
                        } else if (o.c.chosenClass == AutomatonChar.Enums.THE_AUTOMATON) {
                            if (UnlockTracker.isCharacterLocked("Automaton")) {
                                o.locked = true;
                                ReflectionHacks.setPrivate(o, CharacterOption.class, "buttonImg", ImageMaster.CHAR_SELECT_LOCKED);
                            }
                            villainOptions[4] = o;
                        } else if (o.c.chosenClass == TheSnecko.Enums.THE_SNECKO) {
                            if (UnlockTracker.isCharacterLocked("Snecko")) {
                                o.locked = true;
                                ReflectionHacks.setPrivate(o, CharacterOption.class, "buttonImg", ImageMaster.CHAR_SELECT_LOCKED);
                            }
                            villainOptions[5] = o;
                        } else {
                            isVillain = false;
                            moddedOptions.add(o);
                        }

                        if (isVillain && !villainsInNormalAndNormalInVillains)
                            options.remove();

                        break;
                }
            }

            villains.addAll(Arrays.asList(villainOptions));
            villains.addAll(basegameOptions); //Will be empty if disabled
            villains.addAll(moddedOptions);

            maxEvilSelectIndex = (int) Math.ceil(((float) villains.size() / 4)) - 1;

            standard.addAll(__instance.options);

            /// To note: Should villains in the normal mode also be sorted? It looks a bit awkward when they're locked out of order.
        }

        public static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(CustomCharacterSelectScreen.class, "options");
                ArrayList<Matcher> matchers = new ArrayList<>();
                matchers.add(finalMatcher);
                return LineFinder.findInOrder(ctBehavior, matchers, finalMatcher);
            }
        }
    }

    @SpirePatch(
            clz = CharacterSelectScreen.class,
            method = "open"
    )
    public static class ChangeToEvilOptions {
        public static void Prefix(CharacterSelectScreen __instance, boolean isEndless) {

            if (__instance instanceof CustomCharacterSelectScreen) {
                CustomCharacterSelectScreen screen = (CustomCharacterSelectScreen) __instance;
                if (evilMode) {
                    GoldToSoulPatches.changeGoldToSouls(false);
                    screen.options.clear();
                    ArrayList<CharacterOption> allOptions = (ArrayList<CharacterOption>) ReflectionHacks.getPrivate(screen, CustomCharacterSelectScreen.class, "allOptions");

                    ResetOptions.saved_optionsPerIndex = (int) ReflectionHacks.getPrivate(screen, CustomCharacterSelectScreen.class, "optionsPerIndex");
                    ReflectionHacks.setPrivate(screen, CustomCharacterSelectScreen.class, "optionsPerIndex", 4);

                    ReflectionHacks.setPrivate(screen, CustomCharacterSelectScreen.class, "selectIndex", 0);
                    ResetOptions.saved_maxSelectIndex = (int) ReflectionHacks.getPrivate(screen, CustomCharacterSelectScreen.class, "maxSelectIndex");
                    ReflectionHacks.setPrivate(screen, CustomCharacterSelectScreen.class, "maxSelectIndex", maxEvilSelectIndex);

                    allOptions.clear();
                    allOptions.addAll(villains);

                    try {
                        Method m = CustomCharacterSelectScreen.class.getDeclaredMethod("setCurrentOptions", boolean.class);
                        m.setAccessible(true);
                        m.invoke(screen, false);
                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    GoldToSoulPatches.changeGoldToSouls(true);
                }
            }
        }
    }

    @SpirePatch(
            clz = MainMenuScreen.class,
            method = "update"
    )
    public static class ResetOptions {
        private static int saved_maxSelectIndex = -1;
        private static int saved_optionsPerIndex = 4;

        public static void Prefix(MainMenuScreen __instance) {

            if (__instance.screen != MainMenuScreen.CurScreen.CHAR_SELECT) {
                if (saved_maxSelectIndex >= 0) {
                    if (__instance.charSelectScreen instanceof CustomCharacterSelectScreen) {
                        CustomCharacterSelectScreen screen = (CustomCharacterSelectScreen) __instance.charSelectScreen;
                        ArrayList<CharacterOption> allOptions = (ArrayList<CharacterOption>) ReflectionHacks.getPrivate(screen, CustomCharacterSelectScreen.class, "allOptions");

                        ReflectionHacks.setPrivate(screen, CustomCharacterSelectScreen.class, "selectIndex", 0);
                        ReflectionHacks.setPrivate(screen, CustomCharacterSelectScreen.class, "maxSelectIndex", saved_maxSelectIndex);
                        ReflectionHacks.setPrivate(screen, CustomCharacterSelectScreen.class, "optionsPerIndex", saved_optionsPerIndex);

                        allOptions.clear();
                        allOptions.addAll(standard);

                        try {
                            Method m = CustomCharacterSelectScreen.class.getDeclaredMethod("setCurrentOptions", boolean.class);
                            m.setAccessible(true);
                            m.invoke(screen, false);
                        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    saved_maxSelectIndex = -1;
                }
            }
        }
    }

    @SpirePatch(
            clz = CharacterSelectScreen.class,
            method = "render"
    )
    public static class ChangeText {
        public static ExprEditor Instrument() {
            return new ExprEditor() {
                @Override
                public void edit(FieldAccess f) throws CannotCompileException {
                    if (f.isReader() && f.getFieldName().equals("CHOOSE_CHAR_MSG")) {
                        f.replace("if (" + EvilModeCharacterSelect.class.getName() + ".evilMode) {" +
                                "$_ = " + MainMenuEvilMode.EvilMainMenuPanelButton.class.getName() + ".uiStrings.TEXT[2];" +
                                "} else {" +
                                "$_ = $proceed($$);" +
                                "}");
                    }
                }
            };
        }
    }
}
