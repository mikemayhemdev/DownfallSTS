package charbosses.relics;

import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.PenNibPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.PenNib;
import com.megacrit.cardcrawl.relics.Shuriken;

public class CBR_PenNib extends AbstractCharbossRelic {

    public CBR_PenNib() {
        super(new PenNib());
        this.counter = 0;
    }


    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK) {
            ++this.counter;
            if (this.counter == 10) {
                this.counter = 0;
                this.flash();
                this.pulse = false;
            } else if (this.counter == 9) {
                this.beginPulse();
                this.pulse = true;
                this.owner.hand.refreshHandLayout();
                this.addToBot(new RelicAboveCreatureAction(this.owner, this));
                this.addToBot(new ApplyPowerAction(this.owner, this.owner, new PenNibPower(this.owner, 1), 1, true));
            }
        }

    }

    public void atBattleStart() {
        if (this.counter == 9) {
            this.beginPulse();
            this.pulse = true;
            this.owner.hand.refreshHandLayout();
            this.addToBot(new ApplyPowerAction(this.owner, this.owner, new PenNibPower(this.owner, 1), 1, true));
        }

    }
    @Override
    public AbstractRelic makeCopy() {
        return new CBR_PenNib();
    }
}
