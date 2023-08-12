package collector.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.util.Wiz.atb;

public class LanternFlarePower extends AbstractCollectorPower {
    public static final String NAME = "LanternFlare";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.DEBUFF;
    public static final boolean TURN_BASED = false;

    public LanternFlarePower(AbstractMonster target, int amount) {
        super(NAME, TYPE, TURN_BASED, target, AbstractDungeon.player, amount);
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        flash();
        atb(new ApplyPowerAction(owner, owner, new DoomPower((AbstractMonster) owner, amount), amount));
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}