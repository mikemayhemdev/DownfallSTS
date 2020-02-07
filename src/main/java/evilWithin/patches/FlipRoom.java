package evilWithin.patches;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.OverlayMenu;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import evilWithin.EvilWithinMod;
import javassist.CtBehavior;

public class FlipRoom
{
    private static FrameBuffer fbo = null;

    private static void initFBO()
    {
        if (fbo == null) {
            fbo = new FrameBuffer(Pixmap.Format.RGBA8888, Settings.WIDTH, Settings.HEIGHT, false);
        }
    }

    public static void startFBO(SpriteBatch sb)
    {
        if (!EvilWithinMod.EXPERIMENTAL_FLIP) return;

        initFBO();
        sb.end();
        fbo.begin();

        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sb.begin();
    }

    public static void endFBO(SpriteBatch sb)
    {
        if (!EvilWithinMod.EXPERIMENTAL_FLIP) return;

        sb.end();
        fbo.end();

        sb.begin();
        sb.setColor(Color.WHITE);

        TextureRegion fboTex = new TextureRegion(fbo.getColorBufferTexture());
        // Flip horizontally, it's already flipped vertically so flip it back
        fboTex.flip(true, true);
        sb.draw(fboTex, 0, 0, Settings.WIDTH, Settings.HEIGHT);
    }

    @SpirePatch(
            clz = AbstractRoom.class,
            method = "render"
    )
    public static class Room
    {
        public static void Prefix(AbstractRoom __instance, SpriteBatch sb)
        {
            startFBO(sb);
        }

        public static void Postfix(AbstractRoom __instance, SpriteBatch sb)
        {
            endFBO(sb);
        }
    }

    @SpirePatch(
            clz = AbstractDungeon.class,
            method = "render"
    )
    public static class VFX
    {
        @SpireInsertPatch(
                locator = StartLocator.class
        )
        public static void Start(AbstractDungeon __instance, SpriteBatch sb)
        {
            startFBO(sb);
        }

        @SpireInsertPatch(
                locator = End1Locator.class
        )
        public static void End1(AbstractDungeon __instance, SpriteBatch sb)
        {
            endFBO(sb);
        }

        @SpireInsertPatch(
                locator = End2Locator.class
        )
        public static void End2(AbstractDungeon __instance, SpriteBatch sb)
        {
            endFBO(sb);
        }

        @SpireInsertPatch(
                locator = StartTopLevelLocator.class
        )
        public static void StartTopLevel(AbstractDungeon __instance, SpriteBatch sb)
        {
            startFBO(sb);
        }

        @SpireInsertPatch(
                locator = EndTopLevelLocator.class
        )
        public static void EndTopLevel(AbstractDungeon __instance, SpriteBatch sb)
        {
            endFBO(sb);
        }

        private static class StartLocator extends SpireInsertLocator
        {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception
            {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractDungeon.class, "effectList");
                return LineFinder.findAllInOrder(ctBehavior, finalMatcher);
            }
        }

        private static class End1Locator extends SpireInsertLocator
        {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception
            {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractRoom.class, "render");
                return LineFinder.findInOrder(ctBehavior, finalMatcher);
            }
        }

        private static class End2Locator extends SpireInsertLocator
        {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception
            {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(OverlayMenu.class, "render");
                return LineFinder.findInOrder(ctBehavior, finalMatcher);
            }
        }

        private static class StartTopLevelLocator extends SpireInsertLocator
        {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception
            {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractDungeon.class, "topLevelEffects");
                return LineFinder.findInOrder(ctBehavior, finalMatcher);
            }
        }

        private static class EndTopLevelLocator extends SpireInsertLocator
        {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception
            {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(SpriteBatch.class, "setColor");
                int[] found = LineFinder.findAllInOrder(ctBehavior, finalMatcher);
                return new int[]{found[found.length-1]};
            }
        }
    }
}
