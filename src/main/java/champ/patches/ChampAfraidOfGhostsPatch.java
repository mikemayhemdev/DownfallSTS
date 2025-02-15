package champ.patches;

import champ.ChampChar;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.city.Ghosts;
import com.megacrit.cardcrawl.random.Random;
import downfall.events.CouncilOfGhosts_Evil;
import gremlin.characters.GremlinCharacter;
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
            if (AbstractDungeon.player instanceof ChampChar || AbstractDungeon.player instanceof GremlinCharacter) {
            //(AbstractDungeon.player instanceof ChampChar || AbstractDungeon.player instanceof GremlinCharacter) {
                //to-do: figure out why this is here
                //I've decided this is actually really funny so I'm keeping it lol, also Gremlins but because of the 50% hp thing not working
                tmp.remove(Ghosts.ID);
                tmp.remove(CouncilOfGhosts_Evil.ID);
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