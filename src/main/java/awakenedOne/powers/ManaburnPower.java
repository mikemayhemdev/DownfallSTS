package awakenedOne.powers;

import awakenedOne.cards.Retaliation;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import hermit.patches.EnumPatch;

public class ManaburnPower extends AbstractAwakenedPower implements OnLoseEnergyPower {
    // intellij stuff buff

    public static final String NAME = ManaburnPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);


    public ManaburnPower(final AbstractCreature owner, int amount) {
        super(NAME, PowerType.DEBUFF, false, owner, null, amount);
        updateDescription();
    }


    @Override
    public void LoseEnergyAction(int gained) {
        this.flash();
        this.addToBot(new DamageAction(owner, new DamageInfo(owner, amount, DamageInfo.DamageType.HP_LOSS), EnumPatch.HERMIT_GHOSTFIRE));
    }

    @Override
    public void triggerMarks(AbstractCard card) {
        if (card.cardID.equals(Retaliation.ID)) {
            this.addToBot(new LoseHPAction(this.owner, (AbstractCreature)null, this.amount, EnumPatch.HERMIT_GHOSTFIRE));
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}