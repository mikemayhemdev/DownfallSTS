package collector.cardmods;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static collector.CollectorMod.makeID;

public class HoardedMod extends AbstractCardModifier {
    public static final String ID = makeID("HoardedMod");

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
}
