package expansioncontent.cardmods;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import champ.ChampMod;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import downfall.downfallMod;

public class RetainCardMod extends AbstractCardModifier {
    public static String ID = "downfall:RetainCardMod";

    //Don't use for temporary modification, for that use PropertiesMod instead.

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return CardCrawlGame.languagePack.getUIString(ID).TEXT[0] + rawDescription;
    }

    public boolean shouldApply(AbstractCard card) {
        return !CardModifierManager.hasModifier(card, ID);
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.selfRetain = true;
    }

    @Override
    public void onRemove(AbstractCard card) {
        card.selfRetain = false;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new RetainCardMod();
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    //Ethereal check - do not remove - Stanek
     @Override
   public void onRetained(AbstractCard card) {
       if (card.isEthereal)
            AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(card, AbstractDungeon.player.hand, true));
    }
}
