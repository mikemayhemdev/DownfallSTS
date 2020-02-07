package evilWithin.patches;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AnimatedNpc;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.OverlayMenu;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.shop.Merchant;
import evilWithin.EvilWithinMod;
import javassist.CtBehavior;

public class FlipRoom
{
    private static boolean isFlipped = false;
    private static OrthographicCamera camera = null;
    private static Matrix4 oldProjectionMatrix = null;

    public static boolean isFlipped()
    {
        return isFlipped;
    }

    private static void initFlip()
    {
        if (camera == null) {
            camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            if (Settings.VERT_LETTERBOX_AMT != 0 || Settings.HORIZ_LETTERBOX_AMT != 0) {
                camera.position.set(Settings.WIDTH / 2f, Settings.HEIGHT / 2f, 0);
            } else {
                camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
            }
            camera.rotate(camera.up, 180);
            camera.update();
        }
    }

    public static void beginFlip(SpriteBatch sb)
    {
        if (!EvilWithinMod.EXPERIMENTAL_FLIP) return;

        if (isFlipped()) return;

        initFlip();
        sb.flush();

        oldProjectionMatrix = sb.getProjectionMatrix().cpy();
        sb.setProjectionMatrix(camera.combined);
        CardCrawlGame.psb.setProjectionMatrix(camera.combined);

        isFlipped = true;
    }

    public static void endFlip(SpriteBatch sb)
    {
        if (!EvilWithinMod.EXPERIMENTAL_FLIP) return;

        if (isFlipped) {
            sb.flush();

            if (oldProjectionMatrix != null) {
                CardCrawlGame.psb.setProjectionMatrix(oldProjectionMatrix);
                sb.setProjectionMatrix(oldProjectionMatrix);
                oldProjectionMatrix = null;
            }
            isFlipped = false;
        }
    }

    public static void pauseFlip(SpriteBatch sb)
    {
        if (isFlipped) {
            endFlip(sb);
            isFlipped = true;
        }
    }

    public static void unpauseFlip(SpriteBatch sb)
    {
        if (isFlipped) {
            beginFlip(sb);
        }
    }

    @SpirePatch(
            clz = AbstractRoom.class,
            method = "render"
    )
    public static class Room
    {
        public static void Prefix(AbstractRoom __instance, SpriteBatch sb)
        {
            beginFlip(sb);
        }

        public static void Postfix(AbstractRoom __instance, SpriteBatch sb)
        {
            endFlip(sb);
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
            beginFlip(sb);
        }

        @SpireInsertPatch(
                locator = End1Locator.class
        )
        public static void End1(AbstractDungeon __instance, SpriteBatch sb)
        {
            endFlip(sb);
        }

        @SpireInsertPatch(
                locator = End2Locator.class
        )
        public static void End2(AbstractDungeon __instance, SpriteBatch sb)
        {
            endFlip(sb);
        }

        @SpireInsertPatch(
                locator = StartTopLevelLocator.class
        )
        public static void StartTopLevel(AbstractDungeon __instance, SpriteBatch sb)
        {
            beginFlip(sb);
        }

        @SpireInsertPatch(
                locator = EndTopLevelLocator.class
        )
        public static void EndTopLevel(AbstractDungeon __instance, SpriteBatch sb)
        {
            endFlip(sb);
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
