package gremlin.patches.eventpatches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.shrines.GremlinMatchGame;
import com.megacrit.cardcrawl.localization.EventStrings;
import gremlin.characters.GremlinCharacter;

@SpirePatch(clz = GremlinMatchGame.class, method = "buttonEffect")
public class MatchGameRulesPatch
{
    private static final EventStrings strings = CardCrawlGame.languagePack.getEventString("Gremlin:MatchingGame");
    private static final String TEXT = strings.DESCRIPTIONS[0];

    @SpireInsertPatch(
            rloc=5
    )
    public static void Insert(GremlinMatchGame __instance, int buttonPressed) {
        if (AbstractDungeon.player instanceof GremlinCharacter) {
            __instance.imageEventText.updateBodyText(TEXT);
        }
    }
}
