package downfall.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.neow.NeowRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.rooms.TreasureRoomBoss;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;

@SpirePatch(
        clz = AbstractDungeon.class,
        method = "populatePathTaken"
)
public class DebuggingMapLoadCrashFix {

    @SpirePrefixPatch
    public static SpireReturn Prefix(AbstractDungeon __instance, SaveFile save) {
        if (save.current_room.equals(MonsterRoomBoss.class.getName()) ||
                save.current_room.equals(TreasureRoomBoss.class.getName()) ||
                (save.room_y == 15) && (save.room_x == -1 )) {
            return SpireReturn.Continue();
        }

        if (save.room_x < 0 || save.room_y < 0) {
            save.current_room = NeowRoom.class.getName();
        }
        return SpireReturn.Continue();
    }
}
