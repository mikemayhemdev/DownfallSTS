package collector.powers;

import collector.util.Wiz;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class KarmaPower extends AbstractCollectorPower {
    public static final String NAME = "Karma";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public KarmaPower(int amount, int amount2) {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, amount);
    }

    private static final int DOOM_PER_BLOCK = 5;

    @Override
    public void atStartOfTurnPostDraw() {
        int result = getTotalDoom() / DOOM_PER_BLOCK;
        if (result > 0) {
            flash();
            addToBot(new GainBlockAction(owner, result * amount));
        }
    }

    private int getTotalDoom() {
        int result = 0;
        for (AbstractMonster m : Wiz.getEnemies()) {
            AbstractPower found = m.getPower(DoomPower.POWER_ID);
            if (found != null) {
                result += found.amount;
            }
        }
        return result;
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + DOOM_PER_BLOCK + DESCRIPTIONS[2];
    }
}