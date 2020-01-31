package charbosses.cards.green;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;

import charbosses.actions.common.EnemyModifyDamageAction;
import charbosses.cards.AbstractBossCard;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.*;

public class EnGlassKnife extends AbstractBossCard
{
    public static final String ID = "EvilWithin_Charboss:Glass Knife";
    private static final CardStrings cardStrings;
    
    public EnGlassKnife() {
        super(ID, EnGlassKnife.cardStrings.NAME, "green/attack/glass_knife", 1, EnGlassKnife.cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.GREEN, CardRarity.RARE, CardTarget.ENEMY);
        this.baseDamage = 8;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        this.addToBot(new EnemyModifyDamageAction(this.uuid, -2));
    }
    
    @Override
    public int getValue() {
    	return super.getValue() * 2;
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(4);
        }
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new EnGlassKnife();
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Glass Knife");
    }
}
