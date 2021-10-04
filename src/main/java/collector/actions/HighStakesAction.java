package collector.actions;

import collector.cards.ChoiceCards.Fold;
import collector.cards.ChoiceCards.Hit;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class HighStakesAction extends AbstractGameAction {
    static public int TotalCost = 0;
    public boolean upgraded;
    public AbstractCard sourceCard;
    public static ArrayList<AbstractCard> CardsDrawn = new ArrayList<>();
    public HighStakesAction(boolean Upgraded, AbstractCard Source) {
        this.actionType = ActionType.CARD_MANIPULATION;
        upgraded = Upgraded;
        sourceCard = Source;
        this.duration = 0.1F;
        if (!AbstractDungeon.player.limbo.contains(Source)){
            if (AbstractDungeon.player.discardPile.contains(Source)) {
                AbstractDungeon.player.discardPile.removeCard(Source);
            } else if (AbstractDungeon.player.drawPile.contains(Source)){
                AbstractDungeon.player.drawPile.removeCard(Source);
            }else if (AbstractDungeon.player.hand.contains(Source)){
                AbstractDungeon.player.hand.removeCard(Source);
            }
            AbstractDungeon.player.limbo.addToTop(Source);
        }
    }

    public void update() {
        if (this.duration == 0.1F) {
            if (AbstractDungeon.player.drawPile.size() > 0) {
                AbstractCard cardtoDraw = AbstractDungeon.player.drawPile.getTopCard();
                TotalCost += cardtoDraw.cost;
                System.out.println(TotalCost);
                AbstractCard Hit = new Hit(upgraded, sourceCard);
                AbstractCard Fold = new Fold(sourceCard);
                AbstractDungeon.actionManager.addToBottom(new DrawCardAction(1));
                CardsDrawn.add(cardtoDraw);
                if (!upgraded && TotalCost >= 4) {
                    addToBot(new ExhaustSpecificCardAction(sourceCard, AbstractDungeon.player.limbo));
                    for (AbstractCard c : CardsDrawn) {
                        addToBot(new DiscardSpecificCardAction(c));
                    }
                    CardsDrawn.clear();
                    TotalCost = 0;
                } else if (upgraded && TotalCost >= 6) {
                    addToBot(new ExhaustSpecificCardAction(sourceCard, AbstractDungeon.player.limbo));
                    for (AbstractCard c : CardsDrawn) {
                        addToBot(new DiscardSpecificCardAction(c));
                    }
                    CardsDrawn.clear();
                    TotalCost = 0;
                } else {
                    Fold.magicNumber = CardsDrawn.size();
                    Fold.baseMagicNumber = CardsDrawn.size();
                    if (upgraded) {
                        Hit.magicNumber = 6 - TotalCost;
                        Hit.baseMagicNumber = 6 - TotalCost;
                    } else {
                        Hit.magicNumber = 4 - TotalCost;
                        Hit.baseMagicNumber = 4 - TotalCost;
                    }
                    ArrayList<AbstractCard> Stakes = new ArrayList<>();
                    Stakes.add(Hit);
                    Stakes.add(Fold);
                    AbstractDungeon.actionManager.addToBottom(new ChooseOneAction(Stakes));
                }
            } else {
                addToBot(new EmptyDeckShuffleAction());
                addToBot(new HighStakesAction(upgraded,sourceCard));
            }
        }
        this.tickDuration();
    }
}
