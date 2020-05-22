package downfall.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.rooms.ShopRoom;
import downfall.rooms.HeartShopRoom;

import java.util.ArrayList;

@SpirePatch(clz = AbstractDungeon.class, method = "generateMap")
public class SwapOutShop {
    public static void Postfix() {
        if (AbstractDungeon.actNum == 4 && !Settings.isEndless && EvilModeCharacterSelect.evilMode) {
            for (ArrayList<MapRoomNode> nodes : AbstractDungeon.map) {
                for (MapRoomNode node : nodes) {
                    if (node.getRoom() instanceof ShopRoom) {
                        node.setRoom(new HeartShopRoom());
                    }
                }
            }
        }
    }
}