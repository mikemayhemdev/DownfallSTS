

package charbosses.powers.bossmechanicpowers;

import charbosses.bosses.Watcher.CharBossWatcher;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

public class WatcherCripplePower extends AbstractBossMechanicPower {
    public static final String POWER_ID = "downfall:WatcherCripplePower";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;

    private boolean firstused = false;
    private boolean secondused = false;
    private boolean thirdused = false;

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
        if (amount <= 50 && firstused == false ) {
            firstused = true;
            addToBot((AbstractGameAction)new VFXAction((AbstractGameEffect)new LightningEffect(this.owner.hb.cX, this.owner.hb.cY)));
            this.addToBot(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, -1), -1, true, AbstractGameAction.AttackEffect.NONE));
        }
        if (amount <= 25 && secondused == false) {
            secondused = true;
            addToBot((AbstractGameAction)new VFXAction((AbstractGameEffect)new LightningEffect(this.owner.hb.cX, this.owner.hb.cY)));
            this.addToBot(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, -1), -1, true, AbstractGameAction.AttackEffect.NONE));
        }
        if (amount <= 0&& thirdused == false ) {
            thirdused = true;
            addToBot((AbstractGameAction)new VFXAction((AbstractGameEffect)new LightningEffect(this.owner.hb.cX, this.owner.hb.cY)));
            this.addToBot(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, -1), -1, true, AbstractGameAction.AttackEffect.NONE));
        }
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if (amount <= 50) {
            CharBossWatcher cb = (CharBossWatcher) this.owner;
            if (cb != null) {
                if (cb.powerhouseTurn) {
                    onSpecificTrigger();
                }
            }
        }
        if (amount <= 25) {
            CharBossWatcher cb = (CharBossWatcher) this.owner;
            if (cb != null) {
                if (cb.powerhouseTurn) {
                    onSpecificTrigger();
                }
            }
        }
        if (amount <= 0) {
            CharBossWatcher cb = (CharBossWatcher) this.owner;
            if (cb != null) {
                if (cb.powerhouseTurn) {
                    onSpecificTrigger();
                }
            }
        }
    }

    @Override
    public void atEndOfRound() {

        super.atEndOfRound();
        if (this.thirdused) {
            this.amount += 25;
        }
        if (this.secondused) {
            this.amount += 25;
        }
        if (this.firstused) {
            this.amount += 25;
        }
        this.thirdused = false;
        this.secondused = false;
        this.firstused = false;
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
