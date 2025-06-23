package awakenedOne.powers;

import champ.powers.ParryPower;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import hermit.patches.EnumPatch;

public class NihilRetriggerPower extends AbstractAwakenedPower {

    public static final String NAME = NihilRetriggerPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);


    public NihilRetriggerPower(final AbstractCreature owner, int amount) {
        super(NAME, PowerType.DEBUFF, false, owner, null, amount);
        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        if (owner.hasPower(ManaburnPower.POWER_ID)) {
            flash();
            for (int i = 0; i < this.amount; ++i) {
                this.addToBot(new DamageAction(owner, new DamageInfo(owner, owner.getPower(ManaburnPower.POWER_ID).amount, DamageInfo.DamageType.HP_LOSS), EnumPatch.HERMIT_GHOSTFIRE));
            }
        }
    }

    @Override
    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0] + LocalizedStrings.PERIOD;
        } else {
            this.description = (DESCRIPTIONS[1] + "#b" + this.amount + DESCRIPTIONS[2]);
        }
    }

}