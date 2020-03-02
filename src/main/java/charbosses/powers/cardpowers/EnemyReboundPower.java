package charbosses.powers.cardpowers;

import charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;

public class EnemyReboundPower extends AbstractPower {
    public static final String POWER_ID = "Rebound";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    private boolean justEvoked = true;

    public EnemyReboundPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = "Rebound";
        this.owner = owner;
        this.amount = 1;
        this.updateDescription();
        this.loadRegion("rebound");
        this.isTurnBased = true;
        this.type = PowerType.BUFF;
    }

    public void updateDescription() {
        if (this.amount > 1) {
            this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        } else {
            this.description = DESCRIPTIONS[0];
        }

    }

    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if (this.justEvoked) {
            this.justEvoked = false;
        } else {
            if (card.type != CardType.POWER) {
                this.flash();
                action.reboundCard = true;
            }

            this.addToBot(new ReducePowerAction(this.owner, this.owner, "Rebound", 1));
        }
    }

    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, "Rebound"));
        }

    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Rebound");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
