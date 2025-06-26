package downfall.patches;

import awakenedOne.relics.MoonTalisman;
import champ.relics.*;
import collector.relics.BottledCollectible;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import guardian.GuardianMod;
import guardian.relics.BottledAnomaly;
import guardian.relics.BottledStasis;
import guardian.relics.GemstoneGun;
import guardian.relics.PickAxe;
import sneckomod.relics.SneckoCommon;

import java.util.ArrayList;
import java.util.Objects;

@SpirePatch(
        clz = AbstractDungeon.class,
        method = "returnRandomScreenlessRelic"
)
public class ReturnRandomScreenlessRelicPatch {
    @SpirePostfixPatch
    public static void Postfix(AbstractDungeon __instance, AbstractRelic relic) {
        boolean uhoh = false;
        ArrayList<String> downfallscreenrelics = new ArrayList();

        //I'm going to assume that this thing isn't going to be used to ask for event or boss relics
        //so I'm not going to include them
        downfallscreenrelics.add(SneckoCommon.ID);
        if (AbstractDungeon.getCurrRoom().eliteTrigger) {
            downfallscreenrelics.add(PickAxe.ID);
        }
        downfallscreenrelics.add(BottledStasis.ID);
        downfallscreenrelics.add(BottledAnomaly.ID);
        downfallscreenrelics.add(GemstoneGun.ID);
        downfallscreenrelics.add(SignatureFinisher.ID);
        downfallscreenrelics.add(BottledCollectible.ID);
        downfallscreenrelics.add(MoonTalisman.ID);


        for (String relics : downfallscreenrelics) {
            if (relics == relic.relicId) {
                uhoh = true;
            }
        }

        if (uhoh) {
            relic = AbstractDungeon.returnRandomScreenlessRelic(relic.tier);
        }

    }
}
