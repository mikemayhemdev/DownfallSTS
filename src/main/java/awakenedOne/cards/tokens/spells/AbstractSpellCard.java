package awakenedOne.cards.tokens.spells;

import awakenedOne.cards.AbstractAwakenedCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.Collections;
import java.util.List;

import static awakenedOne.AwakenedOneMod.makeID;

public abstract class AbstractSpellCard extends AbstractAwakenedCard {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(makeID("SpellDescriptor"));

    public AbstractSpellCard(String cardID, CardType type, CardTarget target) {
        super(cardID, 0, type, CardRarity.SPECIAL, target, CardColor.COLORLESS);
        exhaust = true;
    }

    @Override
    public List<String> getCardDescriptors() {
        return Collections.singletonList(uiStrings.TEXT[0]);
    }
}
