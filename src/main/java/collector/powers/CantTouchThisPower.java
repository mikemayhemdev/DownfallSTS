package collector.powers;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.util.Wiz.applyToEnemy;
import static collector.util.Wiz.applyToEnemyTop;

public class CantTouchThisPower extends AbstractCollectorPower {
    public static final String NAME = "CantTouchThis";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public CantTouchThisPower(int amount) {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, amount);
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (damageAmount <= owner.currentBlock && info.owner instanceof AbstractMonster && info.type == DamageInfo.DamageType.NORMAL) {
            flash();
            applyToEnemyTop((AbstractMonster) info.owner, new DoomPower((AbstractMonster) info.owner, amount));
        }
        return damageAmount;
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}