package gremlin.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BlockPerCardInHandAction extends AbstractGameAction {
    public BlockPerCardInHandAction(int amount) {
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.BLOCK;
        this.amount = amount;
    }

    @Override
    public void update() {
        if (!this.isDone) {
            this.isDone = true;
            int block = AbstractDungeon.player.hand.size() * amount;
            if (block > 0) {
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, block));
            }
        }
        this.tickDuration();
    }
}
