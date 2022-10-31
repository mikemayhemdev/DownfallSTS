package sneckomod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import sneckomod.relics.SneckoBoss;
import sneckomod.relics.SneckoCommon;

@SpirePatch(
        clz = AbstractDungeon.class,
        method = "initializeCardPools"

)
public class SnekCardPoolPatch {
    @SpirePostfixPatch
    public static void Postfix(AbstractDungeon __instance) {
        AbstractPlayer player = __instance.player;
        if(player.hasRelic(SneckoCommon.ID) || player.hasRelic(SneckoBoss.ID)) {
            SneckoBoss.updateCardPools();
        }
    }
}
