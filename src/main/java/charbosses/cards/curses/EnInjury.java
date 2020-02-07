package charbosses.cards.curses;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;

import charbosses.cards.AbstractBossCard;

import com.megacrit.cardcrawl.core.*;

public class EnInjury extends AbstractBossCard
{
    public static final String ID = "EvilWithin_Charboss:Injury";
    private static final CardStrings cardStrings;
    
    public EnInjury() {
        super(ID, EnInjury.cardStrings.NAME, "curse/injury", -2, EnInjury.cardStrings.DESCRIPTION, CardType.CURSE, CardColor.CURSE, CardRarity.CURSE, CardTarget.NONE);
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
    }
    
    @Override
    public void upgrade() {
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new EnInjury();
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Injury");
    }
}
