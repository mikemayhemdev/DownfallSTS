package awakenedOne.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class SongOfSorrowPower extends AbstractAwakenedPower {
    // intellij stuff buff
    public static final String NAME = SongOfSorrowPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);

    private boolean active = true;

    public SongOfSorrowPower(int amount) {
        super(NAME, PowerType.BUFF, false, AbstractDungeon.player, null, amount);
    }

    @Override
    public void onSpecificTrigger() {
        active = false;
        flash();
        this.addToBot(new DamageAllEnemiesAction((AbstractCreature) null, DamageInfo.createDamageMatrix(this.amount, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE, true));
    }

    public void updateDescription() {
        if (active) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + DESCRIPTIONS[2];
        } else {
            this.description = (DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + DESCRIPTIONS[3]);
        }

    }

}