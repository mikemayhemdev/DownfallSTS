package gremlin.patches.relicpatches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.GremlinMask;
import gremlin.characters.GremlinCharacter;

@SpirePatch(
        clz= GremlinMask.class,
        method="getUpdatedDescription"
)
public class GremlinMaskDescriptionPatch {
    private static final RelicStrings strings = CardCrawlGame.languagePack.getRelicStrings("Gremlin:GremlinMask");

    public static SpireReturn<String> Prefix(GremlinMask __instance){
        if(AbstractDungeon.player instanceof GremlinCharacter){
            return SpireReturn.Return(strings.DESCRIPTIONS[0]);
        }
        return SpireReturn.Continue();
    }
}
