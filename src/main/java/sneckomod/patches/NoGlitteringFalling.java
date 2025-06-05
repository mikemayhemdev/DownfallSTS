//package sneckomod.patches;
//
//import champ.ChampChar;
//import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.SoulboundField;
//import com.evacipated.cardcrawl.modthespire.lib.*;
//import com.megacrit.cardcrawl.cards.AbstractCard;
//import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
//import com.megacrit.cardcrawl.events.beyond.Falling;
//import com.megacrit.cardcrawl.events.city.Ghosts;
//import com.megacrit.cardcrawl.random.Random;
//import downfall.events.CouncilOfGhosts_Evil;
//import gremlin.characters.GremlinCharacter;
//import javassist.CtBehavior;
//import sneckomod.cards.GlitteringGambit;
//import theHexaghost.events.HexaFalling;
//
//import java.util.ArrayList;
//
//import static awakenedOne.AwakenedOneMod.DELVE;
//
//public class NoGlitteringFalling {
//
//    @SpirePatch(
//            clz = AbstractDungeon.class,
//            method = "getEvent"
//    )
//    public static class EventSpawn {
//
//        @SpireInsertPatch(
//                locator = Locator.class,
//                localvars = {"tmp"}
//        )
//
//        //TODO: Replace this with an actual patch for getPurgeableCards. I don't know why SoulboundPatch from StSLib isn't catching it...
//        //todo: okay so it turns out that through the power of jank this was "fixed"
//        public static void Insert(Random rng, ArrayList<String> tmp) {
//            boolean hasSoulboundNotCurse = false;
//
//            for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
//                if (c instanceof GlitteringGambit) {
//                    hasSoulboundNotCurse = true;
//                }
//            }
//
//            //out of sight, out of mind?
//            if (hasSoulboundNotCurse) {
//               tmp.remove(Falling.ID);
//               tmp.remove(HexaFalling.ID);
//            }
//        }
//
//        private static class Locator extends SpireInsertLocator {
//            @Override
//            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
//                Matcher finalMatcher = new Matcher.MethodCallMatcher(ArrayList.class, "isEmpty");
//                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
//            }
//        }
//    }
//}