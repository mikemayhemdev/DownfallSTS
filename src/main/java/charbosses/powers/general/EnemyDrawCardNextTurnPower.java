package charbosses.powers.general;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.powers.AbstractPower;

import charbosses.actions.common.EnemyDrawCardAction;
import charbosses.bosses.AbstractCharBoss;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.*;

public class EnemyDrawCardNextTurnPower extends AbstractPower
{
    public static final String POWER_ID = "EvilWithin:Enemy Draw Card";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    
    public EnemyDrawCardNextTurnPower(final AbstractCreature owner, final int drawAmount) {
        this.name = EnemyDrawCardNextTurnPower.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = drawAmount;
        this.updateDescription();
        this.loadRegion("carddraw");
        this.priority = 20;
    }
    
    @Override
    public void updateDescription() {
        if (this.amount > 1) {
            this.description = EnemyDrawCardNextTurnPower.DESCRIPTIONS[0] + this.amount + EnemyDrawCardNextTurnPower.DESCRIPTIONS[1];
        }
        else {
            this.description = EnemyDrawCardNextTurnPower.DESCRIPTIONS[0] + this.amount + EnemyDrawCardNextTurnPower.DESCRIPTIONS[2];
        }
    }
    
    @Override
    public void atStartOfTurnPostDraw() {
        this.flash();
        this.addToBot(new EnemyDrawCardAction((AbstractCharBoss)this.owner, this.amount));
        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
    }
    
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Draw Card");
        NAME = EnemyDrawCardNextTurnPower.powerStrings.NAME;
        DESCRIPTIONS = EnemyDrawCardNextTurnPower.powerStrings.DESCRIPTIONS;
    }
}
