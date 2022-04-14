package charbosses.powers.cardpowers;

import charbosses.actions.unique.EnemyChangeStanceAction;
import charbosses.stances.AbstractEnemyStance;
import charbosses.stances.EnRealWrathStance;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class EnemyWrathNextTurnPower extends AbstractPower {
    public static final String POWER_ID = "downfall:WrathNextTurnPower";
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    private static final PowerStrings powerStrings;

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("downfall:WrathNextTurnPower");
        NAME = EnemyWrathNextTurnPower.powerStrings.NAME;
        DESCRIPTIONS = EnemyWrathNextTurnPower.powerStrings.DESCRIPTIONS;
    }

    public EnemyWrathNextTurnPower(final AbstractCreature owner) {
        this.name = EnemyWrathNextTurnPower.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.updateDescription();
        this.loadRegion("anger");
    }

    @Override
    public void atStartOfTurn() {
        this.flash();
        this.addToBot(new EnemyChangeStanceAction("Real Wrath"));
        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
    }

    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0];
    }

    public AbstractEnemyStance getWrathStance() {
        return new EnRealWrathStance();
    }
}