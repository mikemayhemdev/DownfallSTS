package guardian.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import expansioncontent.util.DownfallAchievementUnlocker;
import expansioncontent.util.DownfallAchievementVariables;
import guardian.cards.InStasisCard;


public class ReturnStasisCardToHandAction extends AbstractGameAction {
    private final AbstractCard card;

    public ReturnStasisCardToHandAction(AbstractCard card) {
        this.card = card;
        this.actionType = ActionType.DAMAGE;
    }

    public void update() {
        if (!AbstractDungeon.player.hand.contains(this.card)) {
            AbstractDungeon.player.hand.addToHand(this.card);
            DownfallAchievementVariables.cardsReturnedFromStasis++;
            if (DownfallAchievementVariables.cardsReturnedFromStasis >= 10) {
                DownfallAchievementUnlocker.unlockAchievement("HYPER_ACCELERATION");
            }
            if (this.card instanceof InStasisCard) {
                ((InStasisCard) this.card).whenReturnedFromStasis();
            }
            this.card.update();
            AbstractDungeon.player.hand.refreshHandLayout();
        }
        this.isDone = true;
    }
}
