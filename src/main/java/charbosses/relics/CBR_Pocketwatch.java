package charbosses.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.powers.PenNibPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.PenNib;

public class CBR_Pocketwatch extends AbstractCharbossRelic {

    public CBR_Pocketwatch() {
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
        return new CBR_Pocketwatch();
    }
}
