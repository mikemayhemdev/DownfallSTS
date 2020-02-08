package charbosses.powers.cardpowers;

import charbosses.actions.util.CharbossDoCardQueueAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class EnemyDoubleTapPower extends AbstractPower {
    public static final String POWER_ID = "Double Tap";
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    private static final PowerStrings powerStrings;

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Double Tap");
        NAME = EnemyDoubleTapPower.powerStrings.NAME;
        DESCRIPTIONS = EnemyDoubleTapPower.powerStrings.DESCRIPTIONS;
    }

    public EnemyDoubleTapPower(final AbstractCreature owner, final int amount) {
        this.name = EnemyDoubleTapPower.NAME;
        this.ID = "Double Tap";
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.loadRegion("doubleTap");
    }

    @Override
    public void updateDescription() {
        if (this.amount == 1) {
            this.description = EnemyDoubleTapPower.DESCRIPTIONS[0];
        } else {
            this.description = EnemyDoubleTapPower.DESCRIPTIONS[1] + this.amount + EnemyDoubleTapPower.DESCRIPTIONS[2];
        }
    }

    @Override
    public void onUseCard(final AbstractCard card, final UseCardAction action) {
        if (!card.purgeOnUse && card.type == AbstractCard.CardType.ATTACK && this.amount > 0) {
            this.flash();
            AbstractMonster m = null;
            if (action.target != null) {
                m = (AbstractMonster) action.target;
            }
            final AbstractCard tmp = card.makeSameInstanceOf();
            //AbstractCharBoss.boss.limbo.addToBottom(tmp);
            tmp.current_x = card.current_x;
            tmp.current_y = card.current_y;
            tmp.target_x = Settings.WIDTH / 2.0f - 300.0f * Settings.scale;
            tmp.target_y = Settings.HEIGHT / 2.0f;
            if (m != null) {
                tmp.calculateCardDamage(m);
            }
            tmp.purgeOnUse = true;
            //AbstractCharBoss.boss.hand.addToBottom(tmp);
            //AbstractDungeon.actionManager.addToBottom(new EnemyUseCardAction(tmp, m, card.energyOnUse, true, true), true);
            AbstractDungeon.actionManager.addToBottom(new CharbossDoCardQueueAction(tmp));
            --this.amount;
            if (this.amount == 0) {
                this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, "Double Tap"));
            }
        }
    }

    @Override
    public void atEndOfTurn(final boolean isPlayer) {
        if (isPlayer) {
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, "Double Tap"));
        }
    }
}
