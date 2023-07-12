package expansioncontent.cardmods;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class ExhaustMod extends AbstractCardModifier {
    public static String ID = "downfall:ExhaustMod";

    //Don't use for temporary modification, for that use PropertiesMod instead.

    public ExhaustMod() {
        this.priority = 99; //Making sure Exhaust is the last line of the text on the card.
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + CardCrawlGame.languagePack.getUIString(ID).TEXT[0];
    }

    public boolean shouldApply(AbstractCard card) {
        return !CardModifierManager.hasModifier(card, ID);
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.exhaust = true;
    }

    @Override
    public void onRemove(AbstractCard card) {
        card.exhaust = false;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new ExhaustMod();
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public boolean isInherent(AbstractCard card) {
        return true;
    }
}
