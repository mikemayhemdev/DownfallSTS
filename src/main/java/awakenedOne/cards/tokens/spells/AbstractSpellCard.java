package awakenedOne.cards.tokens.spells;

import awakenedOne.cards.AbstractAwakenedCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.Arrays;
import java.util.List;

import static awakenedOne.AwakenedOneMod.makeID;

public abstract class AbstractSpellCard extends AbstractAwakenedCard {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(makeID("SpellDescriptor")); // TODO: Loc

    public AbstractSpellCard(String cardID, AbstractCard.CardType type, AbstractCard.CardTarget target) {
        super(cardID, 1, type, AbstractCard.CardRarity.SPECIAL, target, AbstractCard.CardColor.COLORLESS);
        exhaust = true;
    }

    @Override
    public List<String> getCardDescriptors() {
        return Arrays.asList(uiStrings.TEXT[0]);
    }
}
