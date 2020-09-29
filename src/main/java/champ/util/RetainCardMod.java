package champ.util;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class RetainCardMod extends AbstractCardModifier {

    private boolean grantedRetain;

    @Override
    public void onInitialApplication(AbstractCard card) {
        if (!card.selfRetain) {
            grantedRetain = true;
            card.selfRetain = true;
            card.rawDescription = "Retain. NL " + card.rawDescription;
            card.initializeDescription();
        }
    }

    @Override
    public void onRemove(AbstractCard card) {
        if (grantedRetain) {
            card.selfRetain = false;
            card.rawDescription = card.rawDescription.replaceAll("Retain. NL ", "");
            card.initializeDescription();
        }
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new RetainCardMod();
    }
}
