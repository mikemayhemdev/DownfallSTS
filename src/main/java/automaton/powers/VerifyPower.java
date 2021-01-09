package automaton.powers;

import automaton.cards.AbstractBronzeCard;
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
        flashWithoutSound();
        if (addition instanceof AbstractBronzeCard) {
            for (int i = 0; i < amount; i++)
                ((AbstractBronzeCard) addition).fineTune();
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
