package automaton.cardmods;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class EncodeMod extends BronzeCardMod {

    public static String ID = "bronze:EncodeModifier";


    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + " NL bronze:Encode.";
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new EncodeMod();
    }
}
