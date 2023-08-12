package collector.patches.PetTorchPatches;


import collector.CollectorChar;
import collector.CollectorMod;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.RestRoom;
import downfall.downfallMod;

@SpirePatch(clz = AbstractPlayer.class, method = "render")
public class RenderTorchAtTheRightTime {
    @SpirePostfixPatch
    public static void Postfix(AbstractPlayer obj, SpriteBatch sb) {
        if (AbstractDungeon.player.chosenClass.equals(CollectorChar.Enums.THE_COLLECTOR)) {
            //TODO: During combat, if have temp hp, show torch head
        }
    }
}


