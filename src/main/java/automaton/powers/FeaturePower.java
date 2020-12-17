package automaton.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class FeaturePower extends AbstractAutomatonPower {
    public static final String NAME = "Feature";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public FeaturePower(int amount) {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, amount);
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        if (card.type == AbstractCard.CardType.STATUS || card.type == AbstractCard.CardType.CURSE) {
            flash();
            applyToSelf(new StrengthPower(owner, amount));
            applyToSelf(new LoseStrengthPower(owner, amount));
            applyToSelf(new DexterityPower(owner, amount));
            applyToSelf(new LoseDexterityPower(owner, amount));
            //Imagine how much time could be saved with some kind of function that could be used to make any power temporary.
            //IE: applyTempPower(new StrengthPower(owner, amount)):
            //Function applies the power, then uses some abstract Java trickery to make another power, called Lose [powername]
            //which automatically activates at turn end and removes the associated power.
            //You could do stuff like "Gain 1 Temporary Echo-Form after losing 10 HP in a turn."
            //Ideally, the same thing could be done for "Next-Turn Powers".
            //Like Phantasmal Blade, etc.
            //Combine the two and you could do..
            //Next turn, gain 1 Temporary Strength.
            //But in two lines, with no new powers!
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
    }
}
