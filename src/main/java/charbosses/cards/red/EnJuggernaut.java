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

public class EnJuggernaut extends AbstractBossCard
{
    public static final String ID = "EvilWithin_Charboss:Juggernaut";
    private static final CardStrings cardStrings;
    
    public EnJuggernaut() {
        super(ID, EnJuggernaut.cardStrings.NAME, "red/power/juggernaut", 2, EnJuggernaut.cardStrings.DESCRIPTION, CardType.POWER, CardColor.RED, CardRarity.RARE, CardTarget.SELF);
        this.baseMagicNumber = 5;
        this.magicNumber = this.baseMagicNumber;
        this.magicValue = 6;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(m, m, new JuggernautPower(m, this.magicNumber), this.magicNumber));
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
        return new EnJuggernaut();
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Juggernaut");
    }
}
