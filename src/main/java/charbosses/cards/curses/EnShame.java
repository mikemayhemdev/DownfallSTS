package charbosses.cards.curses;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.powers.*;

import charbosses.actions.util.CharbossDoCardQueueAction;
import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.*;

public class EnShame extends AbstractBossCard
{
    public static final String ID = "EvilWithin_Charboss:Shame";
    private static final CardStrings cardStrings;
    
    public EnShame() {
        super(ID, EnShame.cardStrings.NAME, "curse/shame", -2, EnShame.cardStrings.DESCRIPTION, CardType.CURSE, CardColor.CURSE, CardRarity.CURSE, CardTarget.NONE);
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        if (this.dontTriggerOnUseCard) {
            this.addToTop(new ApplyPowerAction(AbstractCharBoss.boss, AbstractCharBoss.boss, new FrailPower(AbstractCharBoss.boss, 1, true), 1));
        }
    }
    
    @Override
    public void triggerOnEndOfTurnForPlayingCard() {
        this.dontTriggerOnUseCard = true;
        AbstractDungeon.actionManager.addToBottom(new CharbossDoCardQueueAction(this));
    }
    
    @Override
    public void upgrade() {
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new EnShame();
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Shame");
    }
}
