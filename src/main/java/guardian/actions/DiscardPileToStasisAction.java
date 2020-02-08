//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package guardian.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import guardian.GuardianMod;

import java.util.Iterator;

public class DiscardPileToStasisAction extends AbstractGameAction {
    public static final String[] TEXT;

    static {
        TEXT = CardCrawlGame.languagePack.getUIString("Guardian:UIOptions").TEXT;
    }

    private AbstractPlayer p;
    private int amount;

    public DiscardPileToStasisAction(int amount) {
        this.p = AbstractDungeon.player;
        this.setValues(this.p, AbstractDungeon.player);
        this.amount = amount;
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    public void update() {
        if (this.p.discardPile.size() == 1) {
            AbstractCard card = (AbstractCard) this.p.discardPile.group.get(0);
            card.lighten(false);
            AbstractDungeon.actionManager.addToBottom(new PlaceActualCardIntoStasis(card));
            this.isDone = true;
        } else if (this.duration == 0.5F) {
            AbstractDungeon.gridSelectScreen.open(this.p.discardPile, this.amount, false, TEXT[0]);

            this.tickDuration();
        } else {
            if (AbstractDungeon.gridSelectScreen.selectedCards.size() != 0) {
                GuardianMod.logger.info("DiscardPileToStasisAction: card highlighted");
                Iterator var1 = AbstractDungeon.gridSelectScreen.selectedCards.iterator();

                AbstractCard c;
                while (var1.hasNext()) {
                    c = (AbstractCard) var1.next();
                    if (GuardianMod.canSpawnStasisOrb()) {

                        AbstractDungeon.actionManager.addToBottom(new PlaceActualCardIntoStasis(c));
                    }
                    c.lighten(false);
                    c.unhover();
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();

                for (var1 = this.p.discardPile.group.iterator(); var1.hasNext(); c.target_y = 0.0F) {
                    c = (AbstractCard) var1.next();
                    c.unhover();
                    c.target_x = (float) CardGroup.DISCARD_PILE_X;
                }

                this.isDone = true;
            }

            this.tickDuration();
        }
    }
}
