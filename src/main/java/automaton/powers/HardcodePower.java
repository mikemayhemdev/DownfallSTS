package automaton.powers;

import automaton.FunctionHelper;
import automaton.cards.FunctionCard;
import com.evacipated.cardcrawl.mod.stslib.StSLib;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.unique.AddCardToDeckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class HardcodePower extends AbstractAutomatonPower implements OnOutputFunctionPower {
    public static final String NAME = "Hardcode";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;
    private AbstractCard sourceCard;

    public HardcodePower(int amount, AbstractCard source) {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, amount);
        this.sourceCard = source;
    }

    @Override
    public boolean receiveOutputFunction() {
        flash();
        AbstractCard function2 = FunctionHelper.makeFunction(true);
        function2.resetAttributes();
        addToBot(new AddCardToDeckAction(function2));
        if (function2 instanceof FunctionCard) {
            for (AbstractCard q : ((FunctionCard) function2).cards()) {
                if (StSLib.getMasterDeckEquivalent(q) != null) {
                    AbstractDungeon.player.masterDeck.removeCard(StSLib.getMasterDeckEquivalent(q));
                }
            }
        }
        if (StSLib.getMasterDeckEquivalent(sourceCard) != null) {
            AbstractDungeon.player.masterDeck.removeCard(StSLib.getMasterDeckEquivalent(sourceCard));
        }
        addToBot(new ReducePowerAction(owner, owner, this, 1));
        return false;
    }
}
