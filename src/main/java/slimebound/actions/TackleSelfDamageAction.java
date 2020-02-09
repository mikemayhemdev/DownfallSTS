package slimebound.actions;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import slimebound.powers.TackleSelfDamagePreventPower;

public class TackleSelfDamageAction extends DamageAction {

    public TackleSelfDamageAction(DamageInfo info, AttackEffect effect) {
        super(AbstractDungeon.player, info, effect);
    }

    @Override
    public void update() {
        if (AbstractDungeon.player.hasPower(TackleSelfDamagePreventPower.POWER_ID)) {
            AbstractDungeon.player.getPower(TackleSelfDamagePreventPower.POWER_ID).flash();
            addToBot(new RemoveSpecificPowerAction(target, target, TackleSelfDamagePreventPower.POWER_ID));
            isDone = true;
            return;
        }
        super.update();
    }
}
