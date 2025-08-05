package awakenedOne.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class SpellshieldPower extends AbstractAwakenedPower {
    // intellij stuff buff
    public static final String NAME = SpellshieldPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);

    public SpellshieldPower(int amount) {
        super(NAME, PowerType.BUFF, false, AbstractDungeon.player, null, amount);
    }

    public void atEndOfTurnPreEndTurnCards(boolean isPlayer) {
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            public void update() {
                isDone = true;
                if (AbstractDungeon.player.hand.group.stream().anyMatch(c -> c.selfRetain || c.retain))
                    flash();
                AbstractDungeon.player.hand.group.stream()
                        .filter(c -> c.selfRetain || c.retain)
                        .forEach(c -> AbstractDungeon.actionManager.addToBottom(new GainBlockAction(owner, owner, SpellshieldPower.this.amount, true)));
            }
        });
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}