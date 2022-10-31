package gremlin.actions;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.common.*;
import gremlin.cards.Ward;
import gremlin.powers.WizPower;

public class AstoundAction extends AbstractGameAction
{
    private boolean upgraded;

    public AstoundAction(final AbstractCreature source, int amount, boolean upgraded) {
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.BLOCK;
        this.source = source;
        this.amount = amount;
        this.upgraded = upgraded;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_XFAST &&
                this.source.hasPower(WizPower.POWER_ID) && this.source.getPower(WizPower.POWER_ID).amount >= 3) {
            AbstractCard c = new Ward();
            if(upgraded){
                c.upgrade();
            }
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c, amount));
        }
        this.tickDuration();
    }
}
