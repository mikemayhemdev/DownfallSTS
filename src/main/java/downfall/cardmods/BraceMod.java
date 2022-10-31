package downfall.cardmods;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import guardian.GuardianMod;
import guardian.cards.AbstractGuardianCard;

public class BraceMod extends AbstractCardModifier {


    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + CardCrawlGame.languagePack.getUIString(GuardianMod.makeID("BraceMod")).TEXT[0];
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        super.onUse(card, target, action);
        AbstractGuardianCard.brace(2);
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new BraceMod();
    }
}
