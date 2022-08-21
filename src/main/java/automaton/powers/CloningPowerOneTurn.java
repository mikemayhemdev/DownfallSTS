package automaton.powers;

import automaton.actions.AddToFuncAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class CloningPowerOneTurn extends AbstractAutomatonPower implements PostAddToFuncPower {
    public static final String NAME = "CloningOneTurn";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public CloningPowerOneTurn(int amount) {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, amount);
    }

    @Override
    public void receivePostAddToFunc(AbstractCard addition) {
        flash();
        for (int i = 0; i < amount; i++) {
            addToBot(new AddToFuncAction(addition.makeStatEquivalentCopy(), null));
        }
        addToTop(new RemoveSpecificPowerAction(owner, owner, this.ID));
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }
}
