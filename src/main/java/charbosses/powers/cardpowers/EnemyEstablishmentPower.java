package charbosses.powers.cardpowers;

import charbosses.actions.unique.EnemyEstablishmentPowerAction;
import com.megacrit.cardcrawl.actions.unique.EstablishmentPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class EnemyEstablishmentPower extends AbstractPower {
    public static final String POWER_ID = "EstablishmentPower";
    private static final PowerStrings powerStrings;

    public EnemyEstablishmentPower(AbstractCreature owner, int strengthAmount) {
        this.name = powerStrings.NAME;
        this.ID = "EstablishmentPower";
        this.owner = owner;
        this.amount = strengthAmount;
        this.updateDescription();
        this.loadRegion("establishment");
        this.priority = 25;
    }

    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0] + this.amount + powerStrings.DESCRIPTIONS[1];
    }

    public void atEndOfTurn(boolean isPlayer) {
        this.flash();
        this.addToBot(new EnemyEstablishmentPowerAction(this.amount));
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("EstablishmentPower");
    }
}
