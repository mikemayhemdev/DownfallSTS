package awakenedOne.powers;

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static awakenedOne.util.Wiz.atb;

public class MoonlitVisionPower extends AbstractAwakenedPower implements NonStackablePower {
    // intellij stuff buff
    public static final String NAME = MoonlitVisionPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);

    public MoonlitVisionPower() {
        super(NAME, PowerType.BUFF, false, AbstractDungeon.player, null, 1);
        updateDescription();
    }

    @Override
    public void atStartOfTurnPostDraw() {
        if (AbstractDungeon.player.hand.size() >= 8) {
            flash();
            atb(new GainEnergyAction(amount));
        }
    }

    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

}