package awakenedOne.powers;

import awakenedOne.cards.tokens.Feather;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.PoisonPower;

public class ToxinWingPower extends AbstractAwakenedPower {
    // intellij stuff buff
    public static final String NAME = ToxinWingPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);

    public ToxinWingPower(int amount) {
        super(NAME, PowerType.BUFF, false, AbstractDungeon.player, null, amount);
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.cardID.equals(Feather.ID) && action.target != null) {
            flash();
            addToBot(new ApplyPowerAction(action.target, owner, new PoisonPower(action.target, owner, amount), amount));
        }
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];

    }

}