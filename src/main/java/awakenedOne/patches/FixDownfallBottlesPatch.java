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
            for (AbstractCard card : __result.group) {
                if (card.uuid == a.card.uuid) {
                    __result.group.remove(card);
                }
            }
        }

        if (AbstractDungeon.player.hasRelic(BottledStasis.ID)) {
            BottledStasis b = (BottledStasis) AbstractDungeon.player.getRelic(BottledStasis.ID);
            for (AbstractCard card : __result.group) {
                if (card.uuid == b.card.uuid) {
                    __result.group.remove(card);
                }
            }
        }

        if (AbstractDungeon.player.hasRelic(BottledAnomaly.ID)) {
            BottledAnomaly crelic = (BottledAnomaly) AbstractDungeon.player.getRelic(BottledAnomaly.ID);
            for (AbstractCard card : __result.group) {
                if (card.uuid == crelic.card.uuid) {
                    __result.group.remove(card);
                }
            }
        }

        if (AbstractDungeon.player.hasRelic(MoonTalisman.ID)) {
            MoonTalisman d = (MoonTalisman) AbstractDungeon.player.getRelic(MoonTalisman.ID);
            for (AbstractCard card : __result.group) {
                if (card.uuid == d.card.uuid) {
                    __result.group.remove(card);
                }
            }
        }

        if (AbstractDungeon.player.hasRelic(BottledCollectible.ID)) {
            BottledCollectible e = (BottledCollectible) AbstractDungeon.player.getRelic(BottledCollectible.ID);
            for (AbstractCard card : __result.group) {
                if (card.uuid == e.card.uuid) {
                    __result.group.remove(card);
                }
            }
        }

        if (AbstractDungeon.player.hasRelic(D8.ID)) {
            D8 f = (D8) AbstractDungeon.player.getRelic(D8.ID);
            for (AbstractCard card : __result.group) {
                if (card.uuid == f.card.uuid) {
                    __result.group.remove(card);
                }
            }
        }

        if (AbstractDungeon.player.hasRelic(BottledCode.ID)) {
            BottledCode g = (BottledCode) AbstractDungeon.player.getRelic(BottledCode.ID);
            for (AbstractCard card : __result.group) {
                if (card.uuid == g.card.uuid) {
                    __result.group.remove(card);
                }
            }
        }

        if (AbstractDungeon.player.hasRelic(StasisEgg.ID)) {
            StasisEgg s = (StasisEgg) AbstractDungeon.player.getRelic(StasisEgg.ID);
            for (AbstractCard card : __result.group) {
                if (card.uuid == s.card.uuid) {
                    __result.group.remove(card);
                }
            }
        }

        return __result;
    }
}
