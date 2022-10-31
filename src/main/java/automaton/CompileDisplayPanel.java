package automaton;

import automaton.cards.AbstractBronzeCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;

public class CompileDisplayPanel extends EasyInfoDisplayPanel {

    private static UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("bronze:CompileDisplayPanel");

    public CompileDisplayPanel() {
        super(600, 900, 400);
    } // NOTE: X, Y, Width are all multipled by settings.scale on constructor, so use values like this.

    @Override
    public String getTitle() {
        return uiStrings.TEXT[0];
    }

    @Override
    public String getDescription() {
        StringBuilder s = new StringBuilder();
        if (FunctionHelper.held != null && FunctionHelper.doStuff)
            for (AbstractCard card : FunctionHelper.held.group) {
                if (card instanceof AbstractBronzeCard)
                    if (card.rawDescription.contains(uiStrings.TEXT[1]) && ((AbstractBronzeCard) card).doSpecialCompileStuff) {
                        String x = ((AbstractBronzeCard) card).getSpecialCompileText();
                        if (!x.equals("")) {
                            s.append(((AbstractBronzeCard) card).getSpecialCompileText());
                            s.append(" NL "); //?
                        }
                    }
            }
        return s.toString();
    }

    @Override
    public RENDER_TIMING getTiming() {
        return RENDER_TIMING.TIMING_ENERGYPANEL_RENDER;
    }
}
