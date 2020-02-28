package charbosses.relics;

import charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.CaptainsWheel;

public class CBR_CaptainsWheel extends AbstractCharbossRelic {
    public static final String ID = "CaptainsWheel";

    public CBR_CaptainsWheel() {
        super(new CaptainsWheel(), RelicTier.COMMON);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 18 + this.DESCRIPTIONS[1];
    }

    @Override
    public void atBattleStart() {
        this.counter = 0;
    }

    @Override
    public void atTurnStart() {
        if (!this.grayscale) {
            ++this.counter;
        }
        if (this.counter == 3) {
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(AbstractCharBoss.boss, this));
            this.addToBot(new GainBlockAction(AbstractCharBoss.boss, AbstractCharBoss.boss, 18));
            this.counter = -1;
            this.grayscale = true;
        }
    }

    @Override
    public void onVictory() {
        this.counter = -1;
        this.grayscale = false;
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_CaptainsWheel();
    }
}
