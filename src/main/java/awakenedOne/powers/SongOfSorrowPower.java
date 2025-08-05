package awakenedOne.powers;

import awakenedOne.actions.AllEnemyLoseHPAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class SongOfSorrowPower extends AbstractAwakenedPower {
    // intellij stuff buff
    public static final String NAME = SongOfSorrowPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);

    private final boolean active = true;

    public SongOfSorrowPower(int amount) {
        super(NAME, PowerType.BUFF, false, AbstractDungeon.player, null, amount);
    }

    @Override
    public void onSpecificTrigger() {
        flash();
        this.addToBot(new AllEnemyLoseHPAction(amount, AbstractGameAction.AttackEffect.FIRE));
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

}