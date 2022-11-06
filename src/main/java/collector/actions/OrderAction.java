package collector.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.GainGoldTextEffect;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import downfall.vfx.LoseGoldTextEffect;

public class OrderAction extends AbstractGameAction {
    public OrderAction() {
        this.actionType = ActionType.SPECIAL;
    }

    @Override
    public void update() {
        //TODO
        this.isDone = true;
    }
}