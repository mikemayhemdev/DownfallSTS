package downfall.cardmods;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import downfall.downfallMod;

public class EtherealMod extends AbstractCardModifier {

    public static String ID = downfallMod.makeID("EtherealMod");

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return CardCrawlGame.languagePack.getUIString(downfallMod.makeID("EtherealMod")).TEXT[0] + rawDescription;
    }

    public boolean shouldApply(AbstractCard card) {
        return !CardModifierManager.hasModifier(card, ID);
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.isEthereal = true;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new EtherealMod();
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
}
