package timeeater.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static timeeater.TimeEaterMod.makeID;

public class SpeedUpPower extends AbstractTimeEaterPower {
    public static final String ID = makeID(SpeedUpPower.class.getSimpleName());
    public static final PowerStrings strs = CardCrawlGame.languagePack.getPowerStrings(ID);

    public SpeedUpPower(int amount) {
        super(ID,  PowerType.BUFF, false, AbstractDungeon.player, amount);
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
