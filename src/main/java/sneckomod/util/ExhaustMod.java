package sneckomod.util;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import sneckomod.SneckoMod;

public class ExhaustMod extends AbstractCardModifier {


    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + CardCrawlGame.languagePack.getUIString(SneckoMod.makeID("ExhaustMod")).TEXT[0];
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.exhaust = true;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new ExhaustMod();
    }
}
