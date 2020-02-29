package charbosses.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.Abacus;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.MutagenicStrength;

public class CBR_Mutagenic extends AbstractCharbossRelic {
    public static final String ID = "MutagenicStrength";

    public CBR_Mutagenic() {
        super(new MutagenicStrength());
    }


    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 3 + this.DESCRIPTIONS[1] + 3 + this.DESCRIPTIONS[2];
    }

    public void atBattleStart() {
        this.flash();
        this.addToTop(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, 3), 3));
        this.addToTop(new ApplyPowerAction(this.owner, this.owner, new LoseStrengthPower(this.owner, 3), 3));
        this.addToTop(new RelicAboveCreatureAction(this.owner, this));
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_Mutagenic();
    }
}
