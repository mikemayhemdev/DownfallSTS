package hermit.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ComboAction extends AbstractGameAction {
    private AbstractPlayer p;
    public AbstractCard card;
    public boolean actual;

    public ComboAction(boolean actual, AbstractCard card) {
        this.p = AbstractDungeon.player;
        this.setValues(this.p, this.p, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;
        this.card = card;
        this.actual = actual;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_MED) {

            if (this.actual) {
                if (AbstractDungeon.player.hand.size() < BaseMod.MAX_HAND_SIZE && AbstractDungeon.player.discardPile.group.contains(card)) {
                    AbstractDungeon.player.hand.addToHand(this.card);
                    AbstractDungeon.player.discardPile.removeCard(this.card);
                }
            }
            else
            {
                this.addToBot( new ComboAction(true,card));
            }

            this.isDone = true;
        }

        this.tickDuration();
    }
}
