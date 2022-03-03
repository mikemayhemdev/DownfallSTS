package charbosses.relics;

import charbosses.actions.common.EnemyMakeTempCardInHandAction;
import charbosses.cards.colorless.EnShiv;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.MutagenicStrength;
import com.megacrit.cardcrawl.relics.NinjaScroll;

public class CBR_NinjaScroll extends AbstractCharbossRelic {
    public static final String ID = "NinjaScroll";

    public CBR_NinjaScroll() {
        super(new NinjaScroll());
    }


    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void atBattleStart() {
        this.flash();
        this.addToTop(new EnemyMakeTempCardInHandAction(new EnShiv(), 3));
        this.addToTop(new RelicAboveCreatureAction(this.owner, this));
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_NinjaScroll();
    }
}
