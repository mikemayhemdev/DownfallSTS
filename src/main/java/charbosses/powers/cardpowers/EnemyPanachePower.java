package charbosses.powers.cardpowers;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.powers.AbstractPower;

import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;

import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.*;

public class EnemyPanachePower extends AbstractPower
{
    public static final String POWER_ID = "EvilWithin:EnemyPanache";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final int CARD_AMT = 5;
    private int damage;
    
    public EnemyPanachePower(final AbstractCreature owner, final int damage) {
        this.name = EnemyPanachePower.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = 5;
        this.damage = damage;
        this.updateDescription();
        this.loadRegion("panache");
    }
    
    @Override
    public void updateDescription() {
        if (this.amount == 1) {
            this.description = EnemyPanachePower.DESCRIPTIONS[0] + this.amount + EnemyPanachePower.DESCRIPTIONS[1] + this.damage + EnemyPanachePower.DESCRIPTIONS[2];
        }
        else {
            this.description = EnemyPanachePower.DESCRIPTIONS[0] + this.amount + EnemyPanachePower.DESCRIPTIONS[3] + this.damage + EnemyPanachePower.DESCRIPTIONS[2];
        }
    }
    
    @Override
    public void stackPower(final int stackAmount) {
        this.fontScale = 8.0f;
        this.damage += stackAmount;
        this.updateDescription();
    }
    
    @Override
    public void onUseCard(final AbstractCard card, final UseCardAction action) {
    	if (!(card instanceof AbstractBossCard)) {
    		return;
    	}
        --this.amount;
        if (this.amount == 0) {
            this.flash();
            this.amount = 5;
            this.addToBot(new DamageAction(AbstractDungeon.player, new DamageInfo(AbstractCharBoss.boss, this.damage, DamageType.THORNS), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        }
        this.updateDescription();
    }
    
    @Override
    public void atStartOfTurn() {
        this.amount = 5;
        this.updateDescription();
    }
    
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Panache");
        NAME = EnemyPanachePower.powerStrings.NAME;
        DESCRIPTIONS = EnemyPanachePower.powerStrings.DESCRIPTIONS;
    }
}
