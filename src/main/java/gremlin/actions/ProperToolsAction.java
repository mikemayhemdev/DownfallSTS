package gremlin.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gremlin.GremlinMod;
import gremlin.cards.Ward;

public class ProperToolsAction extends AbstractGameAction {
    private final AbstractMonster m;

    public ProperToolsAction(final AbstractMonster m, int amount) {
        this.actionType = ActionType.WAIT;
        this.amount = amount;
        this.m = m;
    }

    @Override
    public void update() {
        if (GremlinMod.doesEnemyIntendToAttack(this.m)) {
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Ward(), amount));
        } else {
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Shiv(), amount));
        }
        this.isDone = true;
    }
}

