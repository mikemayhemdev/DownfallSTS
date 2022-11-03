//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package charbossHermit;

import charbosses.cards.AbstractBossCard;
import charbosses.powers.bossmechanicpowers.AbstractBossMechanicPower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static charbossHermit.NewAge.ArchetypeAct1SharpshooterNewAge.damageThreshold;

public class HermitConcentrationPower extends AbstractBossMechanicPower {
    public static final String POWER_ID = "downfall:HermitConcentrationPower";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESC;

    public HermitConcentrationPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = damageThreshold;
        this.updateDescription();
        loadRegion("curiosity");
        this.type = PowerType.BUFF;
    }

    @Override
    public int onLoseHp(int damageAmount) {
        this.flash();
        stackPower(damageAmount * -1);
        this.updateDescription();
        return damageAmount;
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if (amount <= 0) {
            if (this.owner instanceof CharBossHermit) {
                for (AbstractCard bc : ((CharBossHermit) this.owner).hand.group) {
                    ((AbstractBossCard) bc).onSpecificTrigger();
                }
            }
        }
    }

    public void atEndOfTurn(boolean isPlayer) {
        this.amount = damageThreshold;
        this.updateDescription();
    }

    public void updateDescription() {
        if (this.amount > 0) {
            this.description = DESC[0] + amount + DESC[1];
        } else {
            this.description = DESC[2];
        }
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESC = powerStrings.DESCRIPTIONS;
    }
}
