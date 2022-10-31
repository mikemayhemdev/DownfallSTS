package gremlin.patches.relicpatches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.TinyHouse;
import gremlin.characters.GremlinCharacter;

@SpirePatch(
        clz= TinyHouse.class,
        method="getUpdatedDescription"
)
public class TinyHouseDescriptionPatch {
    private static final RelicStrings strings = CardCrawlGame.languagePack.getRelicStrings("Gremlin:PatchFixes");

    public static SpireReturn<String> Prefix(TinyHouse __instance){
        if(AbstractDungeon.player instanceof GremlinCharacter){
            return SpireReturn.Return(__instance.DESCRIPTIONS[0] + 50 + strings.DESCRIPTIONS[2] + 1 + __instance.DESCRIPTIONS[2]);
        }
        return SpireReturn.Continue();
    }
}