package charbosses.powers.cardpowers;


import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class EnemyMasterRealityPower extends AbstractPower {
    public static final String POWER_ID = "MasterRealityPower";
    private static final PowerStrings powerStrings;

    public EnemyMasterRealityPower(AbstractCreature owner) {
        this.name = powerStrings.NAME;
        this.ID = "MasterRealityPower";
        this.owner = owner;
        this.updateDescription();
        this.loadRegion("master_reality");
        this.type = PowerType.BUFF;
    }

    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0] + this.amount + powerStrings.DESCRIPTIONS[1];
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("MasterRealityPower");
    }
}
