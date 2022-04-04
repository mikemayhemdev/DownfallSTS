package timeeater.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static timeeater.TimeEaterMod.makeID;

public class UnwoundPower extends AbstractTimeEaterPower {
    public static final String ID = makeID(UnwoundPower.class.getSimpleName());
    public static final PowerStrings strs = CardCrawlGame.languagePack.getPowerStrings(ID);

    public UnwoundPower() {
        super(ID,  PowerType.BUFF, true, AbstractDungeon.player, 1);
        isTwoAmount = true;
        amount2 = AbstractDungeon.player.currentHealth;
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        amount2 = AbstractDungeon.player.currentHealth;
    }

    @Override
    public void atStartOfTurnPostDraw() {
        this.flash();
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                owner.currentHealth = amount2;
            }
        });
        addToBot(new ReducePowerAction(owner, owner, this, 1));
    }

    @Override
    public void updateDescription() {
        description = (amount == 1 ? DESCRIPTIONS[0] + amount2 + DESCRIPTIONS[1] : DESCRIPTIONS[2] + amount + DESCRIPTIONS[3] + amount2 + DESCRIPTIONS[4]);
    }
}
