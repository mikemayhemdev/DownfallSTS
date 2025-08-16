package awakenedOne.powers;

import awakenedOne.cards.tokens.Ceremony;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ThaumaturgyPower extends AbstractAwakenedPower {
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

//    @Override
//    public boolean onReceivePower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
//            if ((power instanceof StrengthPower) && (power.amount > 0)) {
//                flash();
//                addToBot(new GainBlockAction(owner, this.amount));
////                addToBot(new GainBlockAction(owner, this.amount * power.amount));
//                updateDescription();
//            }
//        return true;
//    }

    @Override
    public void atStartOfTurn() {
        flash();
        this.addToBot(new MakeTempCardInHandAction(new Ceremony(), 1, false));
        this.amount--;
        if (this.amount <= 0) {
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
        updateDescription();
    }


    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

}