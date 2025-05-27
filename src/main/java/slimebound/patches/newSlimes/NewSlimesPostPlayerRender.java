package slimebound.patches.newSlimes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import slimebound.patches.SlimeboundEnum;
import slimebound.slimes.SlimeHelper;

@SpirePatch(clz = AbstractPlayer.class, method = "render")
public class NewSlimesPostPlayerRender {
    public static void Postfix(AbstractPlayer obj, SpriteBatch sb) {
        if (CardCrawlGame.chosenCharacter == SlimeboundEnum.SLIMEBOUND) {
            if (SlimeHelper.InCombat()) {
                SlimeHelper.Render(sb);
                // TODO: Modified version of render may want to be called outside of combat to see ally slimes
            }
        }
    }
}