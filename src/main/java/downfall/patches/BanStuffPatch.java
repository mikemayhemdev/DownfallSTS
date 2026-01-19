package downfall.patches;

import automaton.potions.BurnAndBuffPotion;
import awakenedOne.AwakenedOneChar;
import awakenedOne.potions.CultistsDelight;
import basemod.BaseMod;
import basemod.devcommands.relic.RelicRemove;
import champ.potions.CounterstrikePotion;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import com.megacrit.cardcrawl.relics.*;
import downfall.downfallMod;
import downfall.potions.CursedFountainPotion;
import expansioncontent.potions.BossPotion;
import gremlin.patches.GremlinEnum;
import gremlin.potions.WizPotion;
import guardian.potions.BlockOnCardUsePotion;
import slimebound.potions.ThreeZeroPotion;
import sneckomod.potions.MuddlingPotion;
import theHexaghost.potions.SoulburnPotion;

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

             if (!EvilModeCharacterSelect.evilMode && !downfallMod.contentSharing_potions) {
                 PotionHelper.potions.remove(BossPotion.POTION_ID);
                 PotionHelper.potions.remove(SoulburnPotion.POTION_ID);
                 PotionHelper.potions.remove(ThreeZeroPotion.POTION_ID);
                 PotionHelper.potions.remove(MuddlingPotion.POTION_ID);
                 PotionHelper.potions.remove(BlockOnCardUsePotion.POTION_ID);
                 PotionHelper.potions.remove(CounterstrikePotion.POTION_ID);
                 PotionHelper.potions.remove(WizPotion.POTION_ID);
                 PotionHelper.potions.remove(BurnAndBuffPotion.POTION_ID);
                 PotionHelper.potions.remove(CultistsDelight.POTION_ID);
             }

            if (chosenClass == GremlinEnum.GREMLIN) {
                relicsToRemoveOnStart.add(PrismaticShard.ID);
            }

            if (!downfallMod.disableBaseGameAdjustments) {
                if (EvilModeCharacterSelect.evilMode || chosenClass == HERMIT) {
                relicsToRemoveOnStart.add(BlueCandle.ID);
                }

                if (chosenClass == AwakenedOneChar.Enums.AWAKENED_ONE && !downfallMod.disableBaseGameAdjustments) {
                    relicsToRemoveOnStart.add(BirdFacedUrn.ID);
                    relicsToRemoveOnStart.add(MummifiedHand.ID);
                }
            }
        }
    }
}

