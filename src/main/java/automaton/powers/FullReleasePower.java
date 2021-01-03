package automaton.powers;

import automaton.actions.RepeatCardAction;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.CardModifierPatches;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class FullReleasePower extends AbstractAutomatonPower implements NonStackablePower {
    public static final String NAME = "FullRelease";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    private AbstractCard stored;

    public FullReleasePower(AbstractCard c) {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, -1);
        stored = c;
        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            flash();
            AbstractCard q = stored.makeStatEquivalentCopy();
            addToBot(new RepeatCardAction(q));
        }
    }


    @Override
    public void updateDescription() {
        if (stored == null) {
            description = "ERROR - PLEASE REPORT BUG";
        }
        else {
            description = DESCRIPTIONS[0] + CardModifierPatches.CardModifierOnCreateDescription.calculateRawDescription(stored, stored.rawDescription);
        }
    }
}
