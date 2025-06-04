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
            SignatureFinisher a = (SignatureFinisher) AbstractDungeon.player.getRelic(SignatureFinisher.ID);
            __result.group.remove(a.card);
        }

        if (AbstractDungeon.player.hasRelic(BottledStasis.ID)) {
            BottledStasis b = (BottledStasis) AbstractDungeon.player.getRelic(BottledStasis.ID);
            __result.group.remove(b.card);
        }

        if (AbstractDungeon.player.hasRelic(BottledAnomaly.ID)) {
            BottledAnomaly crelic = (BottledAnomaly) AbstractDungeon.player.getRelic(BottledAnomaly.ID);
            __result.group.remove(crelic.card);
        }

        if (AbstractDungeon.player.hasRelic(MoonTalisman.ID)) {
            MoonTalisman d = (MoonTalisman) AbstractDungeon.player.getRelic(MoonTalisman.ID);
            __result.group.remove(d.card);
        }

        if (AbstractDungeon.player.hasRelic(BottledCollectible.ID)) {
            BottledCollectible e = (BottledCollectible) AbstractDungeon.player.getRelic(BottledCollectible.ID);
            __result.group.remove(e.card);
        }

        if (AbstractDungeon.player.hasRelic(D8.ID)) {
            D8 f = (D8) AbstractDungeon.player.getRelic(D8.ID);
            __result.group.remove(f.card);
        }

        if (AbstractDungeon.player.hasRelic(BottledCode.ID)) {
            BottledCode g = (BottledCode) AbstractDungeon.player.getRelic(BottledCode.ID);
                __result.group.remove(g.card);
        }

        if (AbstractDungeon.player.hasRelic(StasisEgg.ID)) {
            StasisEgg s = (StasisEgg) AbstractDungeon.player.getRelic(StasisEgg.ID);
            __result.group.remove(s.card);
        }

        return __result;
    }
}
