package downfall.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import downfall.potions.CursedFountainPotion;

public class BanStuffPatch {
    @SpirePatch(
            clz = PotionHelper.class,
            method = "initialize"
    )
    public static class PotionPatch {
        public static void Postfix(AbstractPlayer.PlayerClass chosenClass) {
            PotionHelper.potions.remove(CursedFountainPotion.POTION_ID);
        }
    }
}