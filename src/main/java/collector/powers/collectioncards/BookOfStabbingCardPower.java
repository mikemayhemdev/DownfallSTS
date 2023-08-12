package collector.powers.collectioncards;

import collector.powers.AbstractCollectorPower;
import collector.powers.DoomPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BookOfStabbingCardPower extends AbstractCollectorPower {
    public static final String NAME = "BookOfStabbingCard";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public BookOfStabbingCardPower(int amount) {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, amount);
    }

    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (damageAmount > 0 && target != this.owner && info.type == DamageInfo.DamageType.NORMAL && target instanceof AbstractMonster) {
            this.flash();
            this.addToTop(new ApplyPowerAction(target, this.owner, new DoomPower((AbstractMonster) target, this.amount), this.amount, true));
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}