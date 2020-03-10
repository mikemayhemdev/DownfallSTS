package charbosses.powers.cardpowers;
import charbosses.actions.unique.EnemyChangeStanceAction;
import charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;

public class EnemyMantraPower extends AbstractPower {
    public static final String POWER_ID = "Mantra";
    private static final PowerStrings powerStrings;
    private final int PRAYER_REQUIRED = 10;

    public EnemyMantraPower(AbstractCreature owner, int amount) {
        this.name = powerStrings.NAME;
        this.ID = "Mantra";
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.loadRegion("mantra");
        this.type = PowerType.BUFF;
        if (this.owner instanceof AbstractCharBoss){
            AbstractCharBoss cB = (AbstractCharBoss)this.owner;
            cB.mantraGained += amount;
        }
    }

    public void playApplyPowerSfx() {
        CardCrawlGame.sound.play("POWER_MANTRA", 0.05F);
    }

    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0] + 10 + powerStrings.DESCRIPTIONS[1];
    }

    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if (this.amount >= 10) {
            this.addToTop(new EnemyChangeStanceAction("Divinity"));
            this.amount -= 10;
            if (this.amount <= 0) {
                this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, "Mantra"));
            }
        }

    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Mantra");
    }
}
