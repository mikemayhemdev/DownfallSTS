package charbosses.cards.green;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.cards.tempCards.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.powers.*;

import charbosses.cards.AbstractBossCard;
import charbosses.cards.colorless.EnShiv;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.core.*;

public class EnInfiniteBlades extends AbstractBossCard
{
    public static final String ID = "EvilWithin_Charboss:Infinite Blades";
    private static final CardStrings cardStrings;
    
    public EnInfiniteBlades() {
        super(ID, EnInfiniteBlades.cardStrings.NAME, "green/power/infinite_blades", 1, EnInfiniteBlades.cardStrings.DESCRIPTION, CardType.POWER, CardColor.GREEN, CardRarity.UNCOMMON, CardTarget.SELF);
        this.cardsToPreview = new EnShiv();
        this.limit = 2;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(p, p, new InfiniteBladesPower(p, 1), 1));
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.isInnate = true;
            this.rawDescription = EnInfiniteBlades.cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public int getValue() {
    	return (new EnShiv()).getValue() * 3 + 3;
    }
    
    @Override
    public int getUpgradeValue() {
    	return (new EnShiv()).getValue() * 2;
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new EnInfiniteBlades();
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Infinite Blades");
    }
}
