package downfall.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.neow.NeowRoom;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import downfall.downfallMod;

import java.util.ArrayList;

@SpirePatch(
        clz = AbstractDungeon.class,
        method = "populatePathTaken"
)
public class DebuggingMapLoadCrashFix {

    @SpirePrefixPatch
    public static SpireReturn Prefix(AbstractDungeon __instance, SaveFile save) {
        if (save.room_x < 0 || save.room_y < 0) {
            save.current_room = NeowRoom.class.getName();
        }
        return SpireReturn.Continue();
    }
}
