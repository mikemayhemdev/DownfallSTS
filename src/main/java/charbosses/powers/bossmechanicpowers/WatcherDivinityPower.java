//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package charbosses.powers.bossmechanicpowers;

import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import charbosses.powers.cardpowers.EnemyAccuracyPower;
import charbosses.powers.cardpowers.EnemyMantraPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.TimeWarpTurnEndEffect;

public class WatcherDivinityPower extends AbstractBossMechanicPower {
    public static final String POWER_ID = "downfall:WatcherDivinityPower";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESC;

    public WatcherDivinityPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = 0;
        this.updateDescription();
        loadRegion("curiosity");
        this.type = PowerType.BUFF;
    }

    @Override
    public void atStartOfTurn() {
        System.out.println("atStartOfTurn started...");

        this.flash();

        int initialAmount = this.amount;
        System.out.println("Initial amount of Mantra: " + initialAmount);

        addToBot(new ReducePowerAction(this.owner, this.owner, EnemyMantraPower.POWER_ID, 5));
        System.out.println("DEBUG: Reducing by 5...");

        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                System.out.println("Following up.");

                int reducedAmount = Math.min(5, initialAmount);
                System.out.println("Predicted amount removed: " + reducedAmount);

                int previousMantraGained = AbstractCharBoss.boss.mantraGained;
                AbstractCharBoss.boss.mantraGained -= reducedAmount;
                System.out.println("MantraGained reduced from " + previousMantraGained + " to " + AbstractCharBoss.boss.mantraGained);

                this.isDone = true;
                System.out.println("atStartOfTurn completed.");
            }
        });
    }


    public void updateDescription() {
        this.description = DESC[0];
    }

    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if (!(card instanceof AbstractBossCard)) {
            this.addToBot(new ApplyPowerAction(this.owner, this.owner, new EnemyMantraPower(this.owner, 1), 1));

        }
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESC = powerStrings.DESCRIPTIONS;
    }
}
