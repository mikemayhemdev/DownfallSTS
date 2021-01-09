package automaton.powers;

import automaton.cards.SpaghettiCode;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class LibraryPower extends AbstractAutomatonPower {
    public static final String NAME = "Library";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public LibraryPower(int amount) {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, amount);
    }

    @Override
    public void atStartOfTurn() {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            flash();
            for (int i = 0; i < amount; i++) {
                AbstractCard qCardGet = SpaghettiCode.getRandomEncode();
                qCardGet.modifyCostForCombat(-99);
                addToBot(new MakeTempCardInHandAction(qCardGet, true));
            }
        }
    }
}
