package charbosses.cards.red;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.powers.*;

import charbosses.cards.AbstractBossCard;

import com.megacrit.cardcrawl.core.*;

public class EnBash extends AbstractBossCard
{
    public static final String ID = "EvilWithin_Charboss:Bash";
    private static final CardStrings cardStrings;
    
    public EnBash() {
        super(ID, EnBash.cardStrings.NAME, "red/attack/bash", 2, EnBash.cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.RED, CardRarity.BASIC, CardTarget.ENEMY);
        this.baseDamage = 8;
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        this.magicValue = 4;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        this.addToBot(new ApplyPowerAction(p, m, new VulnerablePower(p, this.magicNumber, false), this.magicNumber));
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(2);
            this.upgradeMagicNumber(1);
        }
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new EnBash();
    }

    @Override
    public int getPriority() {
		return 2;
	}
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Bash");
    }
}
