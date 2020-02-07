package charbosses.cards.red;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;

import charbosses.actions.common.EnemyExhaustAction;
import charbosses.cards.AbstractBossCard;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.*;

public class EnTrueGrit extends AbstractBossCard
{
    public static final String ID = "EvilWithin_Charboss:True Grit";
    private static final CardStrings cardStrings;
    
    public EnTrueGrit() {
        super(ID, EnTrueGrit.cardStrings.NAME, "red/skill/true_grit", 1, EnTrueGrit.cardStrings.DESCRIPTION, CardType.SKILL, CardColor.RED, CardRarity.COMMON, CardTarget.SELF);
        this.baseBlock = 7;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new GainBlockAction(m, m, this.block));
        if (this.upgraded) {
            this.addToBot(new EnemyExhaustAction(1, false));
        }
        else {
            this.addToBot(new EnemyExhaustAction(1, true, false, false));
        }
    }

    @Override
    public int getPriority() {
    	return (this.upgraded ? 0 : -1);
    }
    @Override
    public int getValue() {
    	return super.getValue() + (this.upgraded ? 5 : 1);
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(2);
            this.rawDescription = EnTrueGrit.cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new EnTrueGrit();
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("True Grit");
    }
}
