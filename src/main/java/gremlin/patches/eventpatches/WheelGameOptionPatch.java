package gremlin.patches.eventpatches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.shrines.GremlinWheelGame;
import com.megacrit.cardcrawl.localization.EventStrings;
import gremlin.characters.GremlinCharacter;

@SpirePatch(clz = GremlinWheelGame.class, method = "preApplyResult")
public class WheelGameOptionPatch
{
    private static final EventStrings strings = CardCrawlGame.languagePack.getEventString("Gremlin:WheelGame");
    private static final String TEXT = strings.OPTIONS[0];

    @SpireInsertPatch(
            rloc=24
    )
    public static SpireReturn<Void> Insert(GremlinWheelGame __instance) {
        if (AbstractDungeon.player instanceof GremlinCharacter) {
            __instance.imageEventText.updateBodyText(GremlinWheelGame.DESCRIPTIONS[6]);
            __instance.imageEventText.setDialogOption(TEXT);
            return SpireReturn.Return(null);
        }
        return SpireReturn.Continue();
    }
}
