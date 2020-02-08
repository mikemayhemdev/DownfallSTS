package charbosses.powers.cardpowers;

import charbosses.actions.common.EnemyMakeTempCardInHandAction;
import charbosses.cards.colorless.EnShiv;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class EnemyInfiniteBladesPower extends AbstractPower {
    public static final String POWER_ID = "EvilWithin:Enemy Infinite Blades";
    private static final PowerStrings powerStrings;

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Infinite Blades");
    }

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
        } else {
            this.description = EnemyInfiniteBladesPower.powerStrings.DESCRIPTIONS[0] + this.amount + EnemyInfiniteBladesPower.powerStrings.DESCRIPTIONS[2];
        }
    }
}
