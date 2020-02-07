package charbosses.cards.curses;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;

import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.core.*;

public class EnPain extends AbstractBossCard
{
    public static final String ID = "EvilWithin_Charboss:Pain";
    private static final CardStrings cardStrings;
    
    public EnPain() {
        super(ID, EnPain.cardStrings.NAME, "curse/pain", -2, EnPain.cardStrings.DESCRIPTION, CardType.CURSE, CardColor.CURSE, CardRarity.CURSE, CardTarget.NONE);
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
    }
    
    @Override
    public void triggerOnOtherCardPlayed(final AbstractCard c) {
        this.addToTop(new LoseHPAction(AbstractCharBoss.boss, AbstractCharBoss.boss, 1));
    }
    
    @Override
    public void upgrade() {
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new EnPain();
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Pain");
    }
}
