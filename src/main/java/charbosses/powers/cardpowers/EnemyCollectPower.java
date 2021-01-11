package charbosses.powers.cardpowers;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class EnemyCollectPower extends AbstractPower {
    public static final String POWER_ID = "Collect";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    private int cardsDoubledThisTurn = 1;

    public EnemyCollectPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = "Collect";
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.loadRegion("energized_blue");
    }

    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0];
        } else {
            this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        }

    }

    public void onEnergyRecharge() {
        this.flash();
        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, "Collect"));

    }

    /*
    public void atStartOfTurn() {
        this.cardsDoubledThisTurn = 0;
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (!card.purgeOnUse && this.amount > 0 && this.cardsDoubledThisTurn < this.amount && card instanceof AbstractBossCard) {
            ++this.cardsDoubledThisTurn;
            this.flash();
            AbstractMonster m = null;
            if (action.target != null) {
                m = (AbstractMonster)action.target;
            }

            AbstractCard tmp = card.makeSameInstanceOf();
            AbstractCharBoss.boss.limbo.addToTop(tmp);
            tmp.current_x = card.current_x;
            tmp.current_y = card.current_y;
            tmp.target_x = (float)Settings.WIDTH / 2.0F + 300.0F * Settings.scale;
            tmp.target_y = (float)Settings.HEIGHT / 2.0F;
            if (m != null) {
                tmp.calculateCardDamage(m);
            }

            tmp.purgeOnUse = true;
            tmp.isInAutoplay = true;
            tmp.freeToPlayOnce = true;
            AbstractCharBoss.boss.useCard(tmp,m, EnemyEnergyPanel.totalCount);
//            AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, m, card.energyOnUse, true, true), true);
        }


    }
    */

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Collect");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
