package expansioncontent.powers;

import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.powers.AbstractPower;
import expansioncontent.expansionContentMod;

public class VexVinciblePower extends TwoAmountPower {
    public static final String POWER_ID = expansionContentMod.makeID("VexVinciblePower");

    public static final String NAME = (CardCrawlGame.languagePack.getPowerStrings(POWER_ID)).NAME;

    public static final String[] DESCRIPTIONS = (CardCrawlGame.languagePack.getPowerStrings(POWER_ID)).DESCRIPTIONS;

    public int maxAmt;

    public int variable;

    private boolean activated = false;

    public VexVinciblePower(AbstractCreature owner, int amount, int amount2, int amount3) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.amount2 = amount2;
        this.variable = amount3;
        this.maxAmt = amount;
        updateDescription();
        loadRegion("heartDef");
        this.priority = 99;
        this.activated = false;
    }

    public int onLoseHp(int damageAmount) {
        if (damageAmount > this.amount2) {
            damageAmount = this.amount2;
            this.activated = true;
        }
        this.amount2 -= damageAmount;
        if (this.amount2 < 0)
            this.amount2 = 0;
        updateDescription();
        return damageAmount;
    }

    public void atEndOfRound() {
        if (this.activated)
            addToBot((AbstractGameAction)new ReducePowerAction(this.owner, this.owner, (AbstractPower)this, 1));
        this.amount2 = this.variable;
    }

    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0] + this.amount2 + DESCRIPTIONS[1];
        } else {
            this.description = DESCRIPTIONS[2] + this.amount2 + DESCRIPTIONS[3] + this.amount + DESCRIPTIONS[4];
        }
    }
}
