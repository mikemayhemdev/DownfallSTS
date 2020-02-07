package charbosses.cards.curses;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;

import charbosses.cards.AbstractBossCard;

import com.megacrit.cardcrawl.core.*;

public class EnWrithe extends AbstractBossCard
{
    public static final String ID = "EvilWithin_Charboss:Writhe";
    private static final CardStrings cardStrings;
    
    public EnWrithe() {
        super(ID, EnWrithe.cardStrings.NAME, "curse/writhe", -2, EnWrithe.cardStrings.DESCRIPTION, CardType.CURSE, CardColor.CURSE, CardRarity.CURSE, CardTarget.NONE);
        this.isInnate = true;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
    }
    
    @Override
    public void upgrade() {
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new EnWrithe();
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Writhe");
    }
}