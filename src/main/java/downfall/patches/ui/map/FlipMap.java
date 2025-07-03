package downfall.patches.ui.map;

import chronoMods.TogetherManager;
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
import com.megacrit.cardcrawl.map.DungeonMap;
import com.megacrit.cardcrawl.map.MapEdge;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.map.RoomTypeAssigner;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.screens.DungeonMapScreen;
import com.megacrit.cardcrawl.ui.buttons.DynamicBanner;
import com.megacrit.cardcrawl.ui.panels.TopPanel;
import downfall.downfallMod;
import downfall.patches.EvilModeCharacterSelect;
import downfall.patches.actlikeit.MapCompatiblity;
import downfall.util.ModCrossoverHelperClass;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;
import javassist.expr.MethodCall;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.util.*;

public class FlipMap {
    public static final Logger logger = LogManager.getLogger(FlipMap.class.getName());

    private static final HashSet<String> invalidActs;

    static {
        invalidActs = new HashSet<>();
        invalidActs.add("paleoftheancients:PaleOfTheAncients");
        invalidActs.add("infinite-spire:TheVoid");
    }

    @SpirePatch(
            clz = AbstractDungeon.class,
            method = "generateMap"
    )
    public static class StandardMapFlipper {
        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(RoomTypeAssigner.class, "distributeRoomsAcrossMap");
                return LineFinder.findInOrder(ctBehavior, finalMatcher);
            }
        }

        @SpireInsertPatch(
                locator = Locator.class
        )
        public static void Insert() {
            MapFlipper.flipflipflipflipflip();
        }
    }

    @SpirePatch(
            clz = TheEnding.class,
            method = "generateSpecialMap"
    )
    public static class EndingMapFlipper {
        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(Logger.class, "info");
                return LineFinder.findInOrder(ctBehavior, finalMatcher);
            }
        }

        @SpireInsertPatch(
                locator = Locator.class
        )
        public static void Insert() {
            MapFlipper.flipflipflipflipflip();
        }
    }

    private static boolean moddedActFourCheck() {
        return (AbstractDungeon.actNum >= 4 && !Settings.isEndless && !AbstractDungeon.id.equals(TheEnding.ID));
    }

    public static class MapFlipper {
        public static int startY = 0;

        public static void flipflipflipflipflip() {
            if (EvilModeCharacterSelect.evilMode && !invalidActs.contains(AbstractDungeon.id) && !moddedActFourCheck()) {
                if (downfallMod.normalMapLayout) {
                    if (!AbstractDungeon.id.equals(TheEnding.ID)) {
                        flipCampfire();
                    }
                    flip(AbstractDungeon.map);


                } else {
                    flip(AbstractDungeon.map);
                }
            }
        }

        public static void connectNode(MapRoomNode src, MapRoomNode dst) {
            src.addEdge(new MapEdge(src.x, src.y, src.offsetX, src.offsetY, dst.x, dst.y, dst.offsetX, dst.offsetY, false));
        }


        public static ArrayList<ArrayList<MapRoomNode>> SWFAct4Override() {

            ArrayList<ArrayList<MapRoomNode>> map = new ArrayList();

            MapRoomNode courierNode = new MapRoomNode(3, 0);
            courierNode.room = ModCrossoverHelperClass.returnCourierRoom();
            MapRoomNode restNode = new MapRoomNode(3, 1);
            restNode.room = new RestRoom();
            MapRoomNode shopNode = new MapRoomNode(3, 2);
            shopNode.room = new ShopRoom();
            MapRoomNode enemyNode = new MapRoomNode(3, 3);
            enemyNode.room = new MonsterRoomElite();
            MapRoomNode bossNode = new MapRoomNode(3, 4);
            bossNode.room = new MonsterRoomBoss();
            MapRoomNode victoryNode = new MapRoomNode(3, 5);
            victoryNode.room = new TrueVictoryRoom();


            connectNode(courierNode, restNode);
            connectNode(restNode, shopNode);
            connectNode(shopNode, enemyNode);

            enemyNode.addEdge(new MapEdge(enemyNode.x, enemyNode.y, enemyNode.offsetX, enemyNode.offsetY, bossNode.x, bossNode.y, bossNode.offsetX, bossNode.offsetY, false));


            ArrayList<MapRoomNode> row1 = new ArrayList();
            row1.add(new MapRoomNode(0, 0));
            row1.add(new MapRoomNode(1, 0));
            row1.add(new MapRoomNode(2, 0));
            row1.add(courierNode);
            row1.add(new MapRoomNode(4, 0));
            row1.add(new MapRoomNode(5, 0));
            row1.add(new MapRoomNode(6, 0));

            ArrayList<MapRoomNode> row2 = new ArrayList();
            row2.add(new MapRoomNode(0, 1));
            row2.add(new MapRoomNode(1, 1));
            row2.add(new MapRoomNode(2, 1));
            row2.add(restNode);
            row2.add(new MapRoomNode(4, 1));
            row2.add(new MapRoomNode(5, 1));
            row2.add(new MapRoomNode(6, 1));

            ArrayList<MapRoomNode> row3 = new ArrayList();
            row3.add(new MapRoomNode(0, 2));
            row3.add(new MapRoomNode(1, 2));
            row3.add(new MapRoomNode(2, 2));
            row3.add(shopNode);
            row3.add(new MapRoomNode(4, 2));
            row3.add(new MapRoomNode(5, 2));
            row3.add(new MapRoomNode(6, 2));

            ArrayList<MapRoomNode> row4 = new ArrayList();
            row4.add(new MapRoomNode(0, 3));
            row4.add(new MapRoomNode(1, 3));
            row4.add(new MapRoomNode(2, 3));
            row4.add(enemyNode);
            row4.add(new MapRoomNode(4, 3));
            row4.add(new MapRoomNode(5, 3));
            row4.add(new MapRoomNode(6, 3));

            ArrayList<MapRoomNode> row5 = new ArrayList();
            row5.add(new MapRoomNode(0, 4));
            row5.add(new MapRoomNode(1, 4));
            row5.add(new MapRoomNode(2, 4));
            row5.add(bossNode);
            row5.add(new MapRoomNode(4, 4));
            row5.add(new MapRoomNode(5, 4));
            row5.add(new MapRoomNode(6, 4));


            ArrayList<MapRoomNode> row6 = new ArrayList();
            row6.add(new MapRoomNode(0, 5));
            row6.add(new MapRoomNode(1, 5));
            row6.add(new MapRoomNode(2, 5));
            row6.add(victoryNode);
            row6.add(new MapRoomNode(4, 5));
            row6.add(new MapRoomNode(5, 5));
            row6.add(new MapRoomNode(6, 5));


            map.add(row1);
            map.add(row2);
            map.add(row3);
            map.add(row4);
            map.add(row5);
            map.add(row6);

            AbstractDungeon.map.clear();
            AbstractDungeon.map = map;

            return map;
        }


        private static void flipCampfire() {
            ArrayList<ArrayList<MapRoomNode>> map = AbstractDungeon.map;


            assignRowAsRoomType(map.get(0), RestRoom.class);
            assignRowAsRoomType(map.get(map.size() - 1), MonsterRoom.class);

        }


        public static void assignRowAsRoomType(ArrayList<MapRoomNode> row, Class<? extends AbstractRoom> c) {
            for (MapRoomNode n : row) {
                try {
                    n.setRoom(c.newInstance());
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        // Reverses the direction of the edges between map rooms. for example, rooms on floor 2
        // now have edges that end at floor 1 instead of floor 3. floor 1 nodes end at the boss instead
        // of floor 2. doesn't change which floor the player starts at, that's elsewhere.
        private static void flip(ArrayList<ArrayList<MapRoomNode>> map) {

            if (AbstractDungeon.id == "TheEnding") {
                if (Loader.isModLoaded("chronoMods")) {
                    if (TogetherManager.gameMode.equals(TogetherManager.mode.Coop)) {
                        map = SWFAct4Override();
                    }
                }
            }


            startY = 0;

            ArrayList<MapNodeData> edges = new ArrayList<>();

            for (ArrayList<MapRoomNode> row : map) {
                for (MapRoomNode n : row) {
                    if (n.room instanceof MonsterRoomBoss)
                        continue;

                    for (MapEdge e : n.getEdges()) {
                        if (!edgeArrayContains(edges, e)) {
                            if (e.dstY >= 0 && e.dstY < map.size())

                                edges.add(new MapNodeData(e, n, map.get(e.dstY).get(e.dstX)));
                        }
                    }

                    n.getEdges().clear();
                    n.getParents().clear();
                }
            }

            ArrayList<MapRoomNode> finalNodes = new ArrayList<>();

            for (MapNodeData data : edges) {
                if (data.end.room instanceof MonsterRoomBoss)
                    continue;

                if (data.end.y > startY)
                    startY = data.end.y;

                data.end.addEdge(new MapEdge(data.end.x, data.end.y, data.end.offsetX, data.end.offsetY, data.start.x, data.start.y, data.start.offsetX, data.start.offsetY, false));
                data.end.getEdges().sort(MapEdge::compareTo);
                data.start.getParents().add(data.end);
                if (data.start.y == 0)
                    finalNodes.add(data.start);
            }

            for (MapRoomNode n : finalNodes) {
                n.addEdge(new MapEdge(n.x, n.y, n.offsetX, n.offsetY, 3, -1, 0.0F, Settings.MAP_DST_Y * 2, true));
            }
        }

        private static boolean edgeArrayContains(ArrayList<MapNodeData> data, MapEdge e) {
            for (MapNodeData d : data)
                if (d.e.equals(e))
                    return true;

            return false;
        }

        private static class MapNodeData {
            public MapEdge e;
            public MapRoomNode start, end;

            public MapNodeData(MapEdge e, MapRoomNode start, MapRoomNode end) {
                this.e = e;
                this.start = start;
                this.end = end;
            }
        }

    }

    // prevents elite and rest rooms from being created in the first five floors (first meaning floors with Y=10-14)
// only overrides base logic in evilMode
    @SpirePatch(
            clz = RoomTypeAssigner.class,
            method = "ruleAssignableToRow"
    )
    public static class EliteRoomPatch {
        @SpirePrefixPatch
        public static SpireReturn<Boolean> Prefix(MapRoomNode n, AbstractRoom roomToBeSet) {
            if (EvilModeCharacterSelect.evilMode && !invalidActs.contains(AbstractDungeon.id)) {
                List<Class<? extends AbstractRoom>> applicableRooms = Arrays.asList(RestRoom.class, MonsterRoomElite.class);
                List<Class<RestRoom>> applicableRooms2 = Collections.singletonList(RestRoom.class);

                if (n.y >= 10 && applicableRooms.contains(roomToBeSet.getClass())) {
                    return SpireReturn.Return(false);
                }

                if (n.y <= 1 && applicableRooms2.contains(roomToBeSet.getClass())) {
                    return SpireReturn.Return(false);
                }
                return SpireReturn.Return(true);
            }

            return SpireReturn.Continue();
        }
    }

    // Assigns rooms to map nodes in reverse order. since elites are prevented from spawning at the top of the map,
// this allows those elites to try to spawn again further down.
    @SpirePatch(clz = RoomTypeAssigner.class, method = "assignRoomsToNodes")
    public static class AssignRoomsInReverse {
        public static ExprEditor Instrument() {
            return new ExprEditor() {
                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getMethodName().equals("iterator")) {
                        m.replace("if (downfall.patches.EvilModeCharacterSelect.evilMode) {" +
                                "    java.util.ArrayList _tmp = (java.util.ArrayList)$0.clone();" +
                                "    java.util.Collections.reverse(_tmp);" +
                                "    $_ = _tmp.iterator();" +
                                "} else { $_ = $0.iterator(); }");
                    }
                }
            };
        }
    }

    @SpirePatch(clz = EmeraldElite.class, method = "alternate")
    public static class FixNoRestEmeraldElite {
        @SpireInsertPatch(locator = Locator.class, localvars = {"chosenNode"})
        public static void patch(MapRoomNode chosenNode) {
            if (AbstractDungeon.actNum == 3 && EvilModeCharacterSelect.evilMode && !invalidActs.contains(AbstractDungeon.id)) {
                if (!hasRestSite(chosenNode)) {
                    ArrayList<MapRoomNode> cN = getConnectedNodes(chosenNode);
                    logger.info("Found and fixed emerald elite without campfire.");
                    if (!cN.isEmpty()) {
                        for (MapRoomNode n : cN) {
                            if (!(n.room instanceof TreasureRoom)) {
                                n.room = new RestRoom();
                                return;
                            }
                        }
                        getConnectedNodes(cN.get(0)).stream().findAny().ifPresent(m -> m.room = new RestRoom());
                    }
                }
            }
        }

        private static boolean hasRestSite(MapRoomNode origin) {
            ArrayList<MapRoomNode> cN = getConnectedNodes(origin);
            return cN.stream().anyMatch(mn -> mn.room instanceof RestRoom || hasRestSite(mn));
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(Logger.class, "info");
                return LineFinder.findInOrder(ctBehavior, finalMatcher);
            }
        }
    }

    private static ArrayList<MapRoomNode> getConnectedNodes(MapRoomNode origin) {
        ArrayList<MapRoomNode> retVal = new ArrayList<>();
        for (MapEdge mn : origin.getEdges()) {
            if (AbstractDungeon.map.size() >= mn.dstY && mn.dstY >= 0 && AbstractDungeon.map.get(mn.dstY).size() >= mn.dstX) {
                MapRoomNode cN = AbstractDungeon.map.get(mn.dstY).get(mn.dstX);
                if (cN != null) {
                    retVal.add(cN);
                }
            }
        }
        return retVal;
    }

    // when in evilMode, allows the top row of rooms to be chosen as the first room instead of the bottom row
    @SpirePatch(
            clz = MapRoomNode.class,
            method = "update"
    )
    public static class FirstRoom {
        public static void Raw(CtBehavior ctBehavior) throws CannotCompileException {
            ctBehavior.instrument(new ree());
        }

        public static int isValidFirstNode(MapRoomNode n) {
            if (EvilModeCharacterSelect.evilMode && !invalidActs.contains(AbstractDungeon.id)) {
                if (n.y == FlipMap.MapFlipper.startY) {
                    return 0;
                } else if (n.y == 0) {
                    return 1;
                }
            }
            return n.y;
        }

        private static class ree extends ExprEditor {
            int count = 0;

            @Override
            public void edit(FieldAccess f) throws CannotCompileException {
                if (f.getFieldName().equals("y") && f.getClassName().equals(MapRoomNode.class.getName())) {
                    ++count;

                    if (count == 4 || count == 6) {
                        f.replace("{" +
                                "$_ = " + FirstRoom.class.getName() + ".isValidFirstNode($0);" +
                                "}");
                    }
                }
            }
        }
    }

    // Adjusts the positions of all rooms upward to account for the boss room being
// at the bottom of the map instead of the top
    @SpirePatch(
            clz = MapRoomNode.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class PositionAdjustment {
        private static final float EVIL_ADJUST = 400.0f * Settings.scale;

        @SpirePostfixPatch
        public static void upYouGo(MapRoomNode __instance, int x, int y) {
            if (EvilModeCharacterSelect.evilMode && !invalidActs.contains(AbstractDungeon.id)) {
                __instance.offsetY += EVIL_ADJUST;
            }

        }
    }

    @SpirePatch(
            clz = MapEdge.class,
            method = SpirePatch.CONSTRUCTOR,
            paramtypez = {int.class, int.class, float.class, float.class, int.class, int.class, float.class, float.class, boolean.class}
    )
    public static class JustATeensyNudge {
        private static final float bop = 13.666f * Settings.scale;

        @SpireInsertPatch(
                locator = Locator.class,
                localvars = {"tmpDY", "tmpSY"}
        )
        public static void boop(MapEdge __instance, int srcX, int srcY, float srcOffsetX, float srcOffsetY, int dstX, int dstY, float dstOffsetX, float dstOffsetY, boolean isBoss, @ByRef float[] tmpSY, @ByRef float[] tmpDY) {
            if (EvilModeCharacterSelect.evilMode && !invalidActs.contains(AbstractDungeon.id)) {
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
    public static class YouAreScrollingTheWrongWay {
        private static final float ADJUST = 50.0f * Settings.scale;
        private static final float MORE_ADJUST = 250.0f * Settings.scale;

        @SpireInsertPatch(
                locator = Locator.class,
                localvars = {"targetOffsetY"}
        )
        public static void doot(DungeonMapScreen __instance, boolean scrolly, float ___mapScrollUpperLimit, @ByRef float[] targetOffsetY) {
            if (EvilModeCharacterSelect.evilMode && !invalidActs.contains(AbstractDungeon.id)) {
                if (scrolly) {
                    WhyDoYouNotPayAnyRealAttentionToTheVariables.lower = targetOffsetY[0] + ADJUST;
                    WhyDoYouNotPayAnyRealAttentionToTheVariables.upper = targetOffsetY[0] = DungeonMapScreen.offsetY + MORE_ADJUST;
                    DungeonMapScreen.offsetY = WhyDoYouNotPayAnyRealAttentionToTheVariables.lower;
                } else {
                    if (!AbstractDungeon.firstRoomChosen) {
                        targetOffsetY[0] = DungeonMapScreen.offsetY = ___mapScrollUpperLimit;
                    }

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
    public static class WhyDoYouNotPayAnyRealAttentionToTheVariables {
        public static float upper = 0.0f;
        public static float lower = 0.0f;

        @SpirePrefixPatch
        public static SpireReturn<?> WellThenIHaveToDoItMyself(DungeonMapScreen __instance, float ___targetOffsetY, @ByRef float[] ___scrollWaitTimer) {
            if (EvilModeCharacterSelect.evilMode && !invalidActs.contains(AbstractDungeon.id)) {
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

    // moves the boss room's hitbox to the bottom of the map. has a special case for the
// fourth act since the map is shorter.
    @SpirePatch(
            clz = DungeonMap.class,
            method = "update"
    )
    public static class BossStuff {
        public static float BOSS_OFFSET = 60.0f * Settings.scale;
        public static float BOSS_HB_OFFSET = 256.0F * Settings.scale;

        public static void Raw(CtBehavior ctBehavior) throws CannotCompileException {
            ctBehavior.instrument(new BossRepositionifier());
            ctBehavior.instrument(new BossVisitifier());
        }

        private static class BossRepositionifier extends ExprEditor {
            @Override
            public void edit(MethodCall m) throws CannotCompileException {
                if (m.getMethodName().equals("move") && m.getClassName().equals(Hitbox.class.getName())) {
                    m.replace("{" +
                            "$proceed($1, " + EvilModeCharacterSelect.class.getName() + ".evilMode ? " +
                            DungeonMapScreen.class.getName() + ".offsetY + " + BossStuff.class.getName() + ".BOSS_OFFSET + " + BossStuff.class.getName() + ".BOSS_HB_OFFSET : " +
                            "$2);" +
                            "}");
                }
            }
        }

        //i don't really need to separate this but I'm doing it anyways
        private static class BossVisitifier extends ExprEditor {
            private int count = 0;

            @Override
            public void edit(FieldAccess f) throws CannotCompileException {
                if (f.getFieldName().equals("y") && f.getClassName().equals(MapRoomNode.class.getName())) {
                    ++count;

                    if (count == 1) {
                        f.replace("{" +
                                "$_ = " + BossStuff.class.getName() + ".compatibleGetARealY($0);" +
                                "}");
                    } else if (count == 2) {
                        f.replace("{" +
                                "$_ = " + BossStuff.class.getName() + ".getARealY($0);" +
                                "}");
                    }
                }
            }
        }


        public static int compatibleGetARealY(MapRoomNode mmmmmm) {
            if (Loader.isModLoaded("actlikeit")) {
                return MapCompatiblity.actLikeItCheck();
            } else if (EvilModeCharacterSelect.evilMode && !invalidActs.contains(AbstractDungeon.id)) {
                if (mmmmmm.y == 0)
                    return AbstractDungeon.id.equals(TheEnding.ID) ? 2 : 14;
                return 0;
            }
            return mmmmmm.y;
        }

        public static int getARealY(MapRoomNode mmmmmm) {
            if (EvilModeCharacterSelect.evilMode && !invalidActs.contains(AbstractDungeon.id)) {
                if (mmmmmm.y == 0)
                    return AbstractDungeon.id.equals(TheEnding.ID) ? 2 : 14;
                return 0;
            }
            return mmmmmm.y;
        }
    }

    // change the position of the boss room's visuals to match the hitbox, changed previously
    @SpirePatch(
            clz = DungeonMap.class,
            method = "renderBossIcon"
    )
    public static class BossIconPosition {
        public static void Raw(CtBehavior ctBehavior) throws CannotCompileException {
            ctBehavior.instrument(new BossIconRepositionifier());
        }

        private static class BossIconRepositionifier extends ExprEditor {
            @Override
            public void edit(MethodCall m) throws CannotCompileException {
                if (m.getMethodName().equals("draw") && m.getClassName().equals(SpriteBatch.class.getName())) {
                    m.replace("{" +
                            "$proceed($1, $2, " + EvilModeCharacterSelect.class.getName() + ".evilMode ? " +
                            DungeonMapScreen.class.getName() + ".offsetY + " + BossStuff.class.getName() + ".BOSS_OFFSET : " +
                            "$3, $4, $5);" +
                            "}");
                }
            }
        }
    }
    @SpirePatch(
            clz = DungeonMapScreen.class,
            method = "updateControllerInput"
    )
    public static class FixDownfallControllerInput {
        private static Field visibleMapNodesField;
        private static MapRoomNode lastSelectedNode;

        static {
            try {
                visibleMapNodesField = DungeonMapScreen.class.getDeclaredField("visibleMapNodes");
                visibleMapNodesField.setAccessible(true);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }

        @SpireInsertPatch(
                locator = Locator.class,
                localvars = {"nodes", "index", "anyHovered"}
        )
        public static void Insert(DungeonMapScreen __instance, ArrayList<MapRoomNode> nodes, @ByRef int[] index, @ByRef boolean[] anyHovered) {
            if (EvilModeCharacterSelect.evilMode && !invalidActs.contains(AbstractDungeon.id) && !AbstractDungeon.firstRoomChosen) {
                try {
                    @SuppressWarnings("unchecked")
                    ArrayList<MapRoomNode> visibleMapNodes = (ArrayList<MapRoomNode>) visibleMapNodesField.get(__instance);
                    nodes.clear();
                    for (MapRoomNode n : visibleMapNodes) {
                        if (n.y == FlipMap.MapFlipper.startY) {
                            nodes.add(n);
                        }
                    }

                    anyHovered[0] = false;

                    for (int i = 0; i < nodes.size(); i++) {
                        if (nodes.get(i).hb.hovered) {
                            index[0] = i;
                            anyHovered[0] = true;
                            lastSelectedNode = nodes.get(i);
                            break;
                        }
                    }

                    if (!anyHovered[0]) {
                        if (lastSelectedNode != null && nodes.contains(lastSelectedNode)) {
                            index[0] = nodes.indexOf(lastSelectedNode);
                        } else if (!nodes.isEmpty()) {
                            index[0] = nodes.size() / 2;
                            lastSelectedNode = nodes.get(index[0]);
                        }

                        if (!nodes.isEmpty()) {
                            Gdx.input.setCursorPosition((int)nodes.get(index[0]).hb.cX, Settings.HEIGHT - (int)nodes.get(index[0]).hb.cY);
                            __instance.mapNodeHb = nodes.get(index[0]).hb;
                        }
                    }

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(ArrayList.class, "isEmpty");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }
    @SpirePatch(clz = TopPanel.class, method = "update")
    public static class DisableTopPanelHoveringPatch {
        @SpireInsertPatch(locator = Locator.class)
        public static void Insert(TopPanel __instance) {
            if (Settings.isControllerMode && EvilModeCharacterSelect.evilMode && !__instance.selectPotionMode) {
                __instance.goldHb.hovered = false;

                for (AbstractPotion potion : AbstractDungeon.player.potions) {
                    potion.hb.hovered = false;
                }
            }
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(TopPanel.class, "updateButtons");
                return LineFinder.findInOrder(ctBehavior, finalMatcher);
            }
        }
    }

    @SpirePatch(clz = TopPanel.class, method = "updateAscensionHover")
    public static class DisableAscensionHoveringPatch {
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(TopPanel __instance) {
            if (Settings.isControllerMode && EvilModeCharacterSelect.evilMode && !__instance.selectPotionMode) {
                __instance.ascensionHb.hovered = false;
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }
}