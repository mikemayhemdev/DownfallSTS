package collector.cardmods;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.Arrays;
import java.util.List;

import static collector.CollectorMod.makeID;

public class ActuallyCollectedCardMod extends AbstractCardModifier {
    public static final String ID = makeID("ActuallyCollectedCardMod");

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new ActuallyCollectedCardMod();
    }

    @Override
    public boolean shouldApply(AbstractCard card) {
        return !CardModifierManager.hasModifier(card, ID);
    }

    @Override
    public List<String> extraDescriptors(AbstractCard card) {
        return Arrays.asList(CollectedCardMod.uiStrings.TEXT[1]);
    }
}
