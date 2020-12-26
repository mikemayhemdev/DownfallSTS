package automaton.cardmods;

import automaton.AutomatonMod;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class EncodeAndShuffleMod extends BronzeCardMod {

    public static String ID = "bronze:EncodeModifier";


    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + " NL bronze:Encode, then add a copy of this into your discard pile."; //TODO Hardcoded string
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        if (!card.purgeOnUse) {
            atb(new MakeTempCardInDiscardAction(card.makeStatEquivalentCopy(), 1));
        }
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        super.onInitialApplication(card);
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
