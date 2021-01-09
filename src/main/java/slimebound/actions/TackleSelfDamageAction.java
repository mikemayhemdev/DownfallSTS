package slimebound.actions;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import slimebound.powers.TackleModifyDamagePower;

public class TackleSelfDamageAction extends DamageAction {

    public TackleSelfDamageAction(DamageInfo info, AttackEffect effect) {
        super(AbstractDungeon.player, info, effect);
    }
}
