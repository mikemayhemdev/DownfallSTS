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

public class EnDemonForm extends AbstractBossCard
{
    public static final String ID = "EvilWithin_Charboss:Demon Form";
    private static final CardStrings cardStrings;
    
    public EnDemonForm() {
        super(ID, EnDemonForm.cardStrings.NAME, "red/power/demon_form", 3, EnDemonForm.cardStrings.DESCRIPTION, CardType.POWER, CardColor.RED, CardRarity.RARE, CardTarget.NONE);
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        this.magicValue = 12;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(p, p, new DemonFormPower(p, this.magicNumber), this.magicNumber));
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
        return new EnDemonForm();
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Demon Form");
    }
}
