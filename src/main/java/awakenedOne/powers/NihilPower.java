package awakenedOne.powers;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class NihilPower extends AbstractAwakenedPower {

    public static final String NAME = NihilPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);


    public NihilPower(final AbstractCreature owner, int amount) {
        super(NAME, PowerType.DEBUFF, false, owner, null, amount);
        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
            flash();
            addToBot(new ApplyPowerAction(owner, AbstractDungeon.player, new ManaburnPower(owner, amount), amount));
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}