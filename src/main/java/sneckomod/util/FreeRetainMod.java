package sneckomod.util;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import sneckomod.SneckoMod;

public class FreeRetainMod extends AbstractCardModifier {


    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return  CardCrawlGame.languagePack.getUIString(SneckoMod.makeID("FreeRetainMod")).TEXT[0] + rawDescription;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.selfRetain = true;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new FreeRetainMod();
    }
}
