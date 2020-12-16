package automaton.cardmods;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public abstract class BronzeCardMod extends AbstractCardModifier {

    protected void atb(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

    protected void att(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToTop(action);
    }
}
