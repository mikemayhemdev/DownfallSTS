package charbosses.cards.red;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;

import charbosses.actions.utility.EnemyExhaustAllEtherealAction;
import charbosses.cards.AbstractBossCard;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.core.*;

public class EnGhostlyArmor extends AbstractBossCard
{
    public static final String ID = "EvilWithin_Charboss:Ghostly Armor";
    private static final CardStrings cardStrings;
    
    public EnGhostlyArmor() {
        super(ID, EnGhostlyArmor.cardStrings.NAME, "red/skill/ghostly_armor", 1, EnGhostlyArmor.cardStrings.DESCRIPTION, CardType.SKILL, CardColor.RED, CardRarity.UNCOMMON, CardTarget.SELF);
        this.isEthereal = true;
        this.baseBlock = 10;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new GainBlockAction(m, m, this.block));
    }
    
    @Override
    public void triggerOnEndOfPlayerTurn() {
        this.addToTop(new EnemyExhaustAllEtherealAction());
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
        return new EnGhostlyArmor();
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Ghostly Armor");
    }
}
