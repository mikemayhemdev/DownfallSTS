//package downfall.patches;
//
//import awakenedOne.relics.MoonTalisman;
//import champ.relics.*;
//import collector.relics.BottledCollectible;
//import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
//import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
//import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
//import com.megacrit.cardcrawl.helpers.RelicLibrary;
//import com.megacrit.cardcrawl.relics.AbstractRelic;
//import guardian.GuardianMod;
//import guardian.relics.BottledAnomaly;
//import guardian.relics.BottledStasis;
//import guardian.relics.GemstoneGun;
//import guardian.relics.PickAxe;
//import sneckomod.relics.SneckoCommon;
//
//import java.util.ArrayList;
//import java.util.Objects;
//
//import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.returnRandomRelicKey;
//
//@SpirePatch(
//        clz = AbstractDungeon.class,
//        method = "returnRandomScreenlessRelic"
//)
//public class ReturnRandomScreenlessRelicPatch {
//    @SpirePostfixPatch
//    public static void Postfix(AbstractDungeon __instance, AbstractRelic relic) {
//
//        //I'm going to assume that this thing isn't going to be used to ask for event or boss relics
//        //so I'm not going to include thEm
//
//
//        AbstractRelic tmpRelic;
//        for(tmpRelic = RelicLibrary.getRelic(returnRandomRelicKey(relic.tier)).makeCopy();
//            Objects.equals(tmpRelic.relicId, "Bottled Flame") ||
//                    (Objects.equals(tmpRelic.relicId, PickAxe.ID) && AbstractDungeon.getCurrRoom().eliteTrigger) ||
//                    Objects.equals(tmpRelic.relicId, SneckoCommon.ID) ||
//                    Objects.equals(tmpRelic.relicId, BottledStasis.ID) ||
//                    Objects.equals(tmpRelic.relicId, BottledAnomaly.ID) ||
//                    Objects.equals(tmpRelic.relicId, GemstoneGun.ID) ||
//                    Objects.equals(tmpRelic.relicId, SignatureFinisher.ID) ||
//                    Objects.equals(tmpRelic.relicId, BottledCollectible.ID) ||
//                    Objects.equals(tmpRelic.relicId, MoonTalisman.ID) ||
//                    Objects.equals(tmpRelic.relicId, "Bottled Lightning") || Objects.equals(tmpRelic.relicId, "Bottled Tornado") || Objects.equals(tmpRelic.relicId, "Whetstone"); tmpRelic = RelicLibrary.getRelic(returnRandomRelicKey(relic.tier)).makeCopy()) {
//        }
//
//        relic = tmpRelic;
//
//    }
//}