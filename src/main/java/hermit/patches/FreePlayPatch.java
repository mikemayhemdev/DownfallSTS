package hermit.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;

@SpirePatch(
        clz= AbstractCard.class,
        method="freeToPlay"
)

// I dont know if this patch is necessary anymore, but I'm also not sure if I can just remove it.
// Chances are yes.

public class FreePlayPatch {

    /*
    @SpirePrefixPatch
    public static SpireReturn FreePlay(AbstractCard c)
    {
        if (CardCrawlGame.isInARun() && !AbstractDungeon.player.equals(null)) {
            if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
                if (AbstractDungeon.player.hasPower(EternalPower.POWER_ID) && c.cost != -1 && AbstractDungeon.player.hand.group.indexOf(c) != -1) {
                    EternalPower pow = (EternalPower) AbstractDungeon.player.getPower(EternalPower.POWER_ID);
                    if (pow.amount > 0)
                        return SpireReturn.Return(true);
                }
            }
        }
        return SpireReturn.Continue();
    }
    */
}
