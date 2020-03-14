package charbosses.relics;

import charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Anchor;
import com.megacrit.cardcrawl.relics.CloakClasp;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class CBR_CloakClasp extends AbstractCharbossRelic {
    public static final String ID = "CloakClasp";

    public CBR_CloakClasp() {
        super(new CloakClasp());
    }


    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 1 + this.DESCRIPTIONS[1];
    }

    public void onPlayerEndTurn() {
        if (!AbstractCharBoss.boss.hand.group.isEmpty()) {
            this.flash();
            this.addToBot(new GainBlockAction(AbstractCharBoss.boss, (AbstractCreature)null, AbstractCharBoss.boss.hand.group.size()));
        }

    }


    public AbstractRelic makeCopy() {
        return new CloakClasp();
    }
}
