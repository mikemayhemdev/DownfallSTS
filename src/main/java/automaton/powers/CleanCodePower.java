package automaton.powers;

import automaton.AutomatonMod;
import automaton.FunctionHelper;
import automaton.cards.AbstractBronzeCard;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static automaton.FunctionHelper.WITH_DELIMITER;

public class CleanCodePower extends AbstractAutomatonPower implements PreCardCompileEffectsPower {
    public static final String NAME = "CleanCode";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public CleanCodePower(int amount) {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, amount);
    }

    @Override
    public void receivePreCardCompileEffects(boolean forGameplay) {
        if (forGameplay) {
            flash();
            addToBot(new ReducePowerAction(owner, owner, this, 1));
        }
        for (AbstractCard q : FunctionHelper.held.group) {
            if (q.hasTag(AutomatonMod.BAD_COMPILE) && q instanceof AbstractBronzeCard) {
                ((AbstractBronzeCard) q).turnOffCompileStuff();
            }
        }
    }
}
