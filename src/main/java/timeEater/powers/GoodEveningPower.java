package timeEater.powers;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import timeEater.ClockHelper;

public class GoodEveningPower extends AbstractTimePower {
    public static final String NAME = "GoodEvening";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public GoodEveningPower(int amount) {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, amount);
    }

    @Override
    public float modifyBlock(float blockAmount) {
        return ClockHelper.clock == 11 ? blockAmount * amount : blockAmount;
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type, AbstractCard card) {
        return ClockHelper.clock == 11 ? damage * amount : damage;
    }
}
