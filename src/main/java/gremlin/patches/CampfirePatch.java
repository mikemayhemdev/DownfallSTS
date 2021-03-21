package gremlin.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import gremlin.characters.GremlinCharacter;
import gremlin.relics.GremlinGravestone;
import gremlin.ui.campfire.ResurrectOption;
import javassist.CtBehavior;

import java.util.ArrayList;

@SpirePatch(cls = "com.megacrit.cardcrawl.rooms.CampfireUI", method = "initializeButtons")
public class CampfirePatch
{

    @SpireInsertPatch(locator = Locator.class)
    public static void patch(CampfireUI __instance, ArrayList<AbstractCampfireOption> ___buttons) {
        if (AbstractDungeon.player instanceof GremlinCharacter) {
            if (AbstractDungeon.player.hasRelic(GremlinGravestone.ID)) {
                ___buttons.add(new ResurrectOption());
            }
        }
    }

    public static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "relics");
            return LineFinder.findInOrder(ctBehavior, finalMatcher);
        }
    }
}