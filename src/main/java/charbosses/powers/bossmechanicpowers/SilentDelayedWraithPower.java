//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package charbosses.powers.bossmechanicpowers;

import charbosses.actions.common.EnemyMakeTempCardInHandAction;
import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Silent.CharBossSilent;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.colorless.EnShiv;
import charbosses.cards.green.EnFinisher;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.powers.IntangiblePower;
import com.megacrit.cardcrawl.powers.WraithFormPower;
import downfall.downfallMod;
import downfall.monsters.AbstractTotemMonster;

public class SilentDelayedWraithPower extends AbstractBossMechanicPower {
    public static final String POWER_ID = "downfall:SilentDelayedWraithPower";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESC;

    private int healNormal = 10;
    private int healAsc = 15;

    private boolean activated = false;

    public SilentDelayedWraithPower(AbstractCreature owner) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        updateDescription();
        loadRegion("curiosity");
        type = PowerType.BUFF;
    }

    public void updateDescription() {
        if (AbstractDungeon.ascensionLevel >= 19){
            description = DESC[0] + "+" + DESC[1] + healAsc + DESC[2];
        } else {
            description = DESC[0] + DESC[1] + healNormal + DESC[2];
        }
    }


    public int onLoseHp(int damageAmount) {
        if (damageAmount >= this.owner.currentHealth) {
            damageAmount = this.owner.currentHealth-1;
            if (!activated) {
                //((CharBossSilent) owner).cantDie = true;
                activated = true;
                if (AbstractDungeon.ascensionLevel >= 19){
                    this.addToBot(new ApplyPowerAction(owner, owner, new IntangiblePlayerPower(owner, 3), 3));
                    this.addToBot(new HealAction(owner,owner,healAsc));
                } else {
                    this.addToBot(new ApplyPowerAction(owner, owner, new IntangiblePlayerPower(owner, 2), 2));
                    this.addToBot(new HealAction(owner,owner,healNormal));
                }
                this.addToBot(new ApplyPowerAction(owner, owner, new WraithFormPower(owner, -1), -1));
                this.addToBot(new RemoveSpecificPowerAction(owner, owner, this));
            }
        }
        return damageAmount;
    }

    @Override
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {

            if ((float) this.owner.currentHealth <= damageAmount) {

            }

        return super.onAttackedToChangeDamage(info, damageAmount);
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESC = powerStrings.DESCRIPTIONS;
    }
}
