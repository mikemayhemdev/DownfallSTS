package charbosses.cards.red;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;

import charbosses.cards.AbstractBossCard;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.core.*;

public class EnImpervious extends AbstractBossCard
{
    public static final String ID = "EvilWithin_Charboss:Impervious";
    private static final CardStrings cardStrings;
    
    public EnImpervious() {
        super(ID, EnImpervious.cardStrings.NAME, "red/skill/impervious", 2, EnImpervious.cardStrings.DESCRIPTION, CardType.SKILL, CardColor.RED, CardRarity.RARE, CardTarget.SELF);
        this.baseBlock = 30;
        this.exhaust = true;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new GainBlockAction(m, m, this.block));
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(10);
        }
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new EnImpervious();
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Impervious");
    }
}
