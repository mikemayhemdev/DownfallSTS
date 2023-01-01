package awakenedOne.powers;

import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class EnemyHexedPower extends AbstractAwakenedPower {
    // intellij stuff buff
    public static final String NAME = EnemyHexedPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);

    public EnemyHexedPower(int amount) {
        super(NAME, PowerType.DEBUFF, false, AbstractDungeon.player, null, amount);
    }

    @Override
    public void onAfterCardPlayed(AbstractCard usedCard) {
        if (usedCard.type != AbstractCard.CardType.ATTACK) {
            flash();
            addToBot(new LoseHPAction(owner, owner, amount));
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}