package awakenedOne.powers;

import awakenedOne.actions.AllEnemyLoseHPAction;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class CrowsCallPower extends AbstractAwakenedPower {
    // intellij stuff buff
    public static final String NAME = CrowsCallPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);

    public CrowsCallPower(int amount) {
        super(NAME, PowerType.BUFF, false, AbstractDungeon.player, null, amount);
    }

    @Override
    public void atEndOfTurnPreEndTurnCards(boolean isPlayer) {
        for (AbstractCard q : AbstractDungeon.player.hand.group) {
            if (q.type == AbstractCard.CardType.CURSE || q.type == AbstractCard.CardType.STATUS || q.color == AbstractCard.CardColor.CURSE) {
                addToBot(new AbstractGameAction() {
                    @Override
                    public void update() {
                        isDone = true;
                        q.superFlash(Color.PURPLE);
                        addToTop(new AllEnemyLoseHPAction(amount));
                    }
                });
            }
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}