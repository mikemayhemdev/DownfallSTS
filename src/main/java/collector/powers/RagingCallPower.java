package collector.powers;

import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static collector.util.Wiz.atb;

public class RagingCallPower extends AbstractCollectorPower {
    public static final String NAME = "RagingCall";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public RagingCallPower(int amount) {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, amount);
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (TempHPField.tempHp.get(owner) > 0) {
            flash();
            atb(new DamageAllEnemiesAction(owner, DamageInfo.createDamageMatrix(amount, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}