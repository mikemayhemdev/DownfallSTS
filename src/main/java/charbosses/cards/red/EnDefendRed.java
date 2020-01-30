package charbosses.cards.red;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;

import charbosses.cards.AbstractBossCard;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.core.*;

public class EnDefendRed extends AbstractBossCard
{
    public static final String ID = "EvilWithin_Charboss:Defend_R";
    private static final CardStrings cardStrings;
    
    public EnDefendRed() {
        super(ID, EnDefendRed.cardStrings.NAME, "red/skill/defend", 1, EnDefendRed.cardStrings.DESCRIPTION, CardType.SKILL, CardColor.RED, CardRarity.BASIC, CardTarget.SELF);
        this.baseBlock = 5;
        this.tags.add(CardTags.STARTER_DEFEND);
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
    	this.addToBot(new GainBlockAction(m, m, this.block));
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
        return new EnDefendRed();
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Defend_R");
    }
}
