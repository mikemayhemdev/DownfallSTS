package charbosses.cards.red;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.powers.*;

import charbosses.cards.AbstractBossCard;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.core.*;

public class EnDisarm extends AbstractBossCard
{
    public static final String ID = "EvilWithin_Charboss:Disarm";
    private static final CardStrings cardStrings;
    
    public EnDisarm() {
        super(ID, EnDisarm.cardStrings.NAME, "red/skill/disarm", 1, EnDisarm.cardStrings.DESCRIPTION, CardType.SKILL, CardColor.RED, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        this.magicValue = 6;
        this.exhaust = true;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(p, m, new StrengthPower(p, -this.magicNumber), -this.magicNumber));
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
        return new EnDisarm();
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Disarm");
    }
}
