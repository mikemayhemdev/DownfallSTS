package awakenedOne.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class EmpressPower extends AbstractAwakenedPower {
    // intellij stuff buff
    public static final String NAME = EmpressPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);

    public EmpressPower(AbstractCreature owner) {
        super(NAME, PowerType.BUFF, false, AbstractDungeon.player, null, -1);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

}