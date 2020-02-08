//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package guardian.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class CardToTopOfDrawPileAction extends AbstractGameAction {

    private static final float DURATION = 0.01F;
    private static final float POST_ATTACK_WAIT_DUR = 0.2F;
    private AbstractCard card;

    public CardToTopOfDrawPileAction(AbstractCard card) {
        this.card = card;
        this.actionType = ActionType.DAMAGE;
        this.duration = 0.01F;

    }

    public void update() {
        AbstractDungeon.player.drawPile.addToTop(card);

        this.isDone = true;
    }

}

