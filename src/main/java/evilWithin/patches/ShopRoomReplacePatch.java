package evilWithin.patches;

import basemod.CustomCharacterSelectScreen;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.EventHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.map.DungeonMap;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.map.RoomTypeAssigner;
import com.megacrit.cardcrawl.rooms.*;
import evilWithin.rooms.HeartShopRoom;

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
                } else {
                    return SpireReturn.Continue();
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
        @SpirePrefixPatch
        public static SpireReturn<Boolean> Prefix(ArrayList<MapRoomNode> parents, AbstractRoom roomToBeSet) {

            if (EvilModeCharacterSelect.evilMode) {
                List<Class<? extends AbstractRoom>> applicableRooms = Arrays.asList(RestRoom.class, TreasureRoom.class, HeartShopRoom.class, MonsterRoomElite.class);
                Iterator var3 = parents.iterator();

                AbstractRoom parentRoom;
                do {
                    if (!var3.hasNext()) {
                        return SpireReturn.Return(false);
                    }

                    MapRoomNode parentNode = (MapRoomNode)var3.next();
                    parentRoom = parentNode.getRoom();
                } while(parentRoom == null || !applicableRooms.contains(roomToBeSet.getClass()) || !roomToBeSet.getClass().equals(parentRoom.getClass()));

                return SpireReturn.Continue();

            }



            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = RoomTypeAssigner.class,
            method = "ruleSiblingMatches",
            paramtypez = {ArrayList.class, AbstractRoom.class}
    )
    public static class replaceRuleSiblingMatches {
        @SpirePrefixPatch
        public static SpireReturn<Boolean> Prefix(ArrayList<MapRoomNode> siblings, AbstractRoom roomToBeSet) {

            if (EvilModeCharacterSelect.evilMode) {
                List<Class<? extends AbstractRoom>> applicableRooms = Arrays.asList(RestRoom.class, MonsterRoom.class, EventRoom.class, MonsterRoomElite.class, HeartShopRoom.class);
                Iterator var3 = siblings.iterator();

                MapRoomNode siblingNode;
                do {
                    if (!var3.hasNext()) {
                        return SpireReturn.Return(false);
                    }

                    siblingNode = (MapRoomNode)var3.next();
                } while(siblingNode.getRoom() == null || !applicableRooms.contains(roomToBeSet.getClass()) || !roomToBeSet.getClass().equals(siblingNode.getRoom().getClass()));

                return SpireReturn.Return(true);

            }


            return SpireReturn.Continue();
        }
    }
}

