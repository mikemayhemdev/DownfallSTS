package charbosses.cards.green;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;

import charbosses.cards.AbstractBossCard;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.core.*;

public class EnDefendGreen extends AbstractBossCard
{
    public static final String ID = "EvilWithin_Charboss:Defend_G";
    private static final CardStrings cardStrings;
    
    public EnDefendGreen() {
        super(ID, EnDefendGreen.cardStrings.NAME, "green/skill/defend", 1, EnDefendGreen.cardStrings.DESCRIPTION, CardType.SKILL, CardColor.GREEN, CardRarity.BASIC, CardTarget.SELF);
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
        return new EnDefendGreen();
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Defend_G");
    }
}
