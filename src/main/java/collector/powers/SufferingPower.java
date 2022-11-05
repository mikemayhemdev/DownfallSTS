package collector.powers;

import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

public class SufferingPower extends AbstractCollectorPower {
    public static final String NAME = "Suffering";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public SufferingPower(AbstractMonster m, int count) {
        super(NAME, TYPE, TURN_BASED, m, AbstractDungeon.player, count);
        amount = count;
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        super.onApplyPower(power, target, source);
        onSpecificTrigger();
    }

    @Override
    public void onSpecificTrigger() {
        addToBot(new LoseHPAction(owner, AbstractDungeon.player, amount));
        //TODO - Patch Vulnerable Power and Weak Power to trigger this
    }

}
