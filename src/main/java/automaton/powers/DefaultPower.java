package automaton.powers;

import automaton.actions.AddToFuncAction;
import automaton.cards.FunctionCard;
import automaton.vfx.FineTuningEffect;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DefaultPower extends AbstractAutomatonPower implements NonStackablePower, OnCompilePower {
    public static final String NAME = "Default";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public DefaultPower(int amount) {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, amount);
        canGoNegative = false;
        updateDescription();
    }

    @Override
    public void receiveCompile(AbstractCard function, boolean forGameplay) {
        if (forGameplay) {
            onSpecificTrigger();
            flash();

            for (int i = 0; i < amount; i++) {
                AbstractDungeon.effectList.add(new FineTuningEffect(function));
            }

            addToBot(new AddToFuncAction(((FunctionCard) function).cards().get(0).makeStatEquivalentCopy(), null));
            addToBot(new ReducePowerAction(owner, owner, this, 1));
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
