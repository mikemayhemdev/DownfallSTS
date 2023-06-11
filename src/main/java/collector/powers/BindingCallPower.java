package collector.powers;

import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnLoseTempHpPower;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static collector.util.Wiz.applyToEnemy;
import static collector.util.Wiz.forAllMonstersLiving;

public class BindingCallPower extends AbstractCollectorPower implements OnLoseTempHpPower {
    public static final String NAME = "BindingCall";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public BindingCallPower(int amount) {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, amount);
    }

    @Override
    public int onLoseTempHp(DamageInfo damageInfo, int i) {
        if (i >= TempHPField.tempHp.get(owner)) {
            flash();
            forAllMonstersLiving(q -> applyToEnemy(q, new DoomPower(q, amount)));
        }
        return i;
    }
}