package expansioncontent.actions;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import expansioncontent.cardmods.PropertiesMod;
import expansioncontent.expansionContentMod;

public class EchoACardAction extends AbstractGameAction {
    private final AbstractCard cardToEcho;

    public EchoACardAction(AbstractCard cardToEcho, int amount) {
        this.cardToEcho = cardToEcho;
        this.amount = amount;
    }

    @Override
    public void update() {
        isDone = true;
        if (cardToEcho.hasTag(expansionContentMod.ECHO))
            return;

        AbstractCard card = cardToEcho.makeStatEquivalentCopy();
        PropertiesMod mod = new PropertiesMod(PropertiesMod.supportedProperties.ECHO, false);

        if (!card.isEthereal)
            mod.addProperty(PropertiesMod.supportedProperties.ETHEREAL, false);
        if (!card.exhaust)
            mod.addProperty(PropertiesMod.supportedProperties.EXHAUST, false);

        CardModifierManager.addModifier(card, mod);
        addToTop(new MakeTempCardInHandAction(card, amount));
    }
}
