package expansioncontent.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.function.Predicate;

public class DrawSpecificAction extends AbstractGameAction {
    public AbstractPlayer p;
    public Predicate<AbstractCard> predicate;

    public DrawSpecificAction(int number, Predicate<AbstractCard> condition) {
        this.predicate = condition;
        this.p = AbstractDungeon.player;
        this.setValues(this.p, this.p, number);
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_XFAST;
    }

    public void update() {
        this.isDone = true;
        if (this.p.drawPile.isEmpty())
            return;

        if (this.p.hand.size() == BaseMod.MAX_HAND_SIZE) {
            this.p.createHandIsFullDialog();
            return;
        }

        for (AbstractCard c : this.p.drawPile.group)
            if (this.predicate.test(c)) {
                if (this.amount > 1)
                    addToTop(new DrawSpecificAction(this.amount-1, this.predicate));

                this.p.drawPile.group.remove(c);
                this.p.drawPile.addToTop(c);
                addToTop(new DrawCardAction(1));
                return;
            }
    }
}
