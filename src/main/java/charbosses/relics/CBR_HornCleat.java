package charbosses.relics;

import charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.CaptainsWheel;
import com.megacrit.cardcrawl.relics.HornCleat;

public class CBR_HornCleat extends AbstractCharbossRelic {
    public static final String ID = "HornCleat";

    public CBR_HornCleat() {
        super(new HornCleat());
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 14 + this.DESCRIPTIONS[1];
    }

    @Override
    public void onEnergyRecharge() {
        if (!this.grayscale) {
            ++this.counter;
        }

        if (this.counter == 2) {
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(this.owner, this));
            this.addToBot(new GainBlockAction(this.owner, this.owner, 14));
            this.counter = -1;
            this.grayscale = true;
        }
    }

    public void atBattleStart() {
        this.counter = 0;
    }

    public void onVictory() {
        this.counter = -1;
        this.grayscale = false;
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_HornCleat();
    }
}
