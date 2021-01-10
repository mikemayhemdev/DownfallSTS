package automaton.cardmods;

import automaton.AutomatonMod;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;

public class EncodeAndShuffleMod extends BronzeCardMod {

    public static String ID = "bronze:EncodeModifier";

    protected static UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("bronze:EncodeAndShuffleMod");


    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + uiStrings.TEXT[0];
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        if (!card.purgeOnUse) {
            atb(new MakeTempCardInDiscardAction(card.makeStatEquivalentCopy(), 1));
        }
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.tags.add(AutomatonMod.ENCODES);
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new EncodeAndShuffleMod();
    }
}
