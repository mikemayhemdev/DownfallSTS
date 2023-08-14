package expansioncontent.cardmods;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import expansioncontent.expansionContentMod;

import java.util.ArrayList;

public class UnplayableMod extends AbstractCardModifier {
    public static String ID = "downfall:UnplayableMod";
    public String message;

    //Don't use for temporary modification, for that use PropertiesMod instead.

    public UnplayableMod() {
        priority = 6;
        message = CardCrawlGame.languagePack.getUIString(ID).TEXT[1];
    }

    //Enjoy this small flavor tool
    public void updateMessage(String newMessage) {
        message = newMessage;
    }

    //A method for the sake of convenience.
    public static UnplayableMod getUnplayableModFromCard(AbstractCard card) {
        ArrayList<AbstractCardModifier> mods = CardModifierManager.getModifiers(card, UnplayableMod.ID);

        if (mods.isEmpty() || !(mods.get(0) instanceof UnplayableMod))
            return new UnplayableMod();

        return (UnplayableMod) mods.get(0);
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return CardCrawlGame.languagePack.getUIString(ID).TEXT[0] + rawDescription;
    }

    public boolean shouldApply(AbstractCard card) {
        return !CardModifierManager.hasModifier(card, ID);
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.tags.add(expansionContentMod.UNPLAYABLE);
    }

    @Override
    public void onRemove(AbstractCard card) {
        card.tags.remove(expansionContentMod.UNPLAYABLE);
    }

    @Override
    public boolean canPlayCard(AbstractCard card) {
        card.cantUseMessage = message;
        return false;
    }

    //Making sure that copy also has the flavor.
    @Override
    public AbstractCardModifier makeCopy() {
        UnplayableMod copy = new UnplayableMod();
        copy.updateMessage(message);
        return copy;
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public boolean isInherent(AbstractCard card) {
        return false;
    }
}
