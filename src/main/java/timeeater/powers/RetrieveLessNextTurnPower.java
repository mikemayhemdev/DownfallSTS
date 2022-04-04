package timeeater.powers;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static timeeater.TimeEaterMod.makeID;

public class RetrieveLessNextTurnPower extends AbstractTimeEaterPower {
    public static final String ID = makeID(RetrieveLessNextTurnPower.class.getSimpleName());
    public static final PowerStrings strs = CardCrawlGame.languagePack.getPowerStrings(ID);

    public RetrieveLessNextTurnPower(int amount) {
        super(ID,  PowerType.DEBUFF, true, AbstractDungeon.player, amount);
    }

    @Override
    public void atStartOfTurnPostDraw() {
        this.flash();
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }

    @Override
    public void updateDescription() {
        description = (amount == 1 ? DESCRIPTIONS[0] : (DESCRIPTIONS[1] + amount + DESCRIPTIONS[2]));
    }
}
