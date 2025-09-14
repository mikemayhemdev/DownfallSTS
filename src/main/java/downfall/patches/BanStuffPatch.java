package downfall.patches;

import awakenedOne.AwakenedOneChar;
import basemod.devcommands.relic.RelicRemove;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import com.megacrit.cardcrawl.relics.BirdFacedUrn;
import com.megacrit.cardcrawl.relics.MummifiedHand;
import com.megacrit.cardcrawl.relics.OrangePellets;
import com.megacrit.cardcrawl.relics.PrismaticShard;
import downfall.downfallMod;
import downfall.potions.CursedFountainPotion;
import gremlin.patches.GremlinEnum;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.relicsToRemoveOnStart;
import static hermit.characters.hermit.Enums.HERMIT;

public class BanStuffPatch {
    @SpirePatch(
            clz = PotionHelper.class,
            method = "initialize"
    )
    public static class PotionPatch {
        public static void Postfix(AbstractPlayer.PlayerClass chosenClass) {
            PotionHelper.potions.remove(CursedFountainPotion.POTION_ID);

            if (chosenClass == GremlinEnum.GREMLIN) {
                relicsToRemoveOnStart.add("Prismatic Shard");
            }

            if (!downfallMod.disableBaseGameAdjustments) {
                if (EvilModeCharacterSelect.evilMode) {
                    relicsToRemoveOnStart.add("Ectoplasm");
                }

                if (EvilModeCharacterSelect.evilMode || chosenClass == HERMIT) {
                relicsToRemoveOnStart.add("Blue Candle");
                }

                if (chosenClass == AwakenedOneChar.Enums.AWAKENED_ONE && !downfallMod.disableBaseGameAdjustments) {
                    relicsToRemoveOnStart.add(BirdFacedUrn.ID);
                    relicsToRemoveOnStart.add(MummifiedHand.ID);
                }
            }
        }
    }
}

