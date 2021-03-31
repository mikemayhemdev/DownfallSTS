package gremlin.patches.eventpatches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.shrines.GremlinWheelGame;
import com.megacrit.cardcrawl.localization.EventStrings;
import gremlin.characters.GremlinCharacter;

@SpirePatch(clz = GremlinWheelGame.class, method = "applyResult")
public class WheelGameResultPatch
{
    private static final EventStrings strings = CardCrawlGame.languagePack.getEventString("Gremlin:WheelGame");
    private static final String TEXT = strings.DESCRIPTIONS[0];

    public static boolean hasReward = false;

    @SpireInsertPatch(
            rloc=44
    )
    public static SpireReturn<Void> Insert(GremlinWheelGame __instance) {
        if (AbstractDungeon.player instanceof GremlinCharacter) {
            __instance.imageEventText.updateBodyText(TEXT);
            CardCrawlGame.sound.play("ATTACK_DAGGER_6");
            CardCrawlGame.sound.play("BLOOD_SPLAT");
            hasReward = true;
            AbstractEvent.logMetric("Wheel of Change", "Gremlins");
            return SpireReturn.Return(null);
        }
        return SpireReturn.Continue();
    }
}

