package timeeater.powers;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import timeeater.suspend.SuspendHelper;

import static timeeater.TimeEaterMod.makeID;

public class FuturePlansPower extends AbstractTimeEaterPower {
    public static final String ID = makeID(FuturePlansPower.class.getSimpleName());
    public static final PowerStrings strs = CardCrawlGame.languagePack.getPowerStrings(ID);

    public FuturePlansPower(int amount) {
        super(ID, strs.NAME, PowerType.DEBUFF, false, AbstractDungeon.player, amount);
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        addToBot(new SelectCardsInHandAction(amount, DESCRIPTIONS[3], (cards) -> {
            for (AbstractCard q : cards) {
                AbstractDungeon.player.hand.removeCard(q);
                SuspendHelper.suspend(q);
            }
        }));
    }

    @Override
    public void updateDescription() {
        description = (amount == 1 ? DESCRIPTIONS[0] : (DESCRIPTIONS[1] + amount + DESCRIPTIONS[2]));
    }
}
