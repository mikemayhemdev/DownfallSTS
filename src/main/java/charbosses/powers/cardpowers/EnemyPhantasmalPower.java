package charbosses.powers.cardpowers;

import charbosses.powers.general.EnemyPoisonPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DoubleDamagePower;
import com.megacrit.cardcrawl.powers.NoxiousFumesPower;
import com.megacrit.cardcrawl.powers.PhantasmalPower;

public class EnemyPhantasmalPower extends AbstractPower {
    public static final String POWER_ID = "downfall:Phantasmal";
    private static final PowerStrings powerStrings;

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(PhantasmalPower.POWER_ID);
    }

    public EnemyPhantasmalPower(final AbstractCreature owner, final int amount) {
        this.name = EnemyPhantasmalPower.powerStrings.NAME;
        this.ID = "Phantasmal";
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.loadRegion("phantasmal");
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        this.flash();
        this.addToBot(new ApplyPowerAction(this.owner, this.owner, new DoubleDamagePower(this.owner, 1, true), this.amount));
        this.addToBot(new ReducePowerAction(this.owner, this.owner, "Phantasmal", 1));
    }

    @Override
    public void updateDescription() {
        this.description = EnemyPhantasmalPower.powerStrings.DESCRIPTIONS[0] + this.amount + EnemyPhantasmalPower.powerStrings.DESCRIPTIONS[1];
    }
}
