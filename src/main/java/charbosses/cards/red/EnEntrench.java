package charbosses.cards.red;

import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.unique.DoubleYourBlockAction;
import com.megacrit.cardcrawl.actions.utility.ExhaustAllEtherealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class EnEntrench extends AbstractBossCard {
    public static final String ID = "EvilWithin_Charboss:Entrench";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Entrench");
    }

    public EnEntrench() {
        super(ID, EnEntrench.cardStrings.NAME, "red/skill/entrench", 2, EnEntrench.cardStrings.DESCRIPTION, CardType.SKILL, CardColor.RED, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseBlock = this.block = 10;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new DoubleYourBlockAction(m));
    }

    @Override
    public void triggerOnEndOfPlayerTurn() {
        this.addToTop(new ExhaustAllEtherealAction());
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public int getValue() {
        if (AbstractCharBoss.boss != null && !AbstractCharBoss.boss.isDead) {
            return AbstractCharBoss.boss.currentBlock + 8;
        }
        return super.getValue();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(1);
            this.rawDescription = EnEntrench.cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnEntrench();
    }
}
