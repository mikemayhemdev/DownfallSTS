package expansioncontent.cardmods;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class EtherealMod extends AbstractCardModifier {
    public static String ID = "downfall:EtherealMod";

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
        card.isEthereal = true;
    }

    @Override
    public void onRemove(AbstractCard card) {
        card.isEthereal = false;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new EtherealMod();
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    //Retain Override - do not remove - Stanek
    //sorry - blue
    @Override
        public void onRetained(AbstractCard card) {
            AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(card, AbstractDungeon.player.hand, true));
        }
}