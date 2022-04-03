package timeeater.powers;

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import theHexaghost.powers.TimeStopPower;

import static timeeater.TimeEaterMod.makeID;

public class TimeWarpPower extends AbstractTimeEaterPower implements NonStackablePower {
    public static final String ID = makeID(TimeWarpPower.class.getSimpleName());
    public static final PowerStrings strs = CardCrawlGame.languagePack.getPowerStrings(ID);

    public TimeWarpPower() {
        super(ID, strs.NAME, PowerType.BUFF, false, AbstractDungeon.player, 12);
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        addToBot(new ReducePowerAction(owner, owner, this, 1));
        if (amount == 1) {
            addToBot(new ApplyPowerAction(owner, owner, new TimeStopPower(owner, 1)));
        }
    }

    @Override
    public void updateDescription() {
        description = (amount == 1 ? DESCRIPTIONS[0] : (DESCRIPTIONS[1] + amount + DESCRIPTIONS[2]));
    }
}
