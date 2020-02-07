package charbosses.cards.red;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;

import charbosses.cards.AbstractBossCard;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.*;

public class EnSentinel extends AbstractBossCard
{
    public static final String ID = "EvilWithin_Charboss:Sentinel";
    private static final CardStrings cardStrings;
    
    public EnSentinel() {
        super(ID, EnSentinel.cardStrings.NAME, "red/skill/sentinel", 1, EnSentinel.cardStrings.DESCRIPTION, CardType.SKILL, CardColor.RED, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseBlock = 5;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new GainBlockAction(p, p, this.block));
    }
    
    @Override
    public void triggerOnExhaust() {
        if (this.upgraded) {
            this.addToTop(new GainEnergyAction(3));
        }
        else {
            this.addToTop(new GainEnergyAction(2));
        }
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(3);
            this.rawDescription = EnSentinel.cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new EnSentinel();
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Sentinel");
    }
}
