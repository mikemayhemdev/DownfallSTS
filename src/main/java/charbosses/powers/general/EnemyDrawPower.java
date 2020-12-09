package charbosses.powers.general;

import charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class EnemyDrawPower extends AbstractPower {
    public static final String POWER_ID = "downfall:EnemyDraw";
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    private static final PowerStrings powerStrings;

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Draw");
        NAME = EnemyDrawPower.powerStrings.NAME;
        DESCRIPTIONS = EnemyDrawPower.powerStrings.DESCRIPTIONS;
    }

    public EnemyDrawPower(final AbstractCreature owner, final int drawAmount) {
        this.name = EnemyDrawPower.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = drawAmount;
        this.updateDescription();
        this.loadRegion("draw");
        this.priority = 20;
    }

    @Override
    public void updateDescription() {
        if (this.amount > 0) {
            if (this.amount == 1) {
                this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
            } else {
                this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[3];
            }

            this.type = PowerType.BUFF;
        } else {
            if (this.amount == -1) {
                this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2];
            } else {
                this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[4];
            }

            this.type = PowerType.DEBUFF;
        }

    }

    @Override
    public void atStartOfTurnPostDraw() {
        this.flash();
        //this.addToBot(new EnemyDrawCardAction((AbstractCharBoss) this.owner, this.amount));
    }
}
