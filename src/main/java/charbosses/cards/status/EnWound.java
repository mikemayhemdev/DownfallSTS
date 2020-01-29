package charbosses.cards.status;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;

import charbosses.cards.AbstractBossCard;

import com.megacrit.cardcrawl.core.*;

public class EnWound extends AbstractBossCard
{
    public static final String ID = "EvilWithin_Charboss:Wound";
    private static final CardStrings cardStrings;
    
    public EnWound() {
        super(ID, EnWound.cardStrings.NAME, "status/wound", -2, EnWound.cardStrings.DESCRIPTION, CardType.STATUS, CardColor.COLORLESS, CardRarity.COMMON, CardTarget.NONE);
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
    }
    
    @Override
    public void upgrade() {
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new EnWound();
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Wound");
    }
}
