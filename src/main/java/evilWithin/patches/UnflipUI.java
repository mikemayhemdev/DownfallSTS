package evilWithin.patches;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import evilWithin.EvilWithinMod;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

public class UnflipUI
{
    public static float flipPos(SpriteBatch sb, float x)
    {
        if (FlipRoom.isFlipped()) {
            FlipRoom.pauseFlip(sb);
            x = Gdx.graphics.getWidth() - x;
        }
        return x;
    }

    @SpirePatch(
            clz = AbstractCreature.class,
            method = "renderHealth"
    )
    public static class HealthBar
    {
        public static void Prefix(AbstractCreature __instance, SpriteBatch sb)
        {
            if (!EvilWithinMod.EXPERIMENTAL_FLIP) return;

            FlipRoom.endFlip(sb);
            __instance.hb.cX = Settings.WIDTH - __instance.hb.cX;
        }

        public static void Postfix(AbstractCreature __instance, SpriteBatch sb)
        {
            if (!EvilWithinMod.EXPERIMENTAL_FLIP) return;

            __instance.hb.cX = Settings.WIDTH - __instance.hb.cX;
            FlipRoom.beginFlip(sb);
        }
    }

    @SpirePatch(
            clz = FontHelper.class,
            method = "renderFontLeftTopAligned"
    )
    public static class TextDamageIntent
    {
        public static SpireReturn<Void> Prefix(SpriteBatch sb, BitmapFont font, String msg, float x, float y, Color c)
        {
            if (FlipRoom.isFlipped()) {
                FlipRoom.pauseFlip(sb);

                x = Gdx.graphics.getWidth() - x;
                FontHelper.renderFontRightTopAligned(sb, font, msg, x, y, c);

                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }

        public static void Postfix(SpriteBatch sb, BitmapFont font, String msg, float x, float y, Color c)
        {
            FlipRoom.unpauseFlip(sb);
        }
    }

    @SpirePatch(
            clz = FontHelper.class,
            method = SpirePatch.STATICINITIALIZER
    )
    public static class CenteredText
    {

        public static void Raw(CtBehavior ctBehavior) throws Exception
        {
            CtClass ctClass = ctBehavior.getDeclaringClass();

            for (CtMethod ctMethod : ctClass.getDeclaredMethods()) {
                if (ctMethod.getName().startsWith("renderFontCentered")) {
                    ctMethod.insertBefore("x = " + UnflipUI.class.getName() + ".flipPos(sb, x);");
                    ctMethod.insertAfter(FlipRoom.class.getName() + ".unpauseFlip(sb);");
                }
            }
        }
    }
}
