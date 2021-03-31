package gremlin.actions;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.common.*;

public class BlockPerCardInHandAction extends AbstractGameAction
{
    public BlockPerCardInHandAction(int amount) {
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.BLOCK;
        this.amount = amount;
    }

    @Override
    public void update() {
        if(!this.isDone) {
            this.isDone = true;
            int block = AbstractDungeon.player.hand.size() * amount;
            if (block > 0) {
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, block));
            }
        }
        this.tickDuration();
    }
}
