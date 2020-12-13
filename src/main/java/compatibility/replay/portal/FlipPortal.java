package compatibility.replay.portal;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.map.MapEdge;
import com.megacrit.cardcrawl.map.MapRoomNode;
import downfall.patches.EvilModeCharacterSelect;
import javassist.CtBehavior;

import java.util.ArrayList;
import java.util.Iterator;

@SpirePatch(
        optional = true,
        cls = "replayTheSpire.patches.TeleporterPatches$PickTeleportersPatch",
        method = "Postfix"
)
public class FlipPortal {
    @SpireInsertPatch(
            locator = Locator.class,
            localvars = {
                    "underRoomLeft", "underRoomRight",
                    "roomLeft", "roomRight"
            }
    )
    public static void flipthePORALS(MapRoomNode underRoomLeft, MapRoomNode underRoomRight, MapRoomNode roomLeft, MapRoomNode roomRight)
    {
        if (EvilModeCharacterSelect.evilMode)
        {
            //teleporter left and right each have one edge
            //underRoomLeft and underRoomRight have an edge added which points at the teleporter rooms, which needs to be flipped.

            MapRoomNode teleporterLeft = null, teleporterRight = null;

            Iterator<MapEdge> edges = underRoomLeft.getEdges().iterator();
            while (edges.hasNext())
            {
                MapEdge next = edges.next();

                MapRoomNode nextNode = getDestination(next);
                if (nextNode != null && nextNode.getRoom().getClass().getSimpleName().equals("TeleportRoom"))
                {
                    edges.remove();
                    teleporterLeft = nextNode;
                    break;
                }
            }

            edges = underRoomRight.getEdges().iterator();
            while (edges.hasNext())
            {
                MapEdge next = edges.next();

                MapRoomNode nextNode = getDestination(next);
                if (nextNode != null && nextNode.getRoom().getClass().getSimpleName().equals("TeleportRoom"))
                {
                    edges.remove();
                    teleporterRight = nextNode;
                    break;
                }
            }

            if (teleporterLeft == null || teleporterRight == null)
                return;

            teleporterLeft.getEdges().clear();
            teleporterRight.getEdges().clear();

            roomLeft.addEdge(new MapEdge(roomLeft.x, roomLeft.y, roomLeft.offsetX, roomLeft.offsetY, teleporterLeft.x, teleporterLeft.y, teleporterLeft.offsetX, teleporterLeft.offsetY, false));
            roomRight.addEdge(new MapEdge(roomRight.x, roomRight.y, roomRight.offsetX, roomRight.offsetY, teleporterRight.x, teleporterRight.y, teleporterRight.offsetX, teleporterRight.offsetY, false));

            teleporterLeft.addEdge(new MapEdge(teleporterLeft.x, teleporterLeft.y, teleporterLeft.offsetX, teleporterLeft.offsetY, underRoomLeft.x, underRoomLeft.y, underRoomLeft.offsetX, underRoomLeft.offsetY, false));
            teleporterRight.addEdge(new MapEdge(teleporterRight.x, teleporterRight.y, teleporterRight.offsetX, teleporterRight.offsetY, underRoomRight.x, underRoomRight.y, underRoomRight.offsetX, underRoomRight.offsetY, false));
        }
    }

    private static MapRoomNode getDestination(MapEdge edge)
    {
        try {
            ArrayList<MapRoomNode> row = CardCrawlGame.dungeon.getMap().get(edge.dstY);

            for (MapRoomNode n : row)
            {
                if (n.x == edge.dstX && n.getRoom() != null)
                {
                    return n;
                }
            }
            return null;
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    private static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "hasRelic");
            return LineFinder.findInOrder(ctBehavior, finalMatcher);
        }
    }
}
