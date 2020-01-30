package charbosses.cards.red;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.powers.*;

import charbosses.cards.AbstractBossCard;

import com.megacrit.cardcrawl.core.*;

public class EnFlex extends AbstractBossCard
{
    public static final String ID = "EvilWithin_Charboss:Flex";
    private static final CardStrings cardStrings;
    
    public EnFlex() {
        super(ID, EnFlex.cardStrings.NAME, "red/skill/flex", 0, EnFlex.cardStrings.DESCRIPTION, CardType.SKILL, CardColor.RED, CardRarity.COMMON, CardTarget.SELF);
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        this.magicValue = 2;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(m, m, new StrengthPower(m, this.magicNumber), this.magicNumber));
        this.addToBot(new ApplyPowerAction(m, m, new LoseStrengthPower(m, this.magicNumber), this.magicNumber));
    }
    
    @Override
    public int getPriority() {
    	return 4;
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(2);
        }
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new EnFlex();
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Flex");
    }
}
