package downfall.patches.ui.map;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;
import downfall.patches.EvilModeCharacterSelect;
import downfall.util.TextureLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.mapRng;

@SpirePatch(
        clz = AbstractDungeon.class,
        method = "setEmeraldElite"
)
public class EmeraldElite {
    private static final Logger logger = LogManager.getLogger(EmeraldElite.class.getName());

    @SpirePrefixPatch
    public static SpireReturn<?> alternate()
    {
        if (Settings.isFinalActAvailable && !Settings.hasEmeraldKey && EvilModeCharacterSelect.evilMode) {
            ArrayList<MapRoomNode> eliteNodes = new ArrayList<>();

            for(int i = 0; i < AbstractDungeon.map.size() - 5; ++i) {
                for(int j = 0; j < AbstractDungeon.map.get(i).size(); ++j) {
                    if ((AbstractDungeon.map.get(i)).get(j).room instanceof MonsterRoomElite) {
                        eliteNodes.add(AbstractDungeon.map.get(i).get(j));
                    }
                }
            }

            if (eliteNodes.isEmpty())
            {
                for(int i = 0; i < AbstractDungeon.map.size(); ++i) {
                    for(int j = 0; j < AbstractDungeon.map.get(i).size(); ++j) {
                        if ((AbstractDungeon.map.get(i)).get(j).room instanceof MonsterRoomElite) {
                            eliteNodes.add(AbstractDungeon.map.get(i).get(j));
                        }
                    }
                }
            }

            MapRoomNode chosenNode = eliteNodes.get(mapRng.random(0, eliteNodes.size() - 1));
            chosenNode.hasEmeraldKey = true;
            logger.info("[INFO] Elite nodes identified: " + eliteNodes.size());
            logger.info("[INFO] Emerald Key  placed in: [" + chosenNode.x + "," + chosenNode.y + "]");

            return SpireReturn.Return(null);
        }
        return SpireReturn.Continue();
    }
}
