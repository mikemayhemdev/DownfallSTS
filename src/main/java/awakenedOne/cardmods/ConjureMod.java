package awakenedOne.cardmods;

import awakenedOne.actions.ConjureAction;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import static awakenedOne.AwakenedOneMod.DELVE;
import static awakenedOne.util.Wiz.atb;

public class ConjureMod extends AbstractCardModifier {

    public static String ID = "awakened:ConjureMod";

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + CardCrawlGame.languagePack.getUIString("awakened:ConjureMod").TEXT[0];
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        super.onUse(card, target, action);
        atb(new ConjureAction(false));
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.tags.add(DELVE);
        card.initializeDescription();
    }

    @Override
    public void onRemove(AbstractCard card) {
        card.tags.remove(DELVE);
        card.initializeDescription();
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public boolean shouldApply(AbstractCard card) {
        return !CardModifierManager.hasModifier(card, ID);
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new ConjureMod();
    }

}