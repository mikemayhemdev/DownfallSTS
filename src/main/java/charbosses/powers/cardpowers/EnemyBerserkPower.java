package charbosses.powers.cardpowers;

import charbosses.actions.common.EnemyGainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class EnemyBerserkPower extends AbstractPower {
    public static final String POWER_ID = "Berserk";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("Berserk");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public EnemyBerserkPower(final AbstractCreature owner) {
        this.name = EnemyBerserkPower.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = 1;
        this.updateDescription();
//        this.loadRegion("energized_green");
    }

    @Override
    public void updateDescription() {
        if (this.amount == 1) {
            this.description = EnemyBerserkPower.DESCRIPTIONS[0] + this.amount + EnemyBerserkPower.DESCRIPTIONS[1];
        } else {
            this.description = EnemyBerserkPower.DESCRIPTIONS[0] + this.amount + EnemyBerserkPower.DESCRIPTIONS[2];
        }
    }

    @Override
    public void onEnergyRecharge() {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new EnemyGainEnergyAction(this.amount));
    }
}
