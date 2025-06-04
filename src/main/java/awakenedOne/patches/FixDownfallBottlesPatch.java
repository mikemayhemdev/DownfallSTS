//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package awakenedOne.patches;

import automaton.relics.BottledCode;
import awakenedOne.relics.MoonTalisman;
import basemod.BaseMod;
import basemod.Pair;
import champ.relics.SignatureFinisher;
import collector.relics.BottledCollectible;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import downfall.downfallMod;
import guardian.relics.BottledAnomaly;
import guardian.relics.BottledStasis;
import guardian.relics.StasisEgg;
import sneckomod.relics.D8;

import java.util.Iterator;
import java.util.function.Predicate;

@SpirePatch(
        clz = CardGroup.class,
        method = "getGroupWithoutBottledCards"
)
public class FixDownfallBottlesPatch {
    public FixDownfallBottlesPatch() {
    }

    public static CardGroup Postfix(CardGroup __result, CardGroup group) {

        if (AbstractDungeon.player.hasRelic(SignatureFinisher.ID)) {
            SignatureFinisher s = (SignatureFinisher) AbstractDungeon.player.getRelic(SignatureFinisher.ID);
            __result.group.remove(s.card);
        }

        if (AbstractDungeon.player.hasRelic(BottledStasis.ID)) {
            BottledStasis s = (BottledStasis) AbstractDungeon.player.getRelic(BottledStasis.ID);
            __result.group.remove(s.card);
        }

        if (AbstractDungeon.player.hasRelic(BottledAnomaly.ID)) {
            BottledAnomaly s = (BottledAnomaly) AbstractDungeon.player.getRelic(BottledAnomaly.ID);
            __result.group.remove(s.card);
        }

        if (AbstractDungeon.player.hasRelic(MoonTalisman.ID)) {
            MoonTalisman s = (MoonTalisman) AbstractDungeon.player.getRelic(MoonTalisman.ID);
            __result.group.remove(s.card);
        }

        if (AbstractDungeon.player.hasRelic(BottledCollectible.ID)) {
            BottledCollectible s = (BottledCollectible) AbstractDungeon.player.getRelic(BottledCollectible.ID);
            __result.group.remove(s.card);
        }

        if (AbstractDungeon.player.hasRelic(D8.ID)) {
            D8 s = (D8) AbstractDungeon.player.getRelic(D8.ID);
            __result.group.remove(s.card);
        }

        if (AbstractDungeon.player.hasRelic(BottledCode.ID)) {
            BottledCode s = (BottledCode) AbstractDungeon.player.getRelic(BottledCode.ID);
                __result.group.remove(s.card);
        }

        if (AbstractDungeon.player.hasRelic(StasisEgg.ID)) {
            StasisEgg s = (StasisEgg) AbstractDungeon.player.getRelic(StasisEgg.ID);
            __result.group.remove(s.card);
        }

        return __result;
    }
}
