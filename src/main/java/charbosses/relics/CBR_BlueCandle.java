package charbosses.relics;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.BlueCandle;

public class CBR_BlueCandle extends AbstractCharbossRelic {
    public static final String ID = "BlueCandle";

    public CBR_BlueCandle() {
        super(new BlueCandle());
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 1 + this.DESCRIPTIONS[1];
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.CURSE) {
            this.flash();
            this.addToBot(new LoseHPAction(this.owner, this.owner, 1, AbstractGameAction.AttackEffect.FIRE));
            card.exhaust = true;
            action.exhaustCard = true;
        }

    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_BlueCandle();
    }
}
