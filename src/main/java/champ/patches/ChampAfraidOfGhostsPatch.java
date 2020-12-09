package champ.patches;

import champ.ChampChar;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.city.Ghosts;
import com.megacrit.cardcrawl.random.Random;
import javassist.CtBehavior;

import java.util.ArrayList;

public class ChampAfraidOfGhostsPatch {

    @SpirePatch(
            clz = AbstractDungeon.class,
            method = "getEvent"
    )
    public static class EventSpawn {

        @SpireInsertPatch(
                locator = Locator.class,
                localvars = {"tmp"}
        )
        public static void Insert(Random rng, ArrayList<String> tmp) {
            if (AbstractDungeon.player instanceof ChampChar) {
                tmp.remove(Ghosts.ID);
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
}