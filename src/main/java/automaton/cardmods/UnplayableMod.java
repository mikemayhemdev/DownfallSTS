package automaton.cardmods;

import automaton.AutomatonMod;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class UnplayableMod extends BronzeCardMod {

    public static String ID = "bronze:UnplayableModifier";

    public UnplayableMod() {
        priority = 99;
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return CardCrawlGame.languagePack.getUIString(AutomatonMod.makeID("UnplayableMod")).TEXT[0] + rawDescription;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.cost = -2;
        card.costForTurn = -2;
    }

    @Override
    public boolean canPlayCard(AbstractCard card) {
        card.cantUseMessage = "ERR_NULL_PTR"; //TODO: Localize
        return false;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new UnplayableMod();
    }
}
