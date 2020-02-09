package charbosses.relics;

import charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.SelfFormingClay;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class CBR_SelfFormingClay extends AbstractCharbossRelic {

    public CBR_SelfFormingClay() {
        super(new SelfFormingClay());
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 3 + this.DESCRIPTIONS[1];
    }

    @Override
    public void wasHPLost(final int damageAmount) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && damageAmount > 0) {
            this.flash();
            this.addToTop(new ApplyPowerAction(AbstractCharBoss.boss, AbstractCharBoss.boss, new NextTurnBlockPower(AbstractCharBoss.boss, 3, this.name), 3));
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_SelfFormingClay();
    }
}
