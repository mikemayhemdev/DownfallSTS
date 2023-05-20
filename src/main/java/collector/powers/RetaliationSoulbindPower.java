package collector.powers;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.util.Wiz.applyToEnemyTop;

public class RetaliationSoulbindPower extends AbstractCollectorPower {
    public static final String NAME = "RetaliationSoulbind";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public RetaliationSoulbindPower(int amount) {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, amount);
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.type == DamageInfo.DamageType.NORMAL && info.owner != AbstractDungeon.player) {
            flash();
            applyToEnemyTop((AbstractMonster) info.owner, new SoulbindPower((AbstractMonster) info.owner, amount));
        }
        return damageAmount;
    }
}