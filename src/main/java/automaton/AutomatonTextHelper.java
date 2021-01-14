package automaton;

import automaton.cards.AbstractBronzeCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;

import static automaton.FunctionHelper.WITH_DELIMITER;

public class AutomatonTextHelper {

    private static UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("bronze:AutoTextHelper");

    private static String getDynamicValue(AbstractCard card, char key) {
        switch (key) {
            case 'B':
                if (card.isBlockModified) {
                    if (card.block >= card.baseBlock) {
                        return "[#7fff00]" + card.block + "[]";
                    }
                    return "[#ff6563]" + card.block + "[]";
                }
                return Integer.toString(card.baseBlock);
            case 'D':
                if (card.isDamageModified) {
                    if (card.damage >= card.baseDamage) {
                        return "[#7fff00]" + card.damage + "[]";
                    }
                    return "[#ff6563]" + card.damage + "[]";
                }
                return Integer.toString(card.baseDamage);
            case 'M':
                if (card.isMagicNumberModified) {
                    if (card.magicNumber >= card.baseMagicNumber) {
                        return "[#7fff00]" + card.magicNumber + "[]";
                    }

                    return "[#ff6563]" + card.magicNumber + "[]";
                }
                return Integer.toString(card.baseMagicNumber);
            case 'A':
                if (card instanceof AbstractBronzeCard) {
                    if (((AbstractBronzeCard) card).isAutoModified) {
                        if (((AbstractBronzeCard) card).auto >= ((AbstractBronzeCard) card).baseAuto) {
                            return "[#7fff00]" + ((AbstractBronzeCard) card).auto + "[]";
                        }
                        return "[#ff6563]" + ((AbstractBronzeCard) card).auto + "[]";
                    }
                    return Integer.toString(((AbstractBronzeCard) card).baseAuto);
                }
            default:
                return Integer.toString(-99);
        }
    }

    public static String getInSequenceDescription(AbstractCard card) {
        String x = card.rawDescription;
        if (card.rawDescription.contains(uiStrings.TEXT[0])) {
            String[] splitText = x.split(String.format(WITH_DELIMITER, uiStrings.TEXT[0]));
            String compileText = splitText[1] + splitText[2];
            x = x.replace(compileText, "");
        } else if (card.rawDescription.contains(uiStrings.TEXT[1])) {
            return ""; // It's over!! If you only have Compile effects, you're gone!!!!!
        } // IT NEVER ENDS!!!!!
        if (card.rawDescription.contains(" π")) {
            String[] splitText = x.split(String.format(WITH_DELIMITER, " π"));
            String compileText = splitText[1] + splitText[2];
            x = x.replace(compileText, "");
        } // This one is for cards with other text that doesn't need to be on the Function.
        if (card.rawDescription.contains(" NL \u00A0 ")) {
            String[] splitText = x.split(String.format(WITH_DELIMITER, " NL \u00A0 "));
            String compileText = splitText[0] + splitText[1];
            x = x.replace(compileText, "");
        } // And for non-Function-relevant text before the main card effects.
        if (card.rawDescription.contains(uiStrings.TEXT[2])) {
            x = x.replace(uiStrings.TEXT[2], "");
        }
        if (x.contains("!D!")) {
            x = x.replaceAll("!D!", getDynamicValue(card, 'D'));
        }
        if (x.contains("!B!")) {
            x = x.replaceAll("!B!", getDynamicValue(card, 'B'));
        }
        if (x.contains("!M!")) {
            x = x.replaceAll("!M!", getDynamicValue(card, 'M'));
        }
        if (x.contains("!bauto!")) {
            x = x.replaceAll("!bauto!", getDynamicValue(card, 'A'));
        }
        return x;
    }

    public static String insertEncodeTextBeforeCompile(String rawDescription) {
        if (rawDescription.contains(uiStrings.TEXT[0])) {
            String[] splitText = rawDescription.split(String.format(WITH_DELIMITER, uiStrings.TEXT[0]));
            String compileText = splitText[1] + splitText[2];
            return splitText[0] + uiStrings.TEXT[2] + compileText;
        } else if (rawDescription.contains(uiStrings.TEXT[1])) {
            return uiStrings.TEXT[3] + rawDescription;
        }
        return rawDescription + uiStrings.TEXT[2];
    }

    public static String cleanAllCompileText(String s) {
        if (s.contains(uiStrings.TEXT[0])) {
            String[] splitText = s.split(String.format(WITH_DELIMITER, uiStrings.TEXT[0]));
            String compileText = splitText[1] + splitText[2];
            return s.replaceAll(compileText, "");
        } else if (s.contains(uiStrings.TEXT[1])) {
            return "";
        }
        return s;
    }
}
