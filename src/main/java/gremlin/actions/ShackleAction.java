package gremlin.actions;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class ShackleAction extends AbstractGameAction
{
    public ShackleAction(final AbstractCreature target, int amount) {
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.DEBUFF;
        this.target = target;
        this.amount = amount;
    }

    @Override
    public void update() {
        if (!target.hasPower("Artifact")) {
            AbstractDungeon.actionManager.addToTop(
                    new ApplyPowerAction(target, AbstractDungeon.player, new GainStrengthPower(target, this.amount), this.amount));
        }
        AbstractDungeon.actionManager.addToTop(
                new ApplyPowerAction(target, AbstractDungeon.player, new StrengthPower(target, -amount), -amount));
        this.tickDuration();
    }
}
