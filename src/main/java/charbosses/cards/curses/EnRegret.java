package charbosses.cards.curses;

import charbosses.actions.util.CharbossDoCardQueueAction;
import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class EnRegret extends AbstractBossCard {
    public static final String ID = "EvilWithin_Charboss:Regret";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Regret");
    }

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
}
