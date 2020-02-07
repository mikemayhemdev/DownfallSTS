package charbosses.cards.red;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;

import charbosses.actions.common.EnemyModifyDamageAction;
import charbosses.cards.AbstractBossCard;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.*;

public class EnRampage extends AbstractBossCard
{
    public static final String ID = "EvilWithin_Charboss:Rampage";
    private static final CardStrings cardStrings;
    
    public EnRampage() {
        super(ID, EnRampage.cardStrings.NAME, "red/attack/rampage", 1, EnRampage.cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.RED, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.baseDamage = 8;
        this.baseMagicNumber = 5;
        this.magicNumber = this.baseMagicNumber;
        this.magicValue = 1;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        this.addToBot(new EnemyModifyDamageAction(this.uuid, this.magicNumber));
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(3);
            this.initializeDescription();
        }
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new EnRampage();
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Rampage");
    }
}
