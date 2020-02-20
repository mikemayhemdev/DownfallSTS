package sneckomod.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import slimebound.actions.MakeTempCardInHandActionReduceCost;


public class PureSneckoAction extends AbstractGameAction {
    public AbstractPlayer p;

    public PureSneckoAction(AbstractPlayer p) {
        this.p = p;


    }

    public void update() {

        int handSize = 0;

        for (AbstractCard c : p.hand.group) {
            if (c.color != AbstractDungeon.player.getCardColor()) {
                handSize++;
                AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(c, p.hand, true));
            }
        }

        for (int i = 0; i < handSize; i++) {
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandActionReduceCost(AbstractDungeon.returnTrulyRandomCardInCombat()));

        }
        this.isDone = true;
    }

}



