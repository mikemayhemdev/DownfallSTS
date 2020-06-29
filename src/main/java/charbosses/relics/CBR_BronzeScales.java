package charbosses.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.powers.ThornsPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.BronzeScales;

public class CBR_BronzeScales extends AbstractCharbossRelic {
    public static final String ID = "BronzeScales";
    public CBR_BronzeScales() {
        super(new BronzeScales());
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_BronzeScales();
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 3 + this.DESCRIPTIONS[1];
    }

    @Override
    public void atBattleStart() {
        this.flash();
        this.addToTop(new ApplyPowerAction(this.owner, this.owner, new ThornsPower(this.owner, 3), 3));
    }
}
