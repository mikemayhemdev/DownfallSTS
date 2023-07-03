package collector.powers.collectioncards;

import collector.powers.AbstractCollectorPower;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static collector.util.Wiz.atb;

public class TransientCardPower extends AbstractCollectorPower {
    public static final String NAME = "TransientCard";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.DEBUFF;
    public static final boolean TURN_BASED = false;

    public TransientCardPower() {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, 6);
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        atb(new ReducePowerAction(owner, owner, this, 1));
        if (amount == 1) {
            //TODO: Yoink alchyr's instakill tech
        }
    }
}