package charbosses.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.MummifiedHand;

public class CBR_MummifiedHand extends AbstractCharbossRelic {
    public static final String ID = "CBRMummifiedHand";

    public CBR_MummifiedHand() {
        super(new MummifiedHand());
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.POWER) {
            this.flash();
            this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            //doesn't actually do anything, cost reduction in Silent 3
        }

    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_MummifiedHand();
    }
}
