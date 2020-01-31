package charbosses.cards.green;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.cards.tempCards.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;

import charbosses.actions.common.EnemyMakeTempCardInHandAction;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.colorless.EnShiv;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.*;

public class EnCloakAndDagger extends AbstractBossCard
{
    public static final String ID = "EvilWithin_Charboss:Cloak And Dagger";
    private static final CardStrings cardStrings;
    
    public EnCloakAndDagger() {
        super(ID, EnCloakAndDagger.cardStrings.NAME, "green/skill/cloak_and_dagger", 1, EnCloakAndDagger.cardStrings.DESCRIPTION, CardType.SKILL, CardColor.GREEN, CardRarity.COMMON, CardTarget.SELF);
        this.baseBlock = 6;
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        this.cardsToPreview = new EnShiv();
        this.magicValue = 4;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new GainBlockAction(m, m, this.block));
        this.addToBot(new EnemyMakeTempCardInHandAction(new EnShiv(), this.magicNumber));
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
            this.rawDescription = EnCloakAndDagger.cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    
    @Override
	public int getValue() {
    	this.magicValue = (new EnShiv()).getValue();
    	return super.getValue();
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new EnCloakAndDagger();
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Cloak And Dagger");
    }
}
