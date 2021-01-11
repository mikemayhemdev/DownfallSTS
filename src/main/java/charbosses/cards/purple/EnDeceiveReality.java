package charbosses.cards.purple;

import charbosses.actions.common.EnemyMakeTempCardInHandAction;
import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.tempCards.Safety;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class EnDeceiveReality extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:DeceiveReality";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("DeceiveReality");
    }

    public EnDeceiveReality() {
        super(ID, cardStrings.NAME, "purple/skill/deceive_reality", 1, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.PURPLE, CardRarity.UNCOMMON, CardTarget.SELF, AbstractMonster.Intent.DEFEND);
        this.baseBlock = 4;
        this.cardsToPreview = new Safety();
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {

        this.addToBot(new GainBlockAction(m, m, this.block));
        this.addToBot(new EnemyMakeTempCardInHandAction(new EnSafety(), 1));
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
        return new EnDeceiveReality();
    }
}
