package charbosses.powers.cardpowers;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.powers.AbstractPower;

import charbosses.actions.common.EnemyMakeTempCardInHandAction;
import charbosses.cards.colorless.EnShiv;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.cards.tempCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.core.*;

public class EnemyInfiniteBladesPower extends AbstractPower
{
    public static final String POWER_ID = "EvilWithin:Enemy Infinite Blades";
    private static final PowerStrings powerStrings;
    
    public EnemyInfiniteBladesPower(final AbstractCreature owner, final int bladeAmt) {
        this.name = EnemyInfiniteBladesPower.powerStrings.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = bladeAmt;
        this.updateDescription();
        this.loadRegion("infiniteBlades");
    }
    
    @Override
    public void atStartOfTurnPostDraw() {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.flash();
            this.addToBot(new EnemyMakeTempCardInHandAction(new EnShiv(), this.amount, false));
        }
    }
    
    @Override
    public void stackPower(final int stackAmount) {
        this.fontScale = 8.0f;
        this.amount += stackAmount;
    }
    
    @Override
    public void updateDescription() {
        if (this.amount > 1) {
            this.description = EnemyInfiniteBladesPower.powerStrings.DESCRIPTIONS[0] + this.amount + EnemyInfiniteBladesPower.powerStrings.DESCRIPTIONS[1];
        }
        else {
            this.description = EnemyInfiniteBladesPower.powerStrings.DESCRIPTIONS[0] + this.amount + EnemyInfiniteBladesPower.powerStrings.DESCRIPTIONS[2];
        }
    }
    
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Infinite Blades");
    }
}
