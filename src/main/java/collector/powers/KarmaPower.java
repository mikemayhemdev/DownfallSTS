package collector.powers;

import collector.util.Wiz;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class KarmaPower extends AbstractCollectorTwoAmountPower {
    public static final String NAME = "Karma";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public KarmaPower(int amount, int amount2) {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, amount);
        this.amount2 = amount2;
    }

    @Override
    public void atStartOfTurnPostDraw() {
        flash();
        addToBot(new GainBlockAction(owner, amount));
        if (Wiz.getEnemies().stream().anyMatch(q -> {
            AbstractPower result = q.getPower(DoomPower.POWER_ID);
            if (result != null) {
                if (result.amount >= 25) {
                    return true;
                }
            }
            return false;
        })) {
            addToBot(new GainBlockAction(owner, amount2));
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + amount2 + DESCRIPTIONS[2];
    }
}