package charbosses.cards.status;

import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class EnDazed extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Dazed";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Dazed");
    }

    public EnDazed() {
        super(ID, EnDazed.cardStrings.NAME, "status/dazed", -2, EnDazed.cardStrings.DESCRIPTION, CardType.STATUS, CardColor.COLORLESS, CardRarity.COMMON, CardTarget.NONE, AbstractMonster.Intent.MAGIC);
        this.isEthereal = true;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
    }

    @Override
    public void upgrade() {
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnDazed();
    }
}
