package downfall.powers.neowpowers;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;


public class NeowInvinciblePower extends AbstractPower {
    public static final String POWER_ID = "Invincible";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    private int maxAmt;

    public NeowInvinciblePower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = "Invincible";
        this.owner = owner;
        this.amount = amount;
        this.maxAmt = amount;
        this.updateDescription();
        this.loadRegion("heartDef");
        this.priority = 99;
    }

    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        if (damageAmount > this.amount) {
            damageAmount = this.amount;
        }

        this.amount -= damageAmount;
        if (this.amount <= 0) {
            if (AbstractDungeon.player.hasPower(HeartsFavorPower.POWER_ID)) {
                AbstractDungeon.player.getPower(HeartsFavorPower.POWER_ID).onSpecificTrigger();
            }
        }
        if (this.amount < 0) {
            this.amount = 0;
        }

        this.updateDescription();
        return damageAmount;
    }

    public void atStartOfTurn() {
        this.amount = this.maxAmt;
        this.updateDescription();
    }

    public void updateDescription() {
        if (this.amount <= 0) {
            this.description = DESCRIPTIONS[2];
        } else {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        }

    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Invincible");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
