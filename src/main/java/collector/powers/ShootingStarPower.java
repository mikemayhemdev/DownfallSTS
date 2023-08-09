package collector.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.ExhaustToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ShootingStarPower extends AbstractCollectorPower implements OnPyrePower {
    public static final String NAME = "ShootingStar";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public ShootingStarPower() {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, 1);
    }

    int pyresThisTurn = 0;

    @Override
    public void atStartOfTurn() {
        pyresThisTurn = 0;
    }

    @Override
    public void onPyre(AbstractCard card) {
        if (pyresThisTurn < amount) {
            flash();
            pyresThisTurn++;
            addToBot(new ExhaustToHandAction(card));
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    isDone = true;
                    card.freeToPlayOnce = true;
                }
            });
        }
    }

    @Override
    public void updateDescription() {
        if (this.amount == 1) {
            description = DESCRIPTIONS[0];
        } else {
            description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
        }
    }

}