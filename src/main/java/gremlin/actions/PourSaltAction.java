package gremlin.actions;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.common.*;

public class PourSaltAction extends AbstractGameAction
{
    public PourSaltAction(final AbstractCreature target, int amount) {
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.BLOCK;
        this.target = target;
        this.amount = amount;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_XFAST && this.target != null && this.target.hasPower("Weakened")) {
            AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(new Shiv(), amount));
            AbstractDungeon.actionManager.addToTop(new ReducePowerAction(this.target, AbstractDungeon.player, "Weakened", 1));
        }
        this.tickDuration();
    }
}
