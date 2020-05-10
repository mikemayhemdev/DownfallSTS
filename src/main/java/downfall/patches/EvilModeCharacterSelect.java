package downfall.patches;

import basemod.CustomCharacterSelectScreen;
import basemod.ReflectionHacks;
import basemod.patches.whatmod.WhatMod;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import downfall.downfallMod;
import downfall.patches.ui.topPanel.GoldToSoulPatches;
import guardian.characters.GuardianCharacter;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;
import sneckomod.TheSnecko;
import theHexaghost.TheHexaghost;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class EvilModeCharacterSelect {
    public static boolean evilMode = false;

    private static List<CharacterOption> villains = new ArrayList<>();

    @SpirePatch(
            clz = CustomCharacterSelectScreen.class,
            method = "initialize"
    )
    public static class RemoveEvilOptions {
        @SpireInsertPatch(
                locator = Locator.class
        )
        public static void Insert(CustomCharacterSelectScreen __instance) {
            villains.clear();
            Iterator<CharacterOption> iter = __instance.options.iterator();
            while (iter.hasNext()) {
                CharacterOption o = iter.next();
                if (!villains.contains(o) && downfallMod.modID.equals(WhatMod.findModID(o.c.getClass()))) {
                    iter.remove();
                    villains.add(o);
                }
            }

            if (villains.size() >= 2) {
                Collections.swap(villains, 0, 2);  //Switch Guardian and Slimeboss G H S -> S H G
                Collections.swap(villains, 1, 2);  //Switch Hexaghost and Guardian S H G -> S G H
            }
            
            for (CharacterOption co : villains){
                if (co.c instanceof GuardianCharacter && UnlockTracker.isCharacterLocked("Guardian")){
                    co.locked = true;
                    ReflectionHacks.setPrivate(co,CharacterOption.class,"buttonImg",ImageMaster.CHAR_SELECT_LOCKED);
                }
                if (co.c instanceof TheHexaghost && UnlockTracker.isCharacterLocked("Hexaghost")){
                    co.locked = true;
                    ReflectionHacks.setPrivate(co,CharacterOption.class,"buttonImg",ImageMaster.CHAR_SELECT_LOCKED);
                }
                if (co.c instanceof TheSnecko && UnlockTracker.isCharacterLocked("Snecko")){
                    co.locked = true;
                    ReflectionHacks.setPrivate(co,CharacterOption.class,"buttonImg",ImageMaster.CHAR_SELECT_LOCKED);
                }
            }
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
                    screen.options.addAll(villains);

                    ResetOptions.saved_optionsPerIndex = (int) ReflectionHacks.getPrivate(screen, CustomCharacterSelectScreen.class, "optionsPerIndex");
                    ReflectionHacks.setPrivate(screen, CustomCharacterSelectScreen.class, "optionsPerIndex", 4);

                    try {
                        Method m = CustomCharacterSelectScreen.class.getDeclaredMethod("positionButtons");
                        m.setAccessible(true);
                        m.invoke(screen);
                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }

                    ReflectionHacks.setPrivate(screen, CustomCharacterSelectScreen.class, "selectIndex", 0);
                    ResetOptions.saved_maxSelectIndex = (int) ReflectionHacks.getPrivate(screen, CustomCharacterSelectScreen.class, "maxSelectIndex");
                    ReflectionHacks.setPrivate(screen, CustomCharacterSelectScreen.class, "maxSelectIndex", 0);
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
                        ReflectionHacks.setPrivate(screen, CustomCharacterSelectScreen.class, "selectIndex", 0);
                        ReflectionHacks.setPrivate(screen, CustomCharacterSelectScreen.class, "maxSelectIndex", saved_maxSelectIndex);
                        ReflectionHacks.setPrivate(screen, CustomCharacterSelectScreen.class, "optionsPerIndex", saved_optionsPerIndex);

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
