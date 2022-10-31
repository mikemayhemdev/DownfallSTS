package gremlin.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class PourSaltOuterAction extends AbstractGameAction
{
    private final DamageInfo info;

    public PourSaltOuterAction(final AbstractCreature target, final DamageInfo info, final int amount) {
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.BLOCK;
        this.target = target;
        this.amount = amount;
        this.info = info;
    }

    @Override
    public void update() {
        if (this.target != null && this.target.hasPower("Weakened")) {
            AbstractDungeon.actionManager.addToTop(new PourSaltFollowUpAction(target, amount));
        }
        AbstractDungeon.actionManager.addToTop(new PourSaltAction(target,amount));
        AbstractDungeon.actionManager.addToTop(new DamageAction(target, info, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        this.isDone = true;
    }
}
