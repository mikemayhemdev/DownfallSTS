package downfall.util;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import downfall.downfallMod;

public class EtherealMod extends AbstractCardModifier {

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return CardCrawlGame.languagePack.getUIString(downfallMod.makeID("EtherealMod")).TEXT[0] + rawDescription;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.isEthereal = true;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new EtherealMod();
    }
}
