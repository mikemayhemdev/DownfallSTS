package automaton.powers;

import automaton.FunctionHelper;
import automaton.actions.AddToFuncAction;
import automaton.cardmods.CardEffectsCardMod;
import automaton.cardmods.SavableCardEffectsCardMod;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.unique.AddCardToDeckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardSave;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class HardcodePower extends AbstractAutomatonPower implements OnOutputFunctionPower {
    public static final String NAME = "Hardcode";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public HardcodePower(int amount) {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, amount);
    }

    @Override
    public boolean receiveOutputFunction() {
        flash();
        AbstractCard function2 = FunctionHelper.makeFunction(true);
        ArrayList<AbstractCardModifier> badThingsList = new ArrayList<>();
        for (AbstractCardModifier c : CardModifierManager.getModifiers(function2, CardEffectsCardMod.ID)) {
            if (c instanceof CardEffectsCardMod) {
                badThingsList.add(c);
                CardModifierManager.addModifier(function2, new SavableCardEffectsCardMod(new CardSave(((CardEffectsCardMod) c).stored.cardID, ((CardEffectsCardMod) c).stored.timesUpgraded, ((CardEffectsCardMod) c).stored.misc)));
            }
        }
        for (AbstractCardModifier c : badThingsList) {
            CardModifierManager.removeSpecificModifier(function2, c, true);
        }
        function2.applyPowers();
        addToBot(new AddCardToDeckAction(function2));
        addToBot(new ReducePowerAction(owner, owner, this, 1));
        return false;
    }
}
