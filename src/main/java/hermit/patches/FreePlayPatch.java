package hermit.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theHexaghost.powers.PlayCardAndExhaustPower;

@SpirePatch(
        clz= AbstractCard.class,
        method="freeToPlay"
)

// I dont know if this patch is necessary anymore, but I'm also not sure if I can just remove it.
// Chances are yes.
// edit 2024.8: yeah somehow we needed this patch again lol

public class FreePlayPatch {


    @SpirePrefixPatch
    public static SpireReturn FreePlay(AbstractCard c)
    {
        if (CardCrawlGame.isInARun() && !AbstractDungeon.player.equals(null)) {
            if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
                if (AbstractDungeon.player.hasPower(PlayCardAndExhaustPower.POWER_ID) && c.cost != -1 && AbstractDungeon.player.hand.group.contains(c)) {
                    int amount = AbstractDungeon.player.getPower(PlayCardAndExhaustPower.POWER_ID).amount;
                    if (amount > 0)
                        return SpireReturn.Return(true);
                }
            }
        }
        return SpireReturn.Continue();
    }
}
