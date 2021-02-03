package gremlin.actions;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.common.*;
import gremlin.cards.Ward;

public class ProperToolsAction extends AbstractGameAction
{
    private AbstractMonster m;

    public ProperToolsAction(final AbstractMonster m, int amount) {
        this.actionType = ActionType.WAIT;
        this.amount = amount;
        this.m = m;
    }

    @Override
    public void update() {
        if (this.m != null && (this.m.intent == AbstractMonster.Intent.ATTACK || this.m.intent == AbstractMonster.Intent.ATTACK_BUFF || this.m.intent == AbstractMonster.Intent.ATTACK_DEBUFF || this.m.intent == AbstractMonster.Intent.ATTACK_DEFEND)) {
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Ward(), amount));
        } else {
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Shiv(), amount));
        }
        this.isDone = true;
    }
}

