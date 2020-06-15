package downfall.util;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class EtherealMod extends AbstractCardModifier {

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return "Ethereal. NL " + rawDescription;
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
