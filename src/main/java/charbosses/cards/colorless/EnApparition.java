package charbosses.cards.colorless;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.powers.*;

import charbosses.cards.AbstractBossCard;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class EnApparition extends AbstractBossCard
{
    public static final String ID = "EvilWithin_Charboss:Ghostly";
    private static final CardStrings cardStrings;
    
    public EnApparition() {
        super(ID, EnApparition.cardStrings.NAME, "colorless/skill/apparition", 1, EnApparition.cardStrings.DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.SELF);
        this.exhaust = true;
        this.isEthereal = true;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(m, m, new IntangiblePower(m, 1), 1));
    }
    
    @Override
    public int getValue() {
    	return 25; 
    }
    @Override
    public int getPriority() {
    	return super.getPriority() + 1 + (AbstractDungeon.player.hasPower(ThornsPower.POWER_ID) ? 1 : 0); 
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = EnApparition.cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
            this.isEthereal = false;
        }
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new EnApparition();
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Ghostly");
    }
}
