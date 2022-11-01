package gremlin.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gremlin.GremlinMod;
import gremlin.cards.Ward;

public class CounterStrikeAction extends AbstractGameAction {
    private final AbstractMonster m;
    private final boolean isUpgraded;

    public CounterStrikeAction(final AbstractMonster m, final int amount, boolean isUpgraded) {
        this.actionType = ActionType.WAIT;
        this.amount = amount;
        this.m = m;
        this.isUpgraded = isUpgraded;
    }

    @Override
    public void update() {
        if (GremlinMod.doesEnemyIntendToAttack(this.m)) {
            AbstractCard c = new Ward();
            if (isUpgraded) {
                c.upgrade();
            }
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c, amount));
        }
        this.isDone = true;
    }
}

