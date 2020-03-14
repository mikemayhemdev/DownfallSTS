package charbosses.powers.cardpowers;


import charbosses.bosses.AbstractCharBoss;
import charbosses.stances.EnCalmStance;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class EnemyLikeWaterPower extends AbstractPower {
    public static final String POWER_ID = "LikeWaterPower";
    private static final PowerStrings powerStrings;

    public EnemyLikeWaterPower(AbstractCreature owner, int amt) {
        this.name = powerStrings.NAME;
        this.ID = "LikeWaterPower";
        this.owner = owner;
        this.amount = amt;
        this.updateDescription();
        this.loadRegion("like_water");
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        if (this.amount > 999) {
            this.amount = 999;
        }

        this.updateDescription();
    }

    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0] + this.amount + powerStrings.DESCRIPTIONS[1];
    }

    public void atEndOfTurnPreEndTurnCards(boolean isPlayer) {
        if (isPlayer) {
            AbstractCharBoss p = (AbstractCharBoss)this.owner;
            if (AbstractCharBoss.boss.stance instanceof EnCalmStance) {
                this.flash();
                this.addToBot(new GainBlockAction(this.owner, this.owner, this.amount));
            }
        }

    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("LikeWaterPower");
    }
}
