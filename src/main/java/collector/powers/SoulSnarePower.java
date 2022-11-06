package collector.powers;

import automaton.powers.AbstractAutomatonPower;
import automaton.powers.OnCompilePower;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SoulSnarePower extends AbstractCollectorPower {
    public static final String NAME = "SoulSnare";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public SoulSnarePower(AbstractMonster target, int amount) {
        super(NAME, TYPE, TURN_BASED, target, null, amount);
    }

    @Override
    public void onDeath() {

    }
}
