package downfall.util;

import chronoMods.coop.CoopCourierRoom;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class ModCrossoverHelperClass {
    public static AbstractRoom returnCourierRoom() {
        return new CoopCourierRoom();
    }
}
