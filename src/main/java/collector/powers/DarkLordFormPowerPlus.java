package collector.powers;

import automaton.actions.RepeatCardAction;
import collector.cards.YouAreMine;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DarkLordFormPowerPlus extends AbstractCollectorPower {
    public static final String NAME = "DarkLordFormPlus";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public DarkLordFormPowerPlus() {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, 1);
    }

    @Override
    public void atStartOfTurnPostDraw() {
        flash();
        for (int i = 0; i < amount; i++) {
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    isDone = true;
                    AbstractMonster q = AbstractDungeon.getRandomMonster();
                    AbstractCard c = new YouAreMine();
                    c.upgrade();
                    addToTop(new RepeatCardAction(q, c));
                }
            });
        }
    }
}