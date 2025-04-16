package hermit.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

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
            if (tracker >= energy || (p.drawPile.isEmpty() && p.discardPile.isEmpty()) || p.hand.size() >= BaseMod.MAX_HAND_SIZE) {
                this.isDone = true;
                return;
            }

            if (AbstractDungeon.player.hasPower("No Draw"))
            {
                AbstractDungeon.player.getPower("No Draw").flash();
                this.isDone = true;
                return;
            }

            if (!p.drawPile.isEmpty()) {
                AbstractCard c = (AbstractCard) AbstractDungeon.player.drawPile.group.get(AbstractDungeon.player.drawPile.size() - 1);
                if (c.cost > 0)
                tracker += c.cost;

                this.addToTop(new LuckDrawAction(energy-tracker));
                this.addToTop(new DrawCardAction(1));
            }
            else
            {
                this.addToTop(new LuckDrawAction(energy-tracker));
                this.addToTop(new EmptyDeckShuffleAction());
            }

            this.isDone = true;
            return;
        }

        this.tickDuration();
    }
}
