package downfall.patches;

import basemod.devcommands.relic.RelicRemove;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import com.megacrit.cardcrawl.relics.PrismaticShard;
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

                if (EvilModeCharacterSelect.evilMode) {
             //   relicsToRemoveOnStart.add("Blue Candle");
             //   relicsToRemoveOnStart.add("Ectoplasm");
                 }

            }
            }

        }
