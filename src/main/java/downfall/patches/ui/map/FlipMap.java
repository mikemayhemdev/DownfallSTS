package downfall.patches.ui.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheEnding;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.map.*;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.screens.DungeonMapScreen;
import com.megacrit.cardcrawl.ui.buttons.DynamicBanner;
import downfall.patches.EvilModeCharacterSelect;
import downfall.patches.actlikeit.MapCompatiblity;
import javassist.*;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;
import javassist.expr.MethodCall;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class FlipMap {
    @SpirePatch(
            clz = AbstractDungeon.class,
            method = "generateMap"
    )
    @SpirePatch(
            clz = TheEnding.class,
            method = "generateSpecialMap"
    )
    public static class EverythingIsWrong
    {
        public static int startY = 0;

        @SpireInsertPatch(
                locator = Locator.class
        )
        public static void flipflipflipflipflip()
        {
            if (EvilModeCharacterSelect.evilMode)
                flip(AbstractDungeon.map);
        }

        private static void flip(ArrayList<ArrayList<MapRoomNode>> map)
        {
            startY = 0;

            ArrayList<MapNodeData> edges = new ArrayList<>();

            for (ArrayList<MapRoomNode> row : map)
                for (MapRoomNode n : row)
                {
                    if (n.room == null || n.getRoomSymbol(true) == null || n.room instanceof MonsterRoomBoss)
                        continue; //for some reason act 4 map generates the boss room node and final victory room nodes even though they aren't used

                    for (MapEdge e : n.getEdges())
                        if (!edgeArrayContains(edges, e))
                        {
                            if (e.dstY >= 0 && e.dstY < map.size())
                                edges.add(new MapNodeData(e, n, map.get(e.dstY).get(e.dstX)));
                        }

                    n.getEdges().clear();
                }

            ArrayList<MapRoomNode> finalNodes = new ArrayList<>();

            for (MapNodeData data : edges)
            {
                if (data.end.room == null || data.end.getRoomSymbol(true) == null || data.end.room instanceof MonsterRoomBoss)
                    continue;

                if (data.end.y > startY)
                    startY = data.end.y;

                data.end.addEdge(new MapEdge(data.end.x, data.end.y, data.end.offsetX, data.end.offsetY, data.start.x, data.start.y, data.start.offsetX, data.start.offsetY,false));
                data.end.getEdges().sort(MapEdge::compareTo);

                if (data.start.y == 0)
                    finalNodes.add(data.start);
            }

            for (MapRoomNode n : finalNodes)
            {
                n.addEdge(new MapEdge(n.x, n.y, n.offsetX, n.offsetY, 3, -1, 0.0F, Settings.MAP_DST_Y * 2, true));
            }
        }

        private static boolean edgeArrayContains(ArrayList<MapNodeData> data, MapEdge e)
        {
            for (MapNodeData d : data)
                if (d.e.equals(e))
                    return true;

            return false;
        }

        private static class MapNodeData
        {
            public MapEdge e;
            public MapRoomNode start, end;

            public MapNodeData(MapEdge e, MapRoomNode start, MapRoomNode end)
            {
                this.e = e;
                this.start = start;
                this.end = end;
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(Logger.class, "info");
                return LineFinder.findInOrder(ctBehavior, finalMatcher);
            }
        }
    }

    @SpirePatch(
            clz = MapRoomNode.class,
            method = "update"
    )
    public static class FirstRoom
    {
        public static void Raw(CtBehavior ctBehavior) throws CannotCompileException {
            ctBehavior.instrument(new ree());
        }

        public static int isValidFirstNode(MapRoomNode n)
        {
            if (EvilModeCharacterSelect.evilMode)
            {
                if (n.y == FlipMap.EverythingIsWrong.startY)
                {
                    return 0;
                }
                else if (n.y == 0)
                {
                    return 1;
                }
            }
            return n.y;
        }

        private static class ree extends ExprEditor
        {
            int count = 0;

            @Override
            public void edit(FieldAccess f) throws CannotCompileException {
                if (f.getFieldName().equals("y") && f.getClassName().equals(MapRoomNode.class.getName()))
                {
                    ++count;

                    if (count == 4 || count == 6)
                    {
                        f.replace("{" +
                                "$_ = " + FirstRoom.class.getName() + ".isValidFirstNode($0);" +
                                "}");
                    }
                }
            }
        }
    }

    @SpirePatch(
            clz = MapRoomNode.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class PositionAdjustment
    {
        private static final float EVIL_ADJUST = 400.0f * Settings.scale;

        @SpirePostfixPatch
        public static void upYouGo(MapRoomNode __instance, int x, int y)
        {
            if (EvilModeCharacterSelect.evilMode)
            {
                __instance.offsetY += EVIL_ADJUST;
            }
        }
    }

    @SpirePatch(
            clz = MapEdge.class,
            method = SpirePatch.CONSTRUCTOR,
            paramtypez = { int.class, int.class, float.class, float.class, int.class, int.class, float.class, float.class, boolean.class }
    )
    public static class JustATeensyNudge
    {
        private static final float bop = 13.666f * Settings.scale;

        @SpireInsertPatch(
                locator = Locator.class,
                localvars = { "tmpDY", "tmpSY" }
        )
        public static void boop(MapEdge __instance, int srcX, int srcY, float srcOffsetX, float srcOffsetY, int dstX, int dstY, float dstOffsetX, float dstOffsetY, boolean isBoss, @ByRef float[] tmpSY, @ByRef float[] tmpDY)
        {
            if (EvilModeCharacterSelect.evilMode)
            {
                tmpDY[0] += bop;
                tmpSY[0] += bop;
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher finalMatcher = new Matcher.NewExprMatcher(Vector2.class);
                return LineFinder.findInOrder(ctBehavior, finalMatcher);
            }
        }
    }

    @SpirePatch(
            clz = DungeonMapScreen.class,
            method = "open"
    )
    public static class YouAreScrollingTheWrongWay
    {
        private static final float ADJUST = 50.0f * Settings.scale;
        private static final float MORE_ADJUST = 250.0f * Settings.scale;

        @SpireInsertPatch(
                locator = Locator.class,
                localvars = { "targetOffsetY" }
        )
        public static void doot(DungeonMapScreen __instance, boolean scrolly, float ___mapScrollUpperLimit, @ByRef float[] targetOffsetY)
        {
            if (EvilModeCharacterSelect.evilMode)
            {
                if (scrolly)
                {
                    WhyDoYouNotPayAnyRealAttentionToTheVariables.lower = targetOffsetY[0] + ADJUST;
                    WhyDoYouNotPayAnyRealAttentionToTheVariables.upper = targetOffsetY[0] = DungeonMapScreen.offsetY + MORE_ADJUST;
                    DungeonMapScreen.offsetY = WhyDoYouNotPayAnyRealAttentionToTheVariables.lower;
                }
                else
                {
                    if (!AbstractDungeon.firstRoomChosen)
                    {
                        targetOffsetY[0] = DungeonMapScreen.offsetY = ___mapScrollUpperLimit;
                    }
                    /*else
                    {
                        targetOffsetY[0] += MORE_ADJUST;
                        DungeonMapScreen.offsetY += MORE_ADJUST;
                    }*/
                }
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(DynamicBanner.class, "hide");
                return LineFinder.findInOrder(ctBehavior, finalMatcher);
            }
        }
    }

    @SpirePatch(
            clz = DungeonMapScreen.class,
            method = "updateAnimation"
    )
    public static class WhyDoYouNotPayAnyRealAttentionToTheVariables
    {
        public static float upper = 0.0f;
        public static float lower = 0.0f;

        @SpirePrefixPatch
        public static SpireReturn<?> WellThenIHaveToDoItMyself(DungeonMapScreen __instance, float ___targetOffsetY, @ByRef float[] ___scrollWaitTimer)
        {
            if (EvilModeCharacterSelect.evilMode)
            {
                ___scrollWaitTimer[0] -= Gdx.graphics.getDeltaTime();
                if (___scrollWaitTimer[0] < 0.0F) {
                    DungeonMapScreen.offsetY = MathUtils.lerp(DungeonMapScreen.offsetY, ___targetOffsetY, Gdx.graphics.getDeltaTime() * 12.0F);
                } else if (___scrollWaitTimer[0] < 3.0F) {
                    DungeonMapScreen.offsetY = Interpolation.exp10.apply(upper, lower, ___scrollWaitTimer[0] / 3.0F);
                }

                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = DungeonMap.class,
            method = "update"
    )
    public static class BossStuff
    {
        public static float BOSS_OFFSET = 60.0f * Settings.scale;
        public static float BOSS_HB_OFFSET = 256.0F * Settings.scale;

        public static void Raw(CtBehavior ctBehavior) throws CannotCompileException {
            ctBehavior.instrument(new BossRepositionifier());
            ctBehavior.instrument(new BossVisitifier());
        }

        private static class BossRepositionifier extends ExprEditor
        {
            @Override
            public void edit(MethodCall m) throws CannotCompileException {
                if (m.getMethodName().equals("move") && m.getClassName().equals(Hitbox.class.getName()))
                {
                    m.replace("{" +
                            "$proceed($1, " + EvilModeCharacterSelect.class.getName() + ".evilMode ? " +
                            DungeonMapScreen.class.getName() + ".offsetY + " + BossStuff.class.getName() + ".BOSS_OFFSET + " + BossStuff.class.getName() + ".BOSS_HB_OFFSET : " +
                            "$2);" +
                            "}");
                }
            }
        }

        //i don't really need to separate this but I'm doing it anyways
        private static class BossVisitifier extends ExprEditor
        {
            private int count = 0;
            @Override
            public void edit(FieldAccess f) throws CannotCompileException {
                if (f.getFieldName().equals("y") && f.getClassName().equals(MapRoomNode.class.getName()))
                {
                    ++count;

                    if (count == 1)
                    {
                        f.replace("{" +
                                "$_ = " + BossStuff.class.getName() + ".compatibleGetARealY($0);" +
                                "}");
                    }
                    else if (count == 2)
                    {
                        f.replace("{" +
                                "$_ = " + BossStuff.class.getName() + ".getARealY($0);" +
                                "}");
                    }
                }
            }
        }

        public static int compatibleGetARealY(MapRoomNode mmmmmm)
        {
            if (Loader.isModLoaded("actlikeit"))
            {
                return MapCompatiblity.actLikeItCheck();
            }
            else if (EvilModeCharacterSelect.evilMode)
            {
                if (mmmmmm.y == 0)
                    return AbstractDungeon.id.equals(TheEnding.ID) ? 2 : 14;
                return 0;
            }
            return mmmmmm.y;
        }

        public static int getARealY(MapRoomNode mmmmmm)
        {
            if (EvilModeCharacterSelect.evilMode)
            {
                if (mmmmmm.y == 0)
                    return AbstractDungeon.id.equals(TheEnding.ID) ? 2 : 14;
                return 0;
            }
            return mmmmmm.y;
        }
    }

    @SpirePatch(
            clz = DungeonMap.class,
            method = "renderBossIcon"
    )
    public static class BossIconPosition
    {
        public static void Raw(CtBehavior ctBehavior) throws CannotCompileException {
            ctBehavior.instrument(new BossIconRepositionifier());
        }

        private static class BossIconRepositionifier extends ExprEditor
        {
            @Override
            public void edit(MethodCall m) throws CannotCompileException {
                if (m.getMethodName().equals("draw") && m.getClassName().equals(SpriteBatch.class.getName()))
                {
                    m.replace("{" +
                            "$proceed($1, $2, " + EvilModeCharacterSelect.class.getName() + ".evilMode ? " +
                            DungeonMapScreen.class.getName() + ".offsetY + " + BossStuff.class.getName() + ".BOSS_OFFSET : " +
                            "$3, $4, $5);" +
                            "}");
                }
            }
        }
    }
}