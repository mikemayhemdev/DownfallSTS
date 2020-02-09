package charbosses.cards.curses;

import charbosses.cards.AbstractBossCard;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.SoulboundField;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class EnCurseOfTheBell extends AbstractBossCard {
    public static final String ID = "EvilWithin_Charboss:CurseOfTheBell";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("CurseOfTheBell");
    }

    public EnCurseOfTheBell() {
        super(ID, EnCurseOfTheBell.cardStrings.NAME, "curse/curse_of_the_bell", -2, EnCurseOfTheBell.cardStrings.DESCRIPTION, CardType.CURSE, CardColor.CURSE, CardRarity.SPECIAL, CardTarget.NONE);
        SoulboundField.soulbound.set(this, true);
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
    }

    @Override
    public void upgrade() {
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnCurseOfTheBell();
    }
}
