//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package charbosses.powers.bossmechanicpowers;

import charbosses.actions.unique.EnemyChangeStanceAction;
import charbosses.bosses.AbstractCharBoss;
import charbosses.stances.EnWrathStance;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import downfall.downfallMod;
import expansioncontent.expansionContentMod;

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
        if (downfallMod.useLegacyBosses) {
            loadRegion("curiosity");
        } else {
            loadRegion("anger");
        }
    }

    public void updateDescription() {

        if (downfallMod.useLegacyBosses) {
            this.description = DESCRIPTIONS[0];
        } else {
            this.description = DESCRIPTIONS[1];
        }
    }

    @Override
    public void atEndOfRound() {
        super.atEndOfRound();
        if (downfallMod.useLegacyBosses) {
            if (!active && owner.currentHealth <= owner.maxHealth / 2) {

                this.addToBot(new EnemyChangeStanceAction(EnWrathStance.STANCE_ID));
                active = true;
            }
        }
    }

    @Override
    public float atDamageReceive(float damage, DamageInfo.DamageType damageType) {

        if (damage > 1.0F) {
            if (this.owner instanceof AbstractCharBoss) {
                if (((AbstractCharBoss)owner).stance instanceof EnWrathStance) {
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
