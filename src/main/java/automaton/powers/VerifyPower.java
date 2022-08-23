package automaton.powers;

import automaton.cardmods.CardEffectsCardMod;
import automaton.cards.AbstractBronzeCard;
import automaton.cards.FunctionCard;
import automaton.vfx.FineTuningEffect;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class VerifyPower extends AbstractAutomatonPower implements OnAddToFuncPower {
    public static final String NAME = "Verify";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public VerifyPower(int amount) {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, amount);
    }


    @Override
    public void receiveAddToFunc(AbstractCard addition) {
        for (int i = 0; i < amount; i++) {
            ((AbstractBronzeCard)addition).fineTune(false, false);
        }
    }

    @Override
    public void updateDescription() {

        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];

    }
}
