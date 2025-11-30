package expansioncontent.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class PandemoniumAction extends AbstractGameAction {

    public PandemoniumAction() {
    }

    @Override
    public void update() {
        isDone = true;
        if (!AbstractDungeon.actionManager.turnHasEnded) {
            if (!AbstractDungeon.player.discardPile.isEmpty()) {
                AbstractCard cardToPlay = AbstractDungeon.player.discardPile.getRandomCard(AbstractDungeon.cardRandomRng);
                AbstractDungeon.player.discardPile.removeCard(cardToPlay);
                AbstractDungeon.getCurrRoom().souls.remove(cardToPlay);
                AbstractDungeon.player.limbo.group.add(cardToPlay);
                AbstractMonster randomMonster = AbstractDungeon.getCurrRoom().monsters.getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
                this.addToTop(new NewQueueCardAction(cardToPlay, randomMonster, false, true));
               this.addToTop(new UnlimboAction(cardToPlay));
            }
        }
    }
}
