package collector.powers;

import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class MoreBlockWithTempHPPower extends AbstractCollectorPower {
    public static final String NAME = "MoreBlockWithTempHP";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public MoreBlockWithTempHPPower(int amount) {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, amount);
    }

    @Override
    public float modifyBlock(float blockAmount) {
        if (TempHPField.tempHp.get(owner) > 0) {
            return blockAmount + amount;
        }
        return blockAmount;
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}