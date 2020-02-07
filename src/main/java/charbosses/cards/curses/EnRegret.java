package charbosses.cards.curses;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;

import charbosses.actions.util.CharbossDoCardQueueAction;
import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.*;

public class EnRegret extends AbstractBossCard
{
    public static final String ID = "EvilWithin_Charboss:Regret";
    private static final CardStrings cardStrings;
    
    public EnRegret() {
        super(ID, EnRegret.cardStrings.NAME, "curse/regret", -2, EnRegret.cardStrings.DESCRIPTION, CardType.CURSE, CardColor.CURSE, CardRarity.CURSE, CardTarget.NONE);
        this.magicValue = -1;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        if (this.dontTriggerOnUseCard) {
            this.addToTop(new LoseHPAction(AbstractCharBoss.boss, AbstractCharBoss.boss, this.magicNumber, AbstractGameAction.AttackEffect.FIRE));
        }
    }
    
    @Override
    public void triggerOnEndOfTurnForPlayingCard() {
        this.dontTriggerOnUseCard = true;
        final int size = AbstractCharBoss.boss.hand.size();
        this.baseMagicNumber = size;
        this.magicNumber = size;
        AbstractDungeon.actionManager.addToBottom(new CharbossDoCardQueueAction(this));
    }
    
    @Override
    public void upgrade() {
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new EnRegret();
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Regret");
    }
}
