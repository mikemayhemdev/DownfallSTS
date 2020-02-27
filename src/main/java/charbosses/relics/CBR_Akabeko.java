package charbosses.relics;

import charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Akabeko;
import com.megacrit.cardcrawl.relics.Anchor;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class CBR_Akabeko extends AbstractCharbossRelic {

    public CBR_Akabeko() {
        super(new Akabeko());
        this.tier = RelicTier.UNCOMMON;
    }

    public void atBattleStart() {
        this.flash();
        this.addToTop(new ApplyPowerAction(this.owner, this.owner, new VigorPower(this.owner, 8), 8));
        this.addToTop(new RelicAboveCreatureAction(this.owner, this));
    }
    @Override
    public AbstractRelic makeCopy() {
        return new CBR_Akabeko();
    }
}
