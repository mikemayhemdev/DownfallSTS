package downfall.cardmods;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import downfall.downfallMod;
import sneckomod.SneckoMod;

public class ExhaustMod extends AbstractCardModifier {

    public static String ID = downfallMod.makeID("ExhaustMod");


    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + CardCrawlGame.languagePack.getUIString(SneckoMod.makeID("ExhaustMod")).TEXT[0];
    }

    public boolean shouldApply(AbstractCard card) {
        return !CardModifierManager.hasModifier(card, ID);
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.exhaust = true;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new ExhaustMod();
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
}
