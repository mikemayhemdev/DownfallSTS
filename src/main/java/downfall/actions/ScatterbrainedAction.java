package downfall.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import downfall.cards.curses.Scatterbrained;
import gremlin.actions.MakeEchoAction;

public class ScatterbrainedAction extends AbstractGameAction {
    public ScatterbrainedAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    @Override
    public void update() {
        int amount = BaseMod.MAX_HAND_SIZE - AbstractDungeon.player.hand.size();
        AbstractDungeon.actionManager.addToTop(new MakeEchoAction(new Scatterbrained(), amount));
        this.isDone = true;
    }
}
