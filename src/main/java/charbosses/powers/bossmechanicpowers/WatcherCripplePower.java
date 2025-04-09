

package charbosses.powers.bossmechanicpowers;

import charbosses.bosses.AbstractCharBoss;
import charbosses.stances.EnRealWrathStance;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

public class WatcherCripplePower extends AbstractBossMechanicPower {
    public static final String POWER_ID = "downfall:WatcherCripplePower";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;

    private boolean firstused = false;
    private boolean secondused = false;
    private boolean thirdused = false;

    public static final int LOSE_1_STRENGTH_PER_X_HP = 25;

    public WatcherCripplePower(AbstractCreature owner, int newAmount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = newAmount;
        this.updateDescription();
        loadRegion("curiosity");
    }

    public void updateDescription() {
        if (this.amount > 0) {
            this.description = DESCRIPTIONS[0] +  DESCRIPTIONS[1];
        } else {
            this.description = DESCRIPTIONS[2];
        }
    }

    @Override
    public int onLoseHp(int damageAmount) {
        this.flash();
        stackPower(damageAmount * -1);
        this.updateDescription();
        return super.onLoseHp(damageAmount);
    }

    @Override
    public void onSpecificTrigger() {
        if (amount <= 2 * LOSE_1_STRENGTH_PER_X_HP && !firstused) {
            firstused = true;
            addToBot(new VFXAction(new LightningEffect(this.owner.hb.cX, this.owner.hb.cY)));
            addToBot((AbstractGameAction)new SFXAction("THUNDERCLAP", 0.05F));
            this.addToBot(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, -1), -1, true, AbstractGameAction.AttackEffect.NONE));
        }
        if (amount <= LOSE_1_STRENGTH_PER_X_HP && !secondused) {
            secondused = true;
            addToBot(new VFXAction(new LightningEffect(this.owner.hb.cX, this.owner.hb.cY)));
            addToBot((AbstractGameAction)new SFXAction("THUNDERCLAP", 0.05F));
            this.addToBot(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, -1), -1, true, AbstractGameAction.AttackEffect.NONE));
        }
        if (amount <= 0 && !thirdused) {
            thirdused = true;
            addToBot(new VFXAction(new LightningEffect(this.owner.hb.cX, this.owner.hb.cY)));
            addToBot((AbstractGameAction)new SFXAction("THUNDERCLAP", 0.05F));
            this.addToBot(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, -1), -1, true, AbstractGameAction.AttackEffect.NONE));
        }
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if (amount <= 2 * LOSE_1_STRENGTH_PER_X_HP) {
            if (this.owner != null) {
                onSpecificTrigger();
            }
        }
        if (amount <= LOSE_1_STRENGTH_PER_X_HP) {
            if (this.owner != null) {
                onSpecificTrigger();
            }
        }
        if (amount <= 0) {
            if (this.owner != null) {
                onSpecificTrigger();
            }
        }
    }

    @Override
    public void atEndOfRound() {
        super.atEndOfRound();
        this.amount = 3 * LOSE_1_STRENGTH_PER_X_HP;
        this.thirdused = false;
        this.secondused = false;
        this.firstused = false;
    }
  @Override
    public float atDamageReceive(float damage, DamageInfo.DamageType damageType) {

        if (damage > 1.0F) {
            if (this.owner instanceof AbstractCharBoss) {
                if (((AbstractCharBoss)owner).stance instanceof EnRealWrathStance) {
                    return damage * 2.0F;
                }
            }
        }
        return damage;
    }
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
