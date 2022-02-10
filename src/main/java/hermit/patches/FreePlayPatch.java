package hermit.patches;

import basemod.devcommands.power.Power;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.Pain;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import hermit.powers.EternalPower;
import hermit.relics.Memento;

@SpirePatch(
        clz= AbstractCard.class,
        method="freeToPlay"
)

public class FreePlayPatch {

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

}
