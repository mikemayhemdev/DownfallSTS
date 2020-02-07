package charbosses.cards.red;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;

import charbosses.actions.common.EnemyGainEnergyAction;
import charbosses.cards.AbstractBossCard;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.core.*;

public class EnSeeingRed extends AbstractBossCard
{
    public static final String ID = "EvilWithin_Charboss:Seeing Red";
    private static final CardStrings cardStrings;
    
    public EnSeeingRed() {
        super(ID, EnSeeingRed.cardStrings.NAME, "red/skill/seeing_red", 1, EnSeeingRed.cardStrings.DESCRIPTION, CardType.SKILL, CardColor.RED, CardRarity.UNCOMMON, CardTarget.NONE);
        this.exhaust = true;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new EnemyGainEnergyAction(2));
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(0);
        }
    }

    @Override
    public int getValue() {
    	return 20 - 5 * this.cost;
    }
    @Override
    public int getPriority() {
    	return 4;
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new EnSeeingRed();
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Seeing Red");
    }
}
