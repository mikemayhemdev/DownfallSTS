package automaton.cardmods;

import automaton.AutomatonMod;
import automaton.AutomatonTextHelper;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static automaton.FunctionHelper.WITH_DELIMITER;

public class EncodeMod extends BronzeCardMod {

    public static String ID = "bronze:EncodeModifier";


    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return AutomatonTextHelper.insertEncodeTextBeforeCompile(rawDescription);
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        if (!card.hasTag(AutomatonMod.ENCODES)) {
            card.tags.add(AutomatonMod.ENCODES);
        }
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
