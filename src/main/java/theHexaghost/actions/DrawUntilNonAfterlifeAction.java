package theHexaghost.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theHexaghost.HexaMod;

public class DrawUntilNonAfterlifeAction extends AbstractGameAction {
    private AbstractPlayer p;

    public DrawUntilNonAfterlifeAction() {
        this.p = AbstractDungeon.player;
        this.setValues(this.p, this.p);
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_MED) {
            if ((p.drawPile.isEmpty() && p.discardPile.isEmpty()) || p.hand.size() >= 10) {
                this.isDone = true;
                return;
            }

            if (AbstractDungeon.player.hasPower("No Draw"))
            {
                AbstractDungeon.player.getPower("No Draw").flash();
                this.isDone = true;
                return;
            }

            if (p.drawPile.isEmpty()) {
                this.addToTop(new EmptyDeckShuffleAction());
            } else {
                AbstractCard right = AbstractDungeon.player.hand.group.get(AbstractDungeon.player.hand.size() - 1); // need to draw 1 card first to make this cehck valid
                if (right.hasTag(HexaMod.AFTERLIFE)) {
                    this.addToTop(new DrawUntilNonAfterlifeAction());
                    this.addToTop(new DrawCardAction(1));
                }
            }

            this.isDone = true;
            return;
        }

        this.tickDuration();
    }
}
