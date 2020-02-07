package charbosses.cards.green;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;

import charbosses.actions.common.EnemyDiscardAction;
import charbosses.cards.AbstractBossCard;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.*;

public class EnSurvivor extends AbstractBossCard
{
    public static final String ID = "EvilWithin_Charboss:Survivor";
    private static final CardStrings cardStrings;
    
    public EnSurvivor() {
        super(ID, EnSurvivor.cardStrings.NAME, "green/skill/survivor", 1, EnSurvivor.cardStrings.DESCRIPTION, CardType.SKILL, CardColor.GREEN, CardRarity.BASIC, CardTarget.SELF);
        this.baseBlock = 8;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new GainBlockAction(m, m, this.block));
        this.addToBot(new EnemyDiscardAction(m, m, 1, false));
    }
    
    @Override
    public int getPriority() {
    	return super.getPriority() - 2;
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(3);
        }
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new EnSurvivor();
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Survivor");
    }
}
