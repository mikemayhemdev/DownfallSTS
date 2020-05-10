package downfall.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class LoseGoldAction extends AbstractGameAction {
    public LoseGoldAction(int amount) {
        this.amount = amount;
    }

    public void update() {
        AbstractDungeon.player.loseGold(this.amount);
        this.isDone = true;
    }
}
