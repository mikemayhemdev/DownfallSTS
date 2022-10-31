package gremlin.patches.relicpatches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.Pear;
import gremlin.characters.GremlinCharacter;

@SpirePatch(
        clz= Pear.class,
        method="getUpdatedDescription"
)
public class PearDescriptionPatch {
    private static final RelicStrings strings = CardCrawlGame.languagePack.getRelicStrings("Gremlin:PatchFixes");

    public static SpireReturn<String> Prefix(Pear __instance){
        if(AbstractDungeon.player instanceof GremlinCharacter){
            return SpireReturn.Return(strings.DESCRIPTIONS[0] + 2 + LocalizedStrings.PERIOD);
        }
        return SpireReturn.Continue();
    }
}