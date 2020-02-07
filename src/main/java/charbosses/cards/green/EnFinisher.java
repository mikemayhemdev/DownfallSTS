package charbosses.cards.green;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;

import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import charbosses.powers.cardpowers.EnemyInfiniteBladesPower;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.unique.*;
import com.megacrit.cardcrawl.dungeons.*;
import java.util.*;
import com.megacrit.cardcrawl.core.*;

public class EnFinisher extends AbstractBossCard
{
    public static final String ID = "EvilWithin_Charboss:Finisher";
    private static final CardStrings cardStrings;
    
    public EnFinisher() {
        super(ID, EnFinisher.cardStrings.NAME, "green/attack/finisher", 1, EnFinisher.cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.GREEN, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.baseDamage = 6;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new DamagePerAttackPlayedAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        this.rawDescription = EnFinisher.cardStrings.DESCRIPTION;
        this.initializeDescription();
    }
    
    @Override
    public void applyPowers() {
        super.applyPowers();
        int count = 0;
        if (AbstractCharBoss.boss != null && AbstractCharBoss.finishedSetup) {
        	count = AbstractCharBoss.boss.attacksPlayedThisTurn;
        }
        this.rawDescription = EnFinisher.cardStrings.DESCRIPTION;
        this.rawDescription = this.rawDescription + EnFinisher.cardStrings.EXTENDED_DESCRIPTION[0] + count;
        if (count == 1) {
            this.rawDescription += EnFinisher.cardStrings.EXTENDED_DESCRIPTION[1];
        }
        else {
            this.rawDescription += EnFinisher.cardStrings.EXTENDED_DESCRIPTION[2];
        }
        this.initializeDescription();
    }

    @Override
    public int getPriority() {
    	return 0;
    }
    
    
    @Override
    public int getValue() {
    	int mult = 1;
    	if (AbstractCharBoss.boss != null && AbstractCharBoss.finishedSetup) {
    		mult += AbstractCharBoss.boss.attacksPlayedThisTurn;
    		for (AbstractCard c : AbstractCharBoss.boss.hand.group) {
    			if (c.type == CardType.ATTACK && c.cardID != this.cardID) {
    				mult ++;
    			}
    		}
    		if (AbstractCharBoss.boss.hasPower(EnemyInfiniteBladesPower.POWER_ID)) {
    			mult += AbstractCharBoss.boss.getPower(EnemyInfiniteBladesPower.POWER_ID).amount;
    		}
    	} else {
    		mult = 2;
    	}
    	return super.getValue() * mult;
    }
    
    @Override
    public void onMoveToDiscard() {
        this.rawDescription = EnFinisher.cardStrings.DESCRIPTION;
        this.initializeDescription();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(2);
        }
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new EnFinisher();
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Finisher");
    }
}
