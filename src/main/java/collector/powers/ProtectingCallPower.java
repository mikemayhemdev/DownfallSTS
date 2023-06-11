package collector.powers;

import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static collector.util.Wiz.atb;

public class ProtectingCallPower extends AbstractCollectorPower {
    public static final String NAME = "ProtectingCall";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public ProtectingCallPower(int amount) {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, amount);
    }

    @Override
    public void atStartOfTurnPostDraw() {
        if (TempHPField.tempHp.get(owner) > 0) {
            flash();
            atb(new GainBlockAction(owner, amount));
        }
    }
}