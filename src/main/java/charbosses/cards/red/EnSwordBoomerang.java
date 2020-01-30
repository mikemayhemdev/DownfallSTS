package charbosses.cards.red;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;

import charbosses.cards.AbstractBossCard;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.*;

public class EnSwordBoomerang extends AbstractBossCard
{
    public static final String ID = "EvilWithin_Charboss:Sword Boomerang";
    private static final CardStrings cardStrings;
    
    public EnSwordBoomerang() {
        super(ID, EnSwordBoomerang.cardStrings.NAME, "red/attack/sword_boomerang", 1, EnSwordBoomerang.cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.RED, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        this.baseDamage = 3;
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
        this.magicValue = 0;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        for (int i = 0; i < this.magicNumber; ++i) {
            this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        }
    }
    
    @Override
    public int getValue() {
    	return super.getValue() * this.magicNumber;
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new EnSwordBoomerang();
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Sword Boomerang");
    }
}
