package collector.cardmods;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import static collector.CollectorMod.makeID;

public class PyreMod extends AbstractCardModifier {
    public static final String ID = makeID("PyreMod");
    private static UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("collector:PyreModText");

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new PyreMod();
    }

    @Override
    public boolean canPlayCard(AbstractCard card) {
        if (AbstractDungeon.player.hand.size() - 1 <= 0) {
            card.cantUseMessage = uiStrings.TEXT[0];
            return false;
        }
        return true;
    }

    @Override
    public boolean shouldApply(AbstractCard card) {
        return !CardModifierManager.hasModifier(card, ID);
    }

    @Override
    public boolean isInherent(AbstractCard card) {
        return true;
    }
}
