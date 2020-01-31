package charbosses.powers.general;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.powers.AbstractPower;

import charbosses.bosses.AbstractCharBoss;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.core.*;

public class EnemyEnergizedPower extends AbstractPower
{
    public static final String POWER_ID = "EvilWithin:Enemy Energized";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    
    public EnemyEnergizedPower(final AbstractCreature owner, final int energyAmt) {
        this.name = EnemyEnergizedPower.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = energyAmt;
        if (this.amount >= 999) {
            this.amount = 999;
        }
        this.updateDescription();
        this.loadRegion("energized_green");
    }
    
    @Override
    public void stackPower(final int stackAmount) {
        super.stackPower(stackAmount);
        if (this.amount >= 999) {
            this.amount = 999;
        }
    }
    
    @Override
    public void updateDescription() {
        if (this.amount == 1) {
            this.description = EnemyEnergizedPower.DESCRIPTIONS[0] + this.amount + EnemyEnergizedPower.DESCRIPTIONS[1];
        }
        else {
            this.description = EnemyEnergizedPower.DESCRIPTIONS[0] + this.amount + EnemyEnergizedPower.DESCRIPTIONS[2];
        }
    }
    
    @Override
    public void onEnergyRecharge() {
        this.flash();
        AbstractCharBoss.boss.gainEnergy(this.amount);
        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
    }
    
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Energized");
        NAME = EnemyEnergizedPower.powerStrings.NAME;
        DESCRIPTIONS = EnemyEnergizedPower.powerStrings.DESCRIPTIONS;
    }
}
