package timeeater.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsCenteredAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import timeeater.TimeEaterChar;
import timeeater.actions.SuspendAction;

import java.util.ArrayList;

import static timeeater.util.Wiz.atb;
import static timeeater.util.Wiz.att;

public abstract class AbstractMemoryCard extends AbstractTimeEaterCard {
    public static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("time:Misc");

    public AbstractMemoryCard(final String cardID, final int cost, final CardRarity rarity) {
        super(cardID, cost, CardType.SKILL, rarity, CardTarget.SELF, TimeEaterChar.Enums.MAGENTA);
        cardToPreview.addAll(choices());
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new SelectCardsCenteredAction(choices(), uiStrings.TEXT[0], (cards) -> {
            att(new SuspendAction(cards.get(0)));
        }));
    }

    @Override
    public void upp() {
        upgradeCardToPreview();
        uDesc();
    }

    protected abstract ArrayList<AbstractCard> cards();

    protected ArrayList<AbstractCard> choices() {
        ArrayList<AbstractCard> retVal = cards();
        if (upgraded) for (AbstractCard q : retVal) q.upgrade();
        return retVal;
    }
}
