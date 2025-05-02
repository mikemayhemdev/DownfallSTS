package awakenedOne.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import expansioncontent.actions.EchoACardAction;

import java.util.ArrayList;

public class DuplicatePowerAction
        extends AbstractGameAction
{
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("DualWieldAction");
    public static final String[] TEXT = uiStrings.TEXT;
    private static final float DURATION_PER_CARD = 0.25F;
    private AbstractPlayer p;
    private int dupeAmount;
    private ArrayList<AbstractCard> cannotDuplicate = new ArrayList<>();

    public DuplicatePowerAction(AbstractCreature source, int amount)
    {
        setValues(AbstractDungeon.player, source, amount);
        this.actionType = ActionType.DRAW;
        this.duration = 0.25F;
        this.p = AbstractDungeon.player;
        this.dupeAmount = amount;
    }

    public void update()
    {
        if (this.duration == Settings.ACTION_DUR_FAST)
        {
            for (AbstractCard c : this.p.hand.group) {
                if (!isAttack(c)) {
                    this.cannotDuplicate.add(c);
                }
            }
            if (this.cannotDuplicate.size() == this.p.hand.group.size())
            {
                this.isDone = true;
                return;
            }
            if (this.p.hand.group.size() - this.cannotDuplicate.size() == 1) {
                for (AbstractCard c : this.p.hand.group) {
                    if (isAttack(c))
                    {
                        AbstractDungeon.actionManager.addToTop(new EchoACardAction(c, dupeAmount));
                        this.isDone = true;
                        return;
                    }
                }
            }
            this.p.hand.group.removeAll(this.cannotDuplicate);
            if (this.p.hand.group.size() > 1)
            {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false, false, false, false);
                tickDuration();
                return;
            }
            if (this.p.hand.group.size() == 1)
            {
                AbstractDungeon.actionManager.addToTop(new EchoACardAction(this.p.hand
                        .getTopCard(), dupeAmount));
                returnCards();
                this.isDone = true;
            }
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved)
        {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group)
            {
                this.p.hand.addToTop(c);
                AbstractDungeon.actionManager.addToTop(new EchoACardAction(c, dupeAmount));
            }
            returnCards();

            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone = true;
        }
        tickDuration();
    }

    private void returnCards()
    {
        for (AbstractCard c : this.cannotDuplicate) {
            this.p.hand.addToTop(c);
        }
        this.p.hand.refreshHandLayout();
    }

    private boolean isAttack(AbstractCard card)
    {
        return (card.type.equals(AbstractCard.CardType.POWER));
    }
}