package downfall.util;

import basemod.abstracts.AbstractCardModifier;
import champ.ChampMod;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class RetainCardMod extends AbstractCardModifier {

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return CardCrawlGame.languagePack.getUIString(ChampMod.makeID("RetainCardMod")).TEXT[0] + rawDescription;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.selfRetain = true;
    }


    @Override
    public AbstractCardModifier makeCopy() {
        return new RetainCardMod();
    }
}
