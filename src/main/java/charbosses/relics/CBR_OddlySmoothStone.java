package charbosses.relics;

import charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.OddlySmoothStone;
import com.megacrit.cardcrawl.relics.Vajra;

public class CBR_OddlySmoothStone extends AbstractCharbossRelic {
    public static final String ID = "OddlySmoothStone";
    private static final int DEX = 1;

    public CBR_OddlySmoothStone() {
        super(new OddlySmoothStone());
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 1 + this.DESCRIPTIONS[1];
    }

    @Override
    public void atBattleStart() {
        this.flash();
        this.addToTop(new ApplyPowerAction(AbstractCharBoss.boss, AbstractCharBoss.boss, new DexterityPower(AbstractCharBoss.boss, 1), 1));
        this.addToTop(new RelicAboveCreatureAction(AbstractCharBoss.boss, this));
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_OddlySmoothStone();
    }
}
