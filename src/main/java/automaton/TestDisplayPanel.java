package automaton;

import automaton.cards.AbstractBronzeCard;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class TestDisplayPanel extends EasyInfoDisplayPanel {

    public TestDisplayPanel() {
        super(1500, 900, 200);
    } // NOTE: X, Y, Width are all multipled by settings.scale on constructor, so use values like this.

    @Override
    public String getTitle() {
        return "Compile Data";
    }

    @Override
    public String getDescription() {
        String q = "NORENDER";
        boolean flip = false;
        StringBuilder s = new StringBuilder("When #yCompiled: NL ");
        if (FunctionHelper.held != null)
            for (AbstractCard card : FunctionHelper.held.group) {
                if (card.rawDescription.contains("bronze:Compile")) {
                    flip = true;
                    String[] splitText = card.rawDescription.split("bronze:Compile");
                    String compileText = splitText[1];
                    s.append(compileText.replaceAll("bronze:", "#y").replaceAll("!D!", String.valueOf(card.damage)) .replaceAll("!B!", String.valueOf(card.block)).replaceAll("!M!", String.valueOf(card.magicNumber)).replaceAll("!bauto!", (String.valueOf( ((AbstractBronzeCard)card).auto))));
                } //TODO: This entire thing is terrible and placeholder. Make it good eventually!
            }
        if (!flip) {
            return q;
        }
        return s.toString();
    }
}
