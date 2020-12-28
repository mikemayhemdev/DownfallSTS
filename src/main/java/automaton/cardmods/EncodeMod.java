package automaton.cardmods;

import automaton.AutomatonMod;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static automaton.FunctionHelper.WITH_DELIMITER;

public class EncodeMod extends BronzeCardMod {

    public static String ID = "bronze:EncodeModifier";


    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        if (card.rawDescription.contains(" NL bronze:Compile")) {//TODO:Localize
            String[] splitText = card.rawDescription.split(String.format(WITH_DELIMITER, " NL bronze:Compile"));//TODO:Localize
            String compileText = splitText[1] + splitText[2];
            return splitText[0] + " NL bronze:Encode." + compileText; //TODO: Localize
        } else if (card.rawDescription.contains("bronze:Compile")) { //TODO: Localize
            return "bronze:Encode. NL " + card.rawDescription; // TODO: Localize
        }
        return rawDescription + " NL bronze:Encode."; //TODO: Localize
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        super.onInitialApplication(card);
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
