package downfall.cardmods;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import champ.ChampMod;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import downfall.downfallMod;

public class RetainCardMod extends AbstractCardModifier {

    public static String ID = downfallMod.makeID("RetainCardMod");

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return CardCrawlGame.languagePack.getUIString(ChampMod.makeID("RetainCardMod")).TEXT[0] + rawDescription;
    }

    public boolean shouldApply(AbstractCard card) {
        return !CardModifierManager.hasModifier(card, ID);
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.selfRetain = true;
    }


    @Override
    public AbstractCardModifier makeCopy() {
        return new RetainCardMod();
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
}
