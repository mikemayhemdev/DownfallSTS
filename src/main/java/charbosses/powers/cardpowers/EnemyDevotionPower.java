package charbosses.powers.cardpowers;


import charbosses.actions.unique.EnemyChangeStanceAction;
import charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.MantraPower;

public class EnemyDevotionPower extends AbstractPower {
    public static final String POWER_ID = "DevotionPower";
    private static final PowerStrings powerStrings;

    public EnemyDevotionPower(AbstractCreature owner, int newAmount) {
        this.name = powerStrings.NAME;
        this.ID = "DevotionPower";
        this.owner = owner;
        this.amount = newAmount;
        this.updateDescription();
        this.loadRegion("devotion");
    }

    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0] + this.amount + powerStrings.DESCRIPTIONS[1];
    }

    public void atStartOfTurnPostDraw() {
        this.flash();
        if (!AbstractCharBoss.boss.hasPower("Mantra") && this.amount >= 10) {
            this.addToBot(new EnemyChangeStanceAction("Divinity"));
        } else {
            this.addToBot(new ApplyPowerAction(this.owner, this.owner, new EnemyMantraPower(this.owner, this.amount), this.amount));
        }

    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("DevotionPower");
    }
}
