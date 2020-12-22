package charbosses.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.DuVuDoll;

public class CBR_DuvuDoll extends AbstractCharbossRelic {
    public static final String ID = "DuvuDoll";

    public CBR_DuvuDoll() {
        super(new DuVuDoll());
    }

    public CBR_DuvuDoll(int counter){
        this();
        this.counter = counter;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 1 + this.DESCRIPTIONS[1];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_DuvuDoll();
    }

    @Override
    public void setCounter(final int c) {
        this.counter = c;
        if (this.counter == 0) {
            this.description = this.DESCRIPTIONS[0] + 1 + this.DESCRIPTIONS[1] + this.DESCRIPTIONS[2];
        } else {
            this.description = this.DESCRIPTIONS[0] + 1 + this.DESCRIPTIONS[1] + this.DESCRIPTIONS[3] + this.counter + this.DESCRIPTIONS[4];
        }
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }

    @Override
    public void onEquip() {
        if (this.counter == 0) {
            this.description = this.DESCRIPTIONS[0] + 1 + this.DESCRIPTIONS[1] + this.DESCRIPTIONS[2];
        } else {
            this.description = this.DESCRIPTIONS[0] + 1 + this.DESCRIPTIONS[1] + this.DESCRIPTIONS[3] + this.counter + this.DESCRIPTIONS[4];
        }
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }

    @Override
    public void atBattleStart() {
        if (this.counter > 0) {
            this.flash();
            this.addToTop(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, this.counter), this.counter));
            this.addToTop(new RelicAboveCreatureAction(this.owner, this));
        }
    }
}
