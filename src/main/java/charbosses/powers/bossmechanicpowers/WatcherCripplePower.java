//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package charbosses.powers.bossmechanicpowers;

import charbosses.bosses.Watcher.CharBossWatcher;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class WatcherCripplePower extends AbstractBossMechanicPower {
    public static final String POWER_ID = "downfall:WatcherCripplePower";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;

    private boolean used = false;

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
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
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
        if (amount <= 0 && !used) {
            used = true;
            this.addToBot(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, -4), -4, true, AbstractGameAction.AttackEffect.NONE));
            if (!owner.hasPower(ArtifactPower.POWER_ID))
                this.addToBot(new ApplyPowerAction(this.owner, this.owner, new GainStrengthPower(this.owner, 4), 4, true, AbstractGameAction.AttackEffect.NONE));
        }
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
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
        if (used) {
            this.amount = 100;
            used = false;
        }
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
