//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package downfall.cards.curses;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.downfallMod;

public class PrideStandard extends AbstractCard {
    public static final String ID = "Pride";
    private static final CardStrings cardStrings;

    public PrideStandard() {
        super("Pride", cardStrings.NAME, "curse/pride", 1, cardStrings.DESCRIPTION, CardType.CURSE, CardColor.CURSE, CardRarity.CURSE, CardTarget.SELF);
        this.exhaust = true;
        this.isInnate = true;
        tags.add(downfallMod.DOWNFALL_CURSE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    public void triggerOnEndOfTurnForPlayingCard() {
        this.addToBot(new MakeTempCardInDrawPileAction(this.makeStatEquivalentCopy(), 1, false, true));
    }

    public void upgrade() {
    }

    public AbstractCard makeCopy() {
        return new PrideStandard();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Pride");
    }
}
