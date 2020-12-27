package automaton.powers;

import automaton.FunctionHelper;
import automaton.cards.MinorBeam;
import automaton.relics.OnCompileRelic;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class InfiniteBeamsPower extends AbstractAutomatonPower implements NonStackablePower {
    public static final String NAME = "InfiniteBeams";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public boolean upgraded;

    public InfiniteBeamsPower(int amount, boolean upgraded) {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, amount);
        this.upgraded = upgraded;
    }

    @Override
    public boolean isStackable(AbstractPower power) {
        if (power instanceof InfiniteBeamsPower) {
            return ((InfiniteBeamsPower) power).upgraded == this.upgraded;
        }
        return false;
    }

    @Override
    public void atStartOfTurn() {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            flash();
            AbstractCard q = new MinorBeam();
            if (upgraded) q.upgrade();
            addToBot(new MakeTempCardInHandAction(q, this.amount, false));
        }
    }


    @Override
    public void updateDescription() {
        if (upgraded) {
            if (amount > 1) {
                description =  DESCRIPTIONS[4] + amount + DESCRIPTIONS[5];
            }
            else {
                description = DESCRIPTIONS[1];
            }
        }
        else {
            if (amount > 1) {
                description =  DESCRIPTIONS[2] + amount + DESCRIPTIONS[3];
            }
            else {
                description = DESCRIPTIONS[0];
            }
        }
    }
}
