package gremlin.patches.eventpatches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.shrines.GremlinMatchGame;
import com.megacrit.cardcrawl.localization.EventStrings;
import downfall.downfallMod;

@SpirePatch(clz = GremlinMatchGame.class, method = "update")
public class MatchGameAfterPatch {
    private static final EventStrings strings = CardCrawlGame.languagePack.getEventString("Gremlin:MatchingGame");
    private static final String TEXT = strings.DESCRIPTIONS[1];

    @SpireInsertPatch(
            rloc = 18
    )
    public static void Insert(GremlinMatchGame __instance) {
        if (AbstractDungeon.player.chosenClass.equals(downfallMod.Enums.GREMLIN)) {
            __instance.imageEventText.updateBodyText(TEXT);
        }
    }
}
