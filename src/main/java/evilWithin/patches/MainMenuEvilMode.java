package evilWithin.patches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuPanelButton;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
import com.megacrit.cardcrawl.screens.mainMenu.MenuPanelScreen;
import evilWithin.EvilWithinMod;
import javassist.CtBehavior;

import java.lang.reflect.Field;
import java.util.function.Consumer;

public class MainMenuEvilMode
{
    public static class Enums
    {
        @SpireEnum
        public static MenuPanelScreen.PanelScreen EVIL;
        @SpireEnum
        public static MainMenuPanelButton.PanelClickResult PLAY_EVIL;
    }

    @SpirePatch(
            clz = MainMenuPanelButton.class,
            method = "buttonEffect"
    )
    public static class RedirectPlayNormal
    {
        public static SpireReturn<Void> Prefix(MainMenuPanelButton __instance, MainMenuPanelButton.PanelClickResult ___result)
        {
            if (___result == MainMenuPanelButton.PanelClickResult.PLAY_NORMAL) {
                LatePanelOpen.lateOpen = x -> {
                    x.open(Enums.EVIL);
                };
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = MenuPanelScreen.class,
            method = "update"
    )
    public static class RedirectBackButton
    {
        @SpireInsertPatch(
                locator = Locator.class
        )
        public static SpireReturn<Void> Insert(MenuPanelScreen __instance, MenuPanelScreen.PanelScreen ___screen)
        {
            if (___screen == Enums.EVIL) {
                __instance.button.hb.clicked = false;
                __instance.button.hb.hovered = false;
                InputHelper.justClickedLeft = false;
                LatePanelOpen.lateOpen = x -> {
                    x.open(MenuPanelScreen.PanelScreen.PLAY);
                };
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }

        public static class Locator extends SpireInsertLocator
        {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception
            {
                Matcher matcher = new Matcher.FieldAccessMatcher(MainMenuScreen.class, "screen");
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }

    // Necessary to avoid comodifcation exception
    @SpirePatch(
            clz = MenuPanelScreen.class,
            method = "update"
    )
    public static class LatePanelOpen
    {
        private static Consumer<MenuPanelScreen> lateOpen = null;

        public static void Postfix(MenuPanelScreen __instance)
        {
            if (lateOpen != null) {
                lateOpen.accept(__instance);
                lateOpen = null;
            }
        }
    }

    @SpirePatch(
            clz = MenuPanelScreen.class,
            method = "initializePanels"
    )
    public static class InitEvilPanels
    {
        public static void Postfix(MenuPanelScreen __instance, MenuPanelScreen.PanelScreen ___screen, float ___PANEL_Y)
        {
            if (___screen == Enums.EVIL) {
                __instance.panels.add(new EvilMainMenuPanelButton(
                        MainMenuPanelButton.PanelClickResult.PLAY_NORMAL,
                        MainMenuPanelButton.PanelColor.BLUE,
                        Settings.WIDTH / 2f - 225f * Settings.scale,
                        ___PANEL_Y
                ));
                __instance.panels.add(new EvilMainMenuPanelButton(
                        Enums.PLAY_EVIL,
                        MainMenuPanelButton.PanelColor.RED,
                        Settings.WIDTH / 2f + 225f * Settings.scale,
                        ___PANEL_Y
                ));
            }
        }
    }

    public static class EvilMainMenuPanelButton extends MainMenuPanelButton
    {
        private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(EvilWithinMod.makeID("EvilMenuPanel"));

        public EvilMainMenuPanelButton(PanelClickResult setResult, PanelColor setColor, float x, float y)
        {
            super(setResult, setColor, x, y);
        }

        @SpireOverride
        protected void setLabel()
        {
            if (getResult() == Enums.PLAY_EVIL) {
                ReflectionHacks.setPrivate(this, MainMenuPanelButton.class, "panelImg", ImageMaster.MENU_PANEL_BG_RED);
                ReflectionHacks.setPrivate(this, MainMenuPanelButton.class, "portraitImg", ImageMaster.P_STAT_CHAR);
                ReflectionHacks.setPrivate(this, MainMenuPanelButton.class, "header", uiStrings.TEXT[0]);
                ReflectionHacks.setPrivate(this, MainMenuPanelButton.class, "description", uiStrings.TEXT[1]);
            } else {
                SpireSuper.call();
            }
        }

        @SpireOverride
        protected void buttonEffect()
        {
            if (getResult() == Enums.PLAY_EVIL || getResult() == PanelClickResult.PLAY_NORMAL) {
                // TODO: Do something different for evil character screen
                CardCrawlGame.mainMenuScreen.charSelectScreen.open(false);
            } else {
                SpireSuper.call();
            }
        }

        protected PanelClickResult getResult()
        {
            if (resultField == null) {
                try {
                    resultField = MainMenuPanelButton.class.getDeclaredField("result");
                    resultField.setAccessible(true);
                } catch (NoSuchFieldException e) {
                    throw new RuntimeException(e);
                }
            }

            try {
                return (PanelClickResult) resultField.get(this);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        private static Field resultField = null;
    }
}
