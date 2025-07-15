package awakenedOne.powers;

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnReceivePowerPower;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class ThaumaturgyPower extends AbstractAwakenedPower implements OnReceivePowerPower {
    // intellij stuff buff
    public static final String NAME = ThaumaturgyPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);

    public ThaumaturgyPower(int amount) {
        super(NAME, PowerType.BUFF, false, AbstractDungeon.player, null, amount);
    }

//    @Override
//    public void onAfterCardPlayed(AbstractCard card) {
//        if (card.type == AbstractCard.CardType.POWER) {
//            this.flash();
//            this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new PlatedArmorPower(AbstractDungeon.player, amount), amount));
//        }
//    }

    @Override
    public boolean onReceivePower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
            if ((power instanceof StrengthPower) && (power.amount > 0)) {
                flash();
                addToBot(new GainBlockAction(owner, this.amount*power.amount));
//                addToBot(new GainBlockAction(owner, this.amount * power.amount));
                updateDescription();
            }
        return true;
    }


    public void updateDescription() {
    this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

}