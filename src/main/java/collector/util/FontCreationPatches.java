package collector.util;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.helpers.FontHelper;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class FontCreationPatches {
    private static final float TIP_FONT = 17.0F;
    public static BitmapFont tipFont;
    private static FreeTypeFontGenerator.FreeTypeFontParameter param = ReflectionHacks.getPrivateStatic(FontHelper.class, "param");

    @SpirePatch(clz = FontHelper.class, method = "initialize")
    public static class AddMyFont {
        @SpireInsertPatch(locator = Locator.class)
        public static void patch() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            Method method = FontHelper.class.getDeclaredMethod("prepFont", float.class, boolean.class);
            method.setAccessible(true);
            tipFont = (BitmapFont) method.invoke(null, new Object[]{TIP_FONT, false});
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(FontHelper.class, "cardDescFont_N");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }
}
