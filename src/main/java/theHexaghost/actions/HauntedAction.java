package theHexaghost.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theHexaghost.GhostflameHelper;
import theHexaghost.HexaMod;

import java.util.ArrayList;

public class HauntedAction extends AbstractGameAction {

    AbstractCard sourceHaunted;

    public HauntedAction(AbstractCard source) {
        sourceHaunted = source;
    }

    public void update() {
        ArrayList<AbstractCard> cg = AbstractDungeon.player.hand.group;
        if (cg.contains(this.sourceHaunted)) {
            cg.remove(this);
            if (cg.size() > 0) {
                this.sourceHaunted.flash();
                this.sourceHaunted.superFlash();
                AbstractCard c = cg.get(AbstractDungeon.cardRng.random(0, cg.size() - 1));
                AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
            }
        }
        isDone = true;
    }
}
