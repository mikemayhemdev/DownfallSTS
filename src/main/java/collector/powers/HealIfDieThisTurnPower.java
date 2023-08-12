package collector.powers;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.util.Wiz.atb;

public class HealIfDieThisTurnPower extends AbstractCollectorPower {
    public static final String NAME = "HealIfDieThisTurn";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.DEBUFF;
    public static final boolean TURN_BASED = false;

    public HealIfDieThisTurnPower(AbstractMonster target, int amount) {
        super(NAME, TYPE, TURN_BASED, target, null, amount);
    }

    @Override
    public void onDeath() {
        flash();
        atb(new HealAction(AbstractDungeon.player, AbstractDungeon.player, amount));
    }

    @Override
    public void atEndOfRound() {
        atb(new RemoveSpecificPowerAction(owner, owner, this));
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + FontHelper.colorString(owner.name, "y") + DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
    }
}