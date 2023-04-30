package collector.patches.PetTorchPatches;


import collector.CollectorChar;
import collector.CollectorMod;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import downfall.downfallMod;

@SpirePatch(clz = AbstractPlayer.class, method = "update")
public class UpdateTorchAtTheRightTime {
    @SpirePostfixPatch
    public static void Postfix(AbstractPlayer obj) {
        if (AbstractDungeon.player.chosenClass.equals(CollectorChar.Enums.THE_COLLECTOR)) {
            if (CollectorMod.pet != null) {
                //CollectorMod.pet.update();
            }
        }
    }
}


