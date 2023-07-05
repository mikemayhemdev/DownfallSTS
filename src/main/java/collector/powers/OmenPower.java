package collector.powers;

import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

public class OmenPower extends AbstractCollectorPower {
    public static final String NAME = "Omen";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public OmenPower(int amount) {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, amount);
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (damageAmount > 0 && target != this.owner && info.type == DamageInfo.DamageType.NORMAL && target.hasPower(WeakPower.POWER_ID) && target.hasPower(VulnerablePower.POWER_ID) && !target.isDeadOrEscaped()) {
            this.flash();
            addToBot(new ReducePowerAction(target, owner, WeakPower.POWER_ID, 1));
            addToBot(new ReducePowerAction(target, owner, VulnerablePower.POWER_ID, 1));
            addToBot(new LoseHPAction(target, owner, amount));
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}