package collector.cardmods;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;

import static collector.CollectorMod.makeID;

public class HoardedMod extends AbstractCardModifier {
    public static final String ID = makeID("HoardedMod");
    private static UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);

    private boolean hadRetain;

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        hadRetain = card.selfRetain;
        card.selfRetain = true;
    }

    @Override
    public boolean removeAtEndOfTurn(AbstractCard card) {
        return true;
    }

    @Override
    public void onRemove(AbstractCard card) {
        card.selfRetain = hadRetain;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new HoardedMod();
    }

    @Override
    public boolean canPlayCard(AbstractCard card) {
        return false;
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        //TODO: Don't double up keywords
        String s = rawDescription;
        s = uiStrings.TEXT[0] + s;
        if (!hadRetain) {
            s = uiStrings.TEXT[1] + s;
        }
        return s;
    }
}
