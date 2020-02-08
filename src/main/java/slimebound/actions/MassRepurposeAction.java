package slimebound.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;


public class MassRepurposeAction extends AbstractGameAction {
    public AbstractPlayer p;

    public MassRepurposeAction(AbstractPlayer p) {
        this.p = p;


    }


    public void update() {

        int handSize = 0;

        for (AbstractCard c : p.hand.group) {
            handSize++;
            AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(c, p.hand, true));
        }

        for (int i = 0; i < handSize; i++) {
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandActionReduceCost(AbstractDungeon.returnTrulyRandomCardInCombat()));

        }
        this.isDone = true;
    }

}



