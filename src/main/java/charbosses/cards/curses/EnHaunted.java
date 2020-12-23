package charbosses.cards.curses;

import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.HexaMod;

public class EnHaunted extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Haunted";
    private static final CardStrings cardStrings;
    public static final String IMG_PATH = "haunted.png";

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(HexaMod.makeID("Haunted"));
    }

    public EnHaunted() {
        super(ID, EnHaunted.cardStrings.NAME, HexaMod.makeCardPath(IMG_PATH), -2, EnHaunted.cardStrings.DESCRIPTION, CardType.CURSE, CardColor.CURSE, CardRarity.CURSE, CardTarget.NONE, AbstractMonster.Intent.MAGIC);
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
    }

    @Override
    public void upgrade() {
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnHaunted();
    }
}
