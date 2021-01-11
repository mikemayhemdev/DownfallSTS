package automaton.powers;

import automaton.cards.FunctionCard;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BronzeHeatsinkPower extends AbstractAutomatonPower {
    public static final String NAME = "BronzeHeatsink";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public BronzeHeatsinkPower(int amount) {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, amount);
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        if (card instanceof FunctionCard) {
            flash();
            addToBot(new DrawCardAction(amount));
        }
    }
}
