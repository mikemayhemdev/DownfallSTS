package collector.powers;

import collector.util.Wiz;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static collector.util.Wiz.isAfflicted;

public class KarmaPower extends AbstractCollectorPower {
    public static final String NAME = "Karma";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public KarmaPower(int amount) {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, amount);
    }

    @Override
    public void atStartOfTurnPostDraw() {
        flash();
        if (Wiz.getEnemies().stream().anyMatch(q -> {
            if (isAfflicted(q)) {
                return true;
            }
            return false;
        })) {
            addToBot(new GainBlockAction(owner, amount));
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}