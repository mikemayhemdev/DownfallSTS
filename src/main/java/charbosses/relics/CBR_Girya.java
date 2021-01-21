package charbosses.relics;

import charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Girya;
import downfall.downfallMod;

public class CBR_Girya extends AbstractCharbossRelic {
    public static final String ID = "Girya";

    public CBR_Girya(int counter) {
        super(new Girya());
        this.counter = counter;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + CardCrawlGame.languagePack.getRelicStrings(ID).DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        //this.counter = AbstractDungeon.actNum;
        if (this.counter != 0) {
            this.flash();
            this.addToTop(new ApplyPowerAction(AbstractCharBoss.boss, AbstractCharBoss.boss, new StrengthPower(AbstractCharBoss.boss, this.counter), this.counter));
            this.addToTop(new RelicAboveCreatureAction(AbstractCharBoss.boss, this));
        }
    }


    @Override
    public AbstractRelic makeCopy() {
        return new CBR_Girya(0);
    }
}
