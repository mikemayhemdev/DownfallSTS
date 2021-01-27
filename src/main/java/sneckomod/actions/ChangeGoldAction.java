package sneckomod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.GainGoldTextEffect;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import sneckomod.vfx.LoseGoldTextEffect;

public class ChangeGoldAction extends AbstractGameAction {
    public ChangeGoldAction(int amount) {
        this.amount = amount;
        this.actionType = ActionType.SPECIAL;
    }

    @Override
    public void update() {
        if (amount > 0) {
            AbstractDungeon.effectList.add(new RainingGoldEffect(Math.max(amount, 100)));
            AbstractDungeon.effectList.add(new GainGoldTextEffect(amount));
            AbstractDungeon.player.gainGold(amount);
        } else if (amount < 0) {
            AbstractDungeon.player.loseGold(-amount);
            AbstractDungeon.effectList.add(new LoseGoldTextEffect(-amount));
        } else {
            AbstractDungeon.effectList.add(new GainGoldTextEffect(0));
        }
        this.isDone = true;
    }
}