package charbosses.cards.curses;

import charbosses.actions.util.CharbossDoCardQueueAction;
import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.curses.Clumsy;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class EnClumsy extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Clumsy";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Clumsy");
    }

    public EnClumsy() {
        super("Clumsy", cardStrings.NAME, "curse/clumsy", -2, cardStrings.DESCRIPTION, CardType.CURSE, CardColor.CURSE, CardRarity.CURSE, CardTarget.NONE);
        this.isEthereal = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    public void triggerOnEndOfPlayerTurn() {
        this.addToTop(new ExhaustSpecificCardAction(this, AbstractCharBoss.boss.hand));
    }

    public void upgrade() {
    }

    public AbstractCard makeCopy() {
        return new EnClumsy();
    }

}
