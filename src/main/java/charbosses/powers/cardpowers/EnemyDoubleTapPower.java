package charbosses.powers.cardpowers;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.powers.AbstractPower;

import charbosses.actions.common.EnemyUseCardAction;
import charbosses.actions.util.CharbossDoCardQueueAction;
import charbosses.bosses.AbstractCharBoss;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.core.*;

public class EnemyDoubleTapPower extends AbstractPower
{
    public static final String POWER_ID = "Double Tap";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    
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
        }
        else {
            this.description = EnemyDoubleTapPower.DESCRIPTIONS[1] + this.amount + EnemyDoubleTapPower.DESCRIPTIONS[2];
        }
    }
    
    @Override
    public void onUseCard(final AbstractCard card, final UseCardAction action) {
        if (!card.purgeOnUse && card.type == AbstractCard.CardType.ATTACK && this.amount > 0) {
            this.flash();
            AbstractMonster m = null;
            if (action.target != null) {
                m = (AbstractMonster)action.target;
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
    
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Double Tap");
        NAME = EnemyDoubleTapPower.powerStrings.NAME;
        DESCRIPTIONS = EnemyDoubleTapPower.powerStrings.DESCRIPTIONS;
    }
}
