package hermit.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Iterator;

public class LuckDrawAction extends AbstractGameAction {
    private AbstractPlayer p;
    private CardType typeToCheck;
    private int energy;
    private int tracker = 0;

    public LuckDrawAction(int energy) {
        this.p = AbstractDungeon.player;
        this.setValues(this.p, this.p);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;
        this.typeToCheck = CardType.CURSE;
        this.energy=energy;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_MED) {
            if (tracker >= energy || (p.drawPile.isEmpty() && p.discardPile.isEmpty()) || p.hand.size() >= 10) {
                this.isDone = true;
                return;
            }

            if (!p.drawPile.isEmpty()) {
                AbstractCard c = (AbstractCard) AbstractDungeon.player.drawPile.group.get(AbstractDungeon.player.drawPile.size() - 1);
                if (c.cost > 0)
                    tracker += c.cost;

                this.addToBot(new DrawCardAction(1));
            }

            if (p.drawPile.size() <= 1)
                this.addToBot(new EmptyDeckShuffleAction());

            this.addToBot(new LuckDrawAction(energy-tracker));

            this.isDone = true;
            return;
        }

        this.tickDuration();
    }
}
