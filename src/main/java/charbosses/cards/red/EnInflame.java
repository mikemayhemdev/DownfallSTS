package charbosses.cards.red;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.vfx.combat.*;

import charbosses.cards.AbstractBossCard;

import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.vfx.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.core.*;

public class EnInflame extends AbstractBossCard
{
    public static final String ID = "EvilWithin_Charboss:Inflame";
    private static final CardStrings cardStrings;
    
    public EnInflame() {
        super("Inflame", EnInflame.cardStrings.NAME, "red/power/inflame", 1, EnInflame.cardStrings.DESCRIPTION, CardType.POWER, CardColor.RED, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        this.magicValue = 12;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new VFXAction(m, new InflameEffect(m), 1.0f));
        this.addToBot(new ApplyPowerAction(m, m, new StrengthPower(m, this.magicNumber), this.magicNumber));
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
            this.initializeDescription();
        }
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new EnInflame();
    }
    
    @Override
    public int getPriority() {
    	return 3;
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Inflame");
    }
}
