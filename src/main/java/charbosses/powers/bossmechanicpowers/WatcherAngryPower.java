//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package charbosses.powers.bossmechanicpowers;

import charbosses.actions.unique.EnemyChangeStanceAction;
import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Watcher.CharBossWatcher;
import charbosses.stances.EnDivinityStance;
import charbosses.stances.EnWrathStance;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import slimebound.SlimeboundMod;

public class WatcherAngryPower extends AbstractBossMechanicPower {
    public static final String POWER_ID = "downfall:WatcherAngryPower";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;

    private boolean active = false;

    public WatcherAngryPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.updateDescription();
        loadRegion("curiosity");
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public int onLoseHp(int damageAmount) {
        SlimeboundMod.logger.info("Watcher Angry lost " + damageAmount + " HP");
        SlimeboundMod.logger.info("Watcher Angry current HP " + this.owner.currentHealth);
        SlimeboundMod.logger.info("Watcher Angry Max HP " + this.owner.maxHealth / 2);
        if ((this.owner.currentHealth - damageAmount) <= (this.owner.maxHealth / 2) && !active) {
            flash();
            active = true;
            this.addToBot(new EnemyChangeStanceAction(EnWrathStance.STANCE_ID));
           // this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
        return super.onLoseHp(damageAmount);
    }

    @Override
    public float atDamageReceive(float damage, DamageInfo.DamageType damageType) {

        if (damage > 1.0F) {
            if (this.owner instanceof AbstractCharBoss) {
                if (AbstractCharBoss.boss.stance instanceof EnWrathStance) {
                   return damage * 1.5F;
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
