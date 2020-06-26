package guardian.powers;


import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;


public class DontLeaveDefensiveModePower extends AbstractGuardianPower {
    public static final String POWER_ID = "Guardian:DontLeaveDefensiveModePower";
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static String[] DESCRIPTIONS;

    public DontLeaveDefensiveModePower(AbstractCreature owner, int amount) {

        this.ID = POWER_ID;
        this.owner = owner;
        this.setImage("bronzeOrbProtectionPowerInactive84.png", "bronzeOrbProtectionPowerInactive32.png");
        this.type = POWER_TYPE;
        DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;

        this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;
        this.amount = amount;

        updateDescription();

    }

    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0];
        } else {
            this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        }
    }
}
