package awakenedOne.patches;

import automaton.relics.BottledCode;
import awakenedOne.relics.MoonTalisman;
import champ.relics.SignatureFinisher;
import collector.relics.BottledCollectible;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import guardian.relics.BottledAnomaly;
import guardian.relics.BottledStasis;
import guardian.relics.StasisEgg;
import sneckomod.relics.D8;

public class MoreBottleUpgrades {
    @SpirePatch(clz = AbstractPlayer.class, method = "bottledCardUpgradeCheck")
    public static class BottledCardUpgradeCheckPatch {
        @SpirePostfixPatch
            public static void Postfix(AbstractCard c) {

            if (AbstractDungeon.player.hasRelic(SignatureFinisher.ID)) {
                SignatureFinisher s = (SignatureFinisher) AbstractDungeon.player.getRelic(SignatureFinisher.ID);
                if (s.card.uuid == c.uuid) {
                    ((SignatureFinisher) AbstractDungeon.player.getRelic(SignatureFinisher.ID)).setDescriptionAfterLoading();
                }
            }

            if (AbstractDungeon.player.hasRelic(BottledStasis.ID)) {
                BottledStasis s = (BottledStasis) AbstractDungeon.player.getRelic(BottledStasis.ID);
                if (s.card.uuid == c.uuid) {
                    ((BottledStasis) AbstractDungeon.player.getRelic(BottledStasis.ID)).setDescriptionAfterLoading();
                }
            }

            if (AbstractDungeon.player.hasRelic(BottledAnomaly.ID)) {
                BottledAnomaly s = (BottledAnomaly) AbstractDungeon.player.getRelic(BottledAnomaly.ID);
                if (s.card.uuid == c.uuid) {
                    ((BottledAnomaly) AbstractDungeon.player.getRelic(BottledAnomaly.ID)).setDescriptionAfterLoading();
                }
            }

            if (AbstractDungeon.player.hasRelic(MoonTalisman.ID)) {
                MoonTalisman s = (MoonTalisman) AbstractDungeon.player.getRelic(MoonTalisman.ID);
                if (s.card.uuid == c.uuid) {
                    ((MoonTalisman) AbstractDungeon.player.getRelic(MoonTalisman.ID)).setDescriptionAfterLoading();
                }
            }

            if (AbstractDungeon.player.hasRelic(BottledCollectible.ID)) {
                BottledCollectible s = (BottledCollectible) AbstractDungeon.player.getRelic(BottledCollectible.ID);
                if (s.card.uuid == c.uuid) {
                    ((BottledCollectible) AbstractDungeon.player.getRelic(BottledCollectible.ID)).setDescriptionAfterLoading();
                }
            }

            if (AbstractDungeon.player.hasRelic(D8.ID)) {
                D8 s = (D8) AbstractDungeon.player.getRelic(D8.ID);
                if (s.card.uuid == c.uuid) {
                    ((D8) AbstractDungeon.player.getRelic(D8.ID)).setDescriptionAfterLoading();
                }
            }

            if (AbstractDungeon.player.hasRelic(BottledCode.ID)) {
                BottledCode s = (BottledCode) AbstractDungeon.player.getRelic(BottledCode.ID);
                if (s.card.uuid == c.uuid) {
                    ((BottledCode) AbstractDungeon.player.getRelic(BottledCode.ID)).setDescriptionAfterLoading();
                }
            }

            if (AbstractDungeon.player.hasRelic(StasisEgg.ID)) {
                StasisEgg s = (StasisEgg) AbstractDungeon.player.getRelic(StasisEgg.ID);
                if (s.card.uuid == c.uuid) {
                    ((BottledCode) AbstractDungeon.player.getRelic(BottledCode.ID)).setDescriptionAfterLoading();
                }
            }

        }
    }
}