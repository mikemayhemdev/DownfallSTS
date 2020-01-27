package evilWithin.patches;

import basemod.CustomCharacterSelectScreen;
import basemod.ReflectionHacks;
import basemod.patches.whatmod.WhatMod;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
import evilWithin.EvilWithinMod;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class EvilModeCharacterSelect
{
    public static boolean evilMode = false;

    @SpirePatch(
            clz = CharacterSelectScreen.class,
            method = "open"
    )
    public static class ChangeToEvilOptions
    {
        public static void Prefix(CharacterSelectScreen __instance, boolean isEndless)
        {
            if (__instance instanceof CustomCharacterSelectScreen) {
                CustomCharacterSelectScreen screen = (CustomCharacterSelectScreen) __instance;
                if (evilMode) {
                    screen.options.clear();

                    ArrayList<CharacterOption> allOptions = (ArrayList<CharacterOption>) ReflectionHacks.getPrivate(screen, CustomCharacterSelectScreen.class, "allOptions");
                    for (CharacterOption o : allOptions) {
                        if (EvilWithinMod.modID.equals(WhatMod.findModID(o.c.getClass()))) {
                            screen.options.add(o);
                        }
                    }

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
                }
            }
        }
    }

    @SpirePatch(
            clz = MainMenuScreen.class,
            method = "update"
    )
    public static class ResetOptions
    {
        private static int saved_maxSelectIndex = -1;

        public static void Prefix(MainMenuScreen __instance)
        {
            if (__instance.screen != MainMenuScreen.CurScreen.CHAR_SELECT) {
                if (saved_maxSelectIndex >= 0) {
                    if (__instance.charSelectScreen instanceof CustomCharacterSelectScreen) {
                        CustomCharacterSelectScreen screen = (CustomCharacterSelectScreen) __instance.charSelectScreen;
                        ReflectionHacks.setPrivate(screen, CustomCharacterSelectScreen.class, "selectIndex", 0);
                        ReflectionHacks.setPrivate(screen, CustomCharacterSelectScreen.class, "maxSelectIndex", saved_maxSelectIndex);

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
    public static class ChangeText
    {
        public static ExprEditor Instrument()
        {
            return new ExprEditor() {
                @Override
                public void edit(FieldAccess f) throws CannotCompileException
                {
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
