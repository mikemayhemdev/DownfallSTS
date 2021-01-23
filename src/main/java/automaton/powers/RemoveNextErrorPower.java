package automaton.powers;

import automaton.AutomatonMod;
import automaton.cards.AbstractBronzeCard;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class RemoveNextErrorPower extends AbstractAutomatonPower implements OnAddToFuncPower {
    public static final String NAME = "RemoveNextError";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public RemoveNextErrorPower(int amount) {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, amount);
    }

    @Override
    public void receiveAddToFunc(AbstractCard addition) {
        if (addition instanceof AbstractBronzeCard && addition.hasTag(AutomatonMod.BAD_COMPILE)) {
            ((AbstractBronzeCard) addition).turnOffCompileStuff();
            flash();
            addToTop(new ReducePowerAction(owner, owner, this.ID, 1));
        }
    }

    @Override
    public void updateDescription() {
        description = amount == 1 ? DESCRIPTIONS[0] : DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
    }
}
