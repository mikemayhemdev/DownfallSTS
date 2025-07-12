package awakenedOne.cardmods;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import static awakenedOne.AwakenedOneMod.SPELLCARD;

public class SpellMod extends AbstractCardModifier {

    public static String ID = "awakened:SpellMod";


    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return CardCrawlGame.languagePack.getUIString("awakened:SpellMod").TEXT[0]+rawDescription;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.tags.add(SPELLCARD);
        card.initializeDescription();
    }

    @Override
    public void onRemove(AbstractCard card) {
        card.tags.remove(SPELLCARD);
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
        return new SpellMod();
    }

}