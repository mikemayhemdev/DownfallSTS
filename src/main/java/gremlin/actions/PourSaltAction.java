package gremlin.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class PourSaltAction extends AbstractGameAction {
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
