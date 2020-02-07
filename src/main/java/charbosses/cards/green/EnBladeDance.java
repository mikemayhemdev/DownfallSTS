package charbosses.cards.green;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.cards.tempCards.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;

import charbosses.actions.common.EnemyMakeTempCardInHandAction;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.colorless.EnShiv;

import com.megacrit.cardcrawl.core.*;

public class EnBladeDance extends AbstractBossCard
{
    public static final String ID = "EvilWithin_Charboss:Blade Dance";
    private static final CardStrings cardStrings;
    
    public EnBladeDance() {
        super(ID, EnBladeDance.cardStrings.NAME, "green/skill/blade_dance", 1, EnBladeDance.cardStrings.DESCRIPTION, CardType.SKILL, CardColor.GREEN, CardRarity.COMMON, CardTarget.NONE);
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        this.cardsToPreview = new EnShiv();
        this.magicValue = 4;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new EnemyMakeTempCardInHandAction(new EnShiv(), this.magicNumber));
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }
    }

    @Override
	public int getValue() {
    	this.magicValue = (new EnShiv()).getValue();
    	return super.getValue();
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new EnBladeDance();
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Blade Dance");
    }
}
