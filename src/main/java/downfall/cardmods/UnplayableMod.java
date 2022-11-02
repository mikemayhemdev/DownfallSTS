package downfall.cardmods;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import downfall.downfallMod;

public class UnplayableMod extends BronzeCardMod {

    public static String ID = "downfall:UnplayableModifier";

    public UnplayableMod() {
        priority = 99;
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return CardCrawlGame.languagePack.getUIString(downfallMod.makeID("UnplayableMod")).TEXT[0] + rawDescription;
    }

    public boolean shouldApply(AbstractCard card) {
        return !CardModifierManager.hasModifier(card, ID);
    }

    @Override
    public boolean canPlayCard(AbstractCard card) {
        card.cantUseMessage = CardCrawlGame.languagePack.getUIString(downfallMod.makeID("UnplayableMod")).TEXT[1];
        return false;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new UnplayableMod();
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
}
