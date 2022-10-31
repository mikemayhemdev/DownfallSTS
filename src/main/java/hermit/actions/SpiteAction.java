package hermit.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import hermit.powers.Rugged;

import java.util.Iterator;

public class SpiteAction extends AbstractGameAction {
    private float startingDuration;

    public SpiteAction() {
        this.actionType = ActionType.WAIT;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startingDuration;
    }

    public void update() {
        if (this.duration == this.startingDuration) {
            Iterator var1 = AbstractDungeon.player.hand.group.iterator();

            while(var1.hasNext()) {
                AbstractCard c = (AbstractCard)var1.next();
                if (c.type == CardType.CURSE) {
                    this.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new Rugged(AbstractDungeon.player, 1)));
                    this.addToTop(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
                }
            }

            this.isDone = true;
        }

    }
}