package champ.util;

import basemod.abstracts.AbstractCardModifier;
import champ.ChampMod;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class RetainCardMod extends AbstractCardModifier {

    private boolean grantedRetain;

    @Override
    public void onInitialApplication(AbstractCard card) {
        if (!card.selfRetain) {
            grantedRetain = true;
            card.selfRetain = true;
            card.rawDescription = CardCrawlGame.languagePack.getUIString(ChampMod.makeID("RetainCardMod")).TEXT[0] + card.rawDescription;
            card.initializeDescription();
        }
    }



    @Override
    public void onRemove(AbstractCard card) {
        if (grantedRetain) {
            card.selfRetain = false;
            card.rawDescription = card.rawDescription.replaceAll(CardCrawlGame.languagePack.getUIString(ChampMod.makeID("RetainCardMod")).TEXT[0], "");
            card.initializeDescription();
        }
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new RetainCardMod();
    }
}
