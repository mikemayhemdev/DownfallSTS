package automaton.powers;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class MaxOutputPower extends AbstractAutomatonPower {
    public static final String NAME = "MaxOutput";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public MaxOutputPower(int amount) {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, amount);
        AbstractDungeon.player.gameHandSize += amount;
    }

    @Override
    public void atStartOfTurn() {
        flash();
        addToBot(new MakeTempCardInDrawPileAction(new Dazed(), amount, true, true));
    }

    @Override
    public void onRemove() {
        AbstractDungeon.player.gameHandSize -= amount;
    }


    @Override
    public void updateDescription() {
        description = amount == 1 ? DESCRIPTIONS[0] : DESCRIPTIONS[1] + amount + DESCRIPTIONS[2] + amount + DESCRIPTIONS[3];
    }
}
