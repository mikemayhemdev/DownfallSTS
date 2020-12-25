package sneckomod.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import slimebound.actions.MakeTempCardInHandActionReduceCost;
import sneckomod.SneckoMod;


public class PureSneckoAction extends AbstractGameAction {
    public AbstractPlayer p;
    private boolean up;

    public PureSneckoAction(AbstractPlayer p, boolean upgraded) {
        this.p = p;
        this.up = upgraded;


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
            if (this.up) {
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandActionReduceCost(SneckoMod.getOffClassCard()));
            } else {
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(SneckoMod.getOffClassCard()));
            }

        }
        this.isDone = true;
    }

}



