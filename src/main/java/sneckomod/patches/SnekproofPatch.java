package sneckomod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.powers.ConfusionPower;
import sneckomod.SneckoMod;

@SpirePatch(
        clz = ConfusionPower.class,
        method = "onCardDraw"
)
public class SnekproofPatch {
    public static SpireReturn Prefix(ConfusionPower __instance, AbstractCard drawnCard) {
        if (drawnCard.hasTag(SneckoMod.SNEKPROOF)) return SpireReturn.Return(null);
        return SpireReturn.Continue();
    }
}