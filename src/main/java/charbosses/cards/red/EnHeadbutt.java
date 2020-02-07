package charbosses.cards.red;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;

import charbosses.actions.unique.EnemyDiscardPileToTopOfDeckAction;
import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.EnemyCardGroup;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.*;
import com.megacrit.cardcrawl.core.*;

public class EnHeadbutt extends AbstractBossCard
{
    public static final String ID = "EvilWithin_Charboss:Headbutt";
    private static final CardStrings cardStrings;
    
    public EnHeadbutt() {
        super(ID, EnHeadbutt.cardStrings.NAME, "red/attack/headbutt", 1, EnHeadbutt.cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.RED, CardRarity.COMMON, CardTarget.ENEMY);
        this.baseDamage = 9;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        this.addToBot(new EnemyDiscardPileToTopOfDeckAction((AbstractCharBoss) m));
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(3);
        }
    }
    
    @Override
    public int getPriority() {
    	return super.getPriority() - 1;
    }
    
    private int returnValue() {
    	recursionCheck = true;
    	AbstractBossCard c = ((EnemyCardGroup) (AbstractCharBoss.boss.discardPile)).getHighestValueCard();
    	if (c == null) {
    		return 0;
    	}
    	int v = c.getValue();
    	recursionCheck = false;
    	return ((v + 10) / 2) - 5;
    }
    
    @Override
    public int getValue() {
    	return super.getValue() + ((AbstractCharBoss.boss == null || !AbstractCharBoss.boss.hand.contains(this) || recursionCheck) ? 2 : this.returnValue());
    }
    
    @Override
    public int getUpgradeValue() {
    	return 3;
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new EnHeadbutt();
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Headbutt");
    }
}
