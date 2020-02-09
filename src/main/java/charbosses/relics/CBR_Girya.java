package charbosses.relics;

import charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Girya;

public class CBR_Girya extends AbstractCharbossRelic {

    public CBR_Girya() {
        super(new Girya());
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        this.counter = AbstractDungeon.actNum;
        if (this.counter != 0) {
            this.flash();
            this.addToTop(new ApplyPowerAction(AbstractCharBoss.boss, AbstractCharBoss.boss, new StrengthPower(AbstractCharBoss.boss, this.counter), this.counter));
            this.addToTop(new RelicAboveCreatureAction(AbstractCharBoss.boss, this));
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_Girya();
    }
}
