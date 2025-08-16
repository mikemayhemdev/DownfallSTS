package awakenedOne.powers;

import awakenedOne.actions.AllEnemyLoseHPAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class FlarePower extends AbstractAwakenedPower {
    // intellij stuff buff
    public static final String NAME = FlarePower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);

    public FlarePower(int amount) {
        super(NAME, PowerType.BUFF, false, AbstractDungeon.player, null, amount);
    }

    @Override
    public void onAfterCardPlayed(AbstractCard usedCard) {
        if (usedCard.type != AbstractCard.CardType.ATTACK) {
            this.flash();
            this.addToBot(new AllEnemyLoseHPAction(amount, AbstractGameAction.AttackEffect.FIRE));
        }
    }

    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
