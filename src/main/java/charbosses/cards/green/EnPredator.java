package charbosses.cards.green;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.powers.*;

import charbosses.cards.AbstractBossCard;
import charbosses.powers.general.EnemyDrawCardNextTurnPower;

import com.megacrit.cardcrawl.core.*;

public class EnPredator extends AbstractBossCard
{
    public static final String ID = "EvilWithin_Charboss:Predator";
    private static final CardStrings cardStrings;
    
    public EnPredator() {
        super(ID, EnPredator.cardStrings.NAME, "green/attack/predator", 2, EnPredator.cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.GREEN, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.baseDamage = 15;
        this.magicNumber = this.baseMagicNumber = 2;
        this.magicValue = 5;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        this.addToBot(new ApplyPowerAction(m, m, new EnemyDrawCardNextTurnPower(m, 2), 2));
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(5);
        }
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new EnPredator();
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Predator");
    }
}
