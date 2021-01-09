package downfall.powers.neowpowers;
import charbosses.actions.unique.EnemyChangeStanceAction;
import charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class NeowMantraPower extends AbstractPower {
    public static final String POWER_ID = "downfall:NeowMantra";
    private static final PowerStrings powerStrings;
    private final int PRAYER_REQUIRED = 10;

    public NeowMantraPower(AbstractCreature owner, int amount) {
        this.name = powerStrings.NAME;
        this.ID = "downfall:NeowMantra";
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
        this.description = powerStrings.DESCRIPTIONS[0];
    }

    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if (this.amount >= 10) {
            this.addToBot(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, 10), 10, true, AbstractGameAction.AttackEffect.NONE));

            this.amount -= 10;
        }

    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("downfall:NeowMantra");
    }
}
