package downfall.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class EnemyDemonFormPower extends AbstractPower {
    public static final String POWER_ID = "Demon Form";
    private static final PowerStrings powerStrings;

    public EnemyDemonFormPower(AbstractCreature owner, int strengthAmount) {
        this.name = powerStrings.NAME;
        this.ID = "Demon Form";
        this.owner = owner;
        this.amount = strengthAmount;
        this.updateDescription();
        this.loadRegion("demonForm");
    }

    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0] + this.amount + powerStrings.DESCRIPTIONS[1];
    }

    public void atEndOfRound() {
        this.flash();
        this.addToBot(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, this.amount), this.amount));
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Demon Form");
    }
}
