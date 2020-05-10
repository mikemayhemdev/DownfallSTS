package charbosses.powers.general;

import charbosses.actions.common.EnemyDrawCardAction;
import charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class EnemyDrawCardNextTurnPower extends AbstractPower {
    public static final String POWER_ID = "downfall:Enemy Draw Card";
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    private static final PowerStrings powerStrings;

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Draw Card");
        NAME = EnemyDrawCardNextTurnPower.powerStrings.NAME;
        DESCRIPTIONS = EnemyDrawCardNextTurnPower.powerStrings.DESCRIPTIONS;
    }

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
        } else {
            this.description = EnemyDrawCardNextTurnPower.DESCRIPTIONS[0] + this.amount + EnemyDrawCardNextTurnPower.DESCRIPTIONS[2];
        }
    }

    @Override
    public void atStartOfTurnPostDraw() {
        this.flash();
        this.addToBot(new EnemyDrawCardAction((AbstractCharBoss) this.owner, this.amount));
        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
    }
}
