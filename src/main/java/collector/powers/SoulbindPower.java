package collector.powers;

import collector.CollectorCollection;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.HashMap;

public class SoulbindPower extends AbstractCollectorPower {
    public static final String NAME = "Soulbind";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public static int STACK_THRESHOLD = 10;
    public static int HP_LOSS = 20;

    private static HashMap<AbstractMonster, Boolean> collected = new HashMap<>();

    public SoulbindPower(AbstractMonster target, int amount) {
        super(NAME, TYPE, TURN_BASED, target, null, amount);
    }

    @Override
    public void onInitialApplication() {
        checkBurst();
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        checkBurst();
    }

    private void checkBurst() {
        if (amount >= STACK_THRESHOLD) {
            flash();
            addToBot(new LoseHPAction(owner, AbstractDungeon.player, HP_LOSS));
            if (!collected.get(owner)) {
                CollectorCollection.collect((AbstractMonster) owner);
            }
            addToBot(new ReducePowerAction(owner, AbstractDungeon.player, this, STACK_THRESHOLD));
        }
    }
}