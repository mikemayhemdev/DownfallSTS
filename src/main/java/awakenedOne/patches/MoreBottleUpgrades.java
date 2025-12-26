package awakenedOne.patches;

import automaton.relics.BottledCode;
import awakenedOne.relics.MoonTalisman;
import champ.relics.SignatureFinisher;
import collector.relics.BottledCollectible;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import guardian.relics.BottledAnomaly;
import guardian.relics.BottledStasis;
import guardian.relics.StasisEgg;
import sneckomod.relics.D8;


//todo: make not jank
public class MoreBottleUpgrades {
    @SpirePatch(clz = AbstractPlayer.class, method = "bottledCardUpgradeCheck")
    public static class BottledCardUpgradeCheckPatch {
        @SpirePostfixPatch
        public static void Postfix(AbstractPlayer __instance, AbstractCard c) {

            if (__instance.hasRelic(SignatureFinisher.ID)) {
                SignatureFinisher a = (SignatureFinisher) __instance.getRelic(SignatureFinisher.ID);
                if (a.card.uuid != null) {
                    if (a.card.uuid == c.uuid) {
                        ((SignatureFinisher) __instance.getRelic(SignatureFinisher.ID)).setDescriptionAfterLoading();
                    }
                }
            }

            if (__instance.hasRelic(BottledStasis.ID)) {
                BottledStasis b = (BottledStasis) __instance.getRelic(BottledStasis.ID);
                if (b.card.uuid != null) {
                    if (b.card.uuid == c.uuid) {
                        ((BottledStasis) __instance.getRelic(BottledStasis.ID)).setDescriptionAfterLoading();
                    }
                }
            }

            if (__instance.hasRelic(BottledAnomaly.ID)) {
                BottledAnomaly crelic = (BottledAnomaly) __instance.getRelic(BottledAnomaly.ID);
                if (crelic.card.uuid != null) {
                    if (crelic.card.uuid == c.uuid) {
                        ((BottledAnomaly) __instance.getRelic(BottledAnomaly.ID)).setDescriptionAfterLoading();
                    }
                }
            }

            if (__instance.hasRelic(MoonTalisman.ID)) {
                MoonTalisman e = (MoonTalisman) __instance.getRelic(MoonTalisman.ID);
                if (e.card.uuid != null) {
                    if (e.card.uuid == c.uuid) {
                        ((MoonTalisman) __instance.getRelic(MoonTalisman.ID)).setDescriptionAfterLoading();
                    }
                }
            }

            if (__instance.hasRelic(BottledCollectible.ID)) {
                BottledCollectible f = (BottledCollectible) __instance.getRelic(BottledCollectible.ID);
                if (f.card.uuid != null) {
                    if (f.card.uuid == c.uuid) {
                        ((BottledCollectible) __instance.getRelic(BottledCollectible.ID)).setDescriptionAfterLoading();
                    }
                }
            }

            if (__instance.hasRelic(D8.ID)) {
                D8 g = (D8) __instance.getRelic(D8.ID);
                if (g.card.uuid != null) {
                    if (g.card.uuid == c.uuid) {
                        ((D8) __instance.getRelic(D8.ID)).setDescriptionAfterLoading();
                    }
                }
            }

            if (__instance.hasRelic(BottledCode.ID)) {
                BottledCode h = (BottledCode) __instance.getRelic(BottledCode.ID);
                if (h.card.uuid != null) {
                    if (h.card.uuid == c.uuid) {
                        ((BottledCode) __instance.getRelic(BottledCode.ID)).setDescriptionAfterLoading();
                    }
                }
            }

            if (__instance.hasRelic(StasisEgg.ID)) {
                StasisEgg i = (StasisEgg) __instance.getRelic(StasisEgg.ID);
                if (i.card.uuid != null) {
                    if (i.card.uuid == c.uuid) {
                        ((BottledCode) __instance.getRelic(BottledCode.ID)).setDescriptionAfterLoading();
                    }
                }
            }

        }
    }
}