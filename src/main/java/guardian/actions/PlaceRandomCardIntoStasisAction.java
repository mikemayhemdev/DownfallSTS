package guardian.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import guardian.GuardianMod;
import guardian.orbs.StasisOrb;


public class PlaceRandomCardIntoStasisAction extends AbstractGameAction {
    private int numCards;

    public PlaceRandomCardIntoStasisAction(int numCards) {
        this.actionType = ActionType.DAMAGE;
        this.numCards = numCards;
    }

    public void update() {
        if (this.numCards == 0) {
            this.isDone = true;
        }

        if (AbstractDungeon.player.drawPile.isEmpty() && AbstractDungeon.player.discardPile.isEmpty()) {
            this.isDone = true;
        } else {
            if (AbstractDungeon.player.drawPile.isEmpty()) {
                AbstractDungeon.actionManager.addToBottom(new EmptyDeckShuffleAction());
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1F));
                AbstractDungeon.actionManager.addToBottom(new PlaceRandomCardIntoStasisAction(this.numCards ));

            } else {
                if (GuardianMod.canSpawnStasisOrb())
                {
                    AbstractDungeon.actionManager.addToBottom(new ChannelAction(new StasisOrb(AbstractDungeon.player.drawPile.getRandomCard(true), false)));
                    AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1F));
                    if (this.numCards - 1 > 0) AbstractDungeon.actionManager.addToBottom(new PlaceRandomCardIntoStasisAction(this.numCards - 1));
                }

               }
        }

        this.isDone = true;
    }
}
