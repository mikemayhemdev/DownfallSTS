package theHexaghost.powers;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theHexaghost.HexaMod;

public class LoseIntesityPower extends AbstractPower {

    public static final String POWER_ID = HexaMod.makeID("LoseIntensityPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public LoseIntesityPower(AbstractCreature owner, int newAmount) {
        this.name = NAME;
        this.ID = "LoseIntensity";
        this.owner = owner;
        this.amount = newAmount;
        this.type = AbstractPower.PowerType.DEBUFF;
        updateDescription();
        loadRegion("flex");
    }


    public void updateDescription() {
        /* 27 */     this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        /*    */   }


    public void atEndOfTurn(boolean isPlayer) {
        flash();
        addToBot(new ApplyPowerAction(this.owner, this.owner, new EnhancePower(-this.amount), -this.amount));
        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
    }
}

