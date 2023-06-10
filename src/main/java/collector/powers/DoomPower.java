package collector.powers;

import collector.CollectorCollection;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.InstantKillAction;
import com.megacrit.cardcrawl.actions.utility.HideHealthBarAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.util.Wiz.att;

public class DoomPower extends AbstractCollectorPower {
    public static final String NAME = "Doom";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.DEBUFF;
    public static final boolean TURN_BASED = false;

    public DoomPower(AbstractMonster target, int amount) {
        super(NAME, TYPE, TURN_BASED, target, null, amount);
    }

    @Override
    public void atStartOfTurn() {
        if (this.owner.currentHealth <= this.amount && owner.currentHealth > 0) {
            flash();
            att(new HideHealthBarAction(owner));
            att(new AbstractGameAction() {
                @Override
                public void update() {
                    isDone = true;
                    CollectorCollection.collect((AbstractMonster) owner);
                }
            });
            att(new InstantKillAction(owner));
        }
    }
}