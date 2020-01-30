package charbosses.cards.red;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.powers.*;

import charbosses.cards.AbstractBossCard;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.*;
import java.util.*;
import com.megacrit.cardcrawl.core.*;

public class EnBarricade extends AbstractBossCard
{
    public static final String ID = "EvilWithin_Charboss:Barricade";
    private static final CardStrings cardStrings;
    
    public EnBarricade() {
        super(ID, EnBarricade.cardStrings.NAME, "red/power/barricade", 3, EnBarricade.cardStrings.DESCRIPTION, CardType.POWER, CardColor.RED, CardRarity.RARE, CardTarget.SELF);
        this.baseMagicNumber = this.magicNumber = 1;
        this.magicValue = 50;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        boolean powerExists = false;
        for (final AbstractPower pow : m.powers) {
            if (pow.ID.equals("Barricade")) {
                powerExists = true;
                break;
            }
        }
        if (!powerExists) {
            this.addToBot(new ApplyPowerAction(m, m, new BarricadePower(m)));
        }
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(2);
        }
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new EnBarricade();
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Barricade");
    }
}
