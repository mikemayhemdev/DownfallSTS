package evilWithin.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import evilWithin.EvilWithinMod;

public class UnflipUI
{
    @SpirePatch(
            clz = AbstractCreature.class,
            method = "renderHealth"
    )
    public static class HealthBar
    {
        public static void Prefix(AbstractCreature __instance, SpriteBatch sb)
        {
            if (!EvilWithinMod.EXPERIMENTAL_FLIP) return;

            FlipRoom.endFBO(sb);
            __instance.hb.cX = Settings.WIDTH - __instance.hb.cX;
        }

        public static void Postfix(AbstractCreature __instance, SpriteBatch sb)
        {
            if (!EvilWithinMod.EXPERIMENTAL_FLIP) return;

            __instance.hb.cX = Settings.WIDTH - __instance.hb.cX;
            FlipRoom.startFBO(sb);
        }
    }
}
