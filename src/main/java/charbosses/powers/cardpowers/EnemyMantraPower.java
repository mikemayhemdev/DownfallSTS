package charbosses.powers.cardpowers;
import charbosses.actions.unique.EnemyChangeStanceAction;
import charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;
import com.megacrit.cardcrawl.powers.StrengthPower;
import downfall.downfallMod;

public class EnemyMantraPower extends AbstractPower {
    public static final String POWER_ID = "Mantra";
    private static final PowerStrings powerStrings;
    private final int PRAYER_REQUIRED = 10;
    private int initialAmount = 0;

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

    @Override
    public void atStartOfTurn() {
        //System.out.println("atStartOfTurn started...");

        this.flash();

        initialAmount = this.amount;

        //System.out.println("Initial amount of Mantra: " + initialAmount);

        addToBot(new ReducePowerAction(this.owner, this.owner, EnemyMantraPower.POWER_ID, 5));
    }

    public void atEndOfRound() {
        if (downfallMod.useLegacyBosses) {
           // System.out.println("DEBUG: Reducing by 5...");

            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                 //   System.out.println("Following up.");

                    int reducedAmount = Math.min(5, initialAmount);
                   // System.out.println("Predicted amount removed: " + reducedAmount);

                    int previousMantraGained = AbstractCharBoss.boss.mantraGained;
                    AbstractCharBoss.boss.mantraGained -= reducedAmount;
                  //  System.out.println("MantraGained reduced from " + previousMantraGained + " to " + AbstractCharBoss.boss.mantraGained);

                    this.isDone = true;
                   // System.out.println("atStartOfTurn completed.");
                }
            });
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
            this.addToBot(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, 3), 3));
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
