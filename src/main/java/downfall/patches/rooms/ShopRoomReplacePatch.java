package downfall.patches.rooms;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.EventHelper;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.map.RoomTypeAssigner;
import com.megacrit.cardcrawl.rooms.*;
import downfall.patches.EvilModeCharacterSelect;
import downfall.rooms.HeartShopRoom;
import javassist.CtBehavior;

import java.util.*;

public class ShopRoomReplacePatch {

    @SpirePatch(
            clz = AbstractDungeon.class,
            method = "generateRoomTypes",
            paramtypez = {ArrayList.class, int.class}
    )
    public static class replaceShopRoomType {
        @SpirePostfixPatch
        public static void Postfix(ArrayList<AbstractRoom> roomList, int availableRoomCount) {

            if (EvilModeCharacterSelect.evilMode) {
                ArrayList<AbstractRoom> shopRooms = new ArrayList<>();
                for (AbstractRoom r : roomList) {
                    if (r instanceof ShopRoom) {
                        shopRooms.add(r);
                    }
                }
                for (AbstractRoom r : shopRooms) {
                    roomList.remove(r);
                    roomList.add(new HeartShopRoom());
                }
                roomList.removeAll(shopRooms);
            }

        }
    }

    @SpirePatch(
            clz = AbstractDungeon.class,
            method = "generateRoom",
            paramtypez = {EventHelper.RoomResult.class}
    )
    public static class replaceRoomGeneration {
        @SpirePrefixPatch
        public static SpireReturn<AbstractRoom> Prefix(AbstractDungeon __instance, EventHelper.RoomResult roomType) {

            if (EvilModeCharacterSelect.evilMode) {
                if (roomType == EventHelper.RoomResult.SHOP) {
                    return SpireReturn.Return(new HeartShopRoom());
                }
            }

            return SpireReturn.Continue();
        }
    }


    @SpirePatch(
            clz = RoomTypeAssigner.class,
            method = "ruleParentMatches",
            paramtypez = {ArrayList.class, AbstractRoom.class}
    )
    public static class replaceRuleParentMatches {
        @SpireInsertPatch(rloc = 5, localvars = {"applicableRooms"})
        public static void patch(ArrayList<MapRoomNode> parents, AbstractRoom roomToBeSet, @ByRef List<Class<? extends AbstractRoom>>[] applicableRooms) {
            if (EvilModeCharacterSelect.evilMode) {
                applicableRooms[0] = new ArrayList<>(applicableRooms[0]);
                applicableRooms[0].add(HeartShopRoom.class);
            }
        }
    }

    @SpirePatch(
            clz = RoomTypeAssigner.class,
            method = "ruleSiblingMatches",
            paramtypez = {ArrayList.class, AbstractRoom.class}
    )
    public static class replaceRuleSiblingMatches {
        @SpireInsertPatch(rloc = 6, localvars = {"applicableRooms"})
        public static void patch(ArrayList<MapRoomNode> parents, AbstractRoom roomToBeSet, @ByRef List<Class<? extends AbstractRoom>>[] applicableRooms) {
            if (EvilModeCharacterSelect.evilMode) {
                applicableRooms[0] = new ArrayList<>(applicableRooms[0]);
                applicableRooms[0].add(HeartShopRoom.class);
            }
        }
    }
}

