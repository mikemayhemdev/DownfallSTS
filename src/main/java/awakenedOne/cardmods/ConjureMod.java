package awakenedOne.cardmods;

import awakenedOne.actions.ConjureAction;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;

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
    public AbstractCardModifier makeCopy() {
        return new ConjureMod();
    }

}