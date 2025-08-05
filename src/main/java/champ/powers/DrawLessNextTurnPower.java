package champ.powers;

import basemod.interfaces.CloneablePowerInterface;
import champ.ChampMod;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class DrawLessNextTurnPower extends AbstractPower implements CloneablePowerInterface {

    public static final String POWER_ID = ChampMod.makeID("DrawLessNextTurnPower");

 private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public DrawLessNextTurnPower(final int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;
        this.amount = amount;
        this.type = PowerType.DEBUFF;
        this.isTurnBased = true;
        this.loadRegion("lessdraw");

        this.updateDescription();
    }

    @Override
    public void onInitialApplication() {
        AbstractDungeon.player.gameHandSize -= amount;
    }

    @Override
    public void stackPower(int stackAmount) {
        AbstractDungeon.player.gameHandSize -= stackAmount;
        super.stackPower(stackAmount);
    }

    @Override
    public void onRemove() {
        AbstractDungeon.player.gameHandSize += amount;
    }

    @Override
    public void reducePower(int reduceAmount) {
        AbstractDungeon.player.gameHandSize += Math.min(reduceAmount,amount);
        super.reducePower(reduceAmount);
    }

    @Override
    public void atStartOfTurnPostDraw() {
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }


    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + (amount == 1 ? DESCRIPTIONS[1] : DESCRIPTIONS[2]);
    }

    @Override
    public AbstractPower makeCopy() {
        return new DrawLessNextTurnPower(amount);
    }
}