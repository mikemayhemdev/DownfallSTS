package charbosses.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class BossIntangiblePower extends AbstractPower {
    public static final String POWER_ID = "Intangible";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;

    public BossIntangiblePower(AbstractCreature owner, int turns) {
        this.name = NAME;
        this.ID = "Intangible";
        this.owner = owner;
        this.amount = turns;
        this.updateDescription();
        this.loadRegion("intangible");
        this.priority = 75;
    }

    public void playApplyPowerSfx() {
        CardCrawlGame.sound.play("POWER_INTANGIBLE", 0.05F);
    }

    public float atDamageFinalReceive(float damage, DamageType type) {
        if (damage > 1.0F) {
            damage = 1.0F;
        }

        return damage;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    public void atEndOfTurn(boolean isPlayer) {
        this.flash();
        if (this.amount == 0) {
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, "Intangible"));
        } else {
            this.addToBot(new ReducePowerAction(this.owner, this.owner, "Intangible", 1));
        }
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Intangible");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
