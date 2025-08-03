package awakenedOne.powers;

import awakenedOne.actions.ConjureAction;
import awakenedOne.cards.tokens.spells.AbstractSpellCard;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static awakenedOne.util.Wiz.atb;

public class EmpressPower extends AbstractAwakenedPower implements NonStackablePower {
    // intellij stuff buff
    public static final String NAME = EmpressPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);

    public EmpressPower() {
        super(NAME, PowerType.BUFF, false, AbstractDungeon.player, null, 4);
        updateDescription();
    }

    @Override
    public void onSpecificTrigger() {
        amount -= 1;
        if (amount == 0) {
            flash();
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
            amount = 4;
        }
    }

    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

}