//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package guardian.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import guardian.GuardianMod;
import guardian.characters.GuardianCharacter;

import java.util.Iterator;

public class PlaceCardsInHandIntoStasisAction extends AbstractGameAction {
    public static final String[] TEXT;

    private boolean anyNumber;

    static {
        TEXT = CardCrawlGame.languagePack.getUIString("Guardian:UIOptions").TEXT;
    }

    public PlaceCardsInHandIntoStasisAction(AbstractCreature source, int amount, boolean anyNumber) {
        this.setValues(AbstractDungeon.player, source, amount);
        this.actionType = ActionType.CARD_MANIPULATION;

        this.anyNumber = anyNumber;
    }

    public void update() {
        if (this.duration == 0.5F) {
            if (AbstractDungeon.player.hand.isEmpty())
            {
                this.isDone = true;
                return;
            }

            AbstractDungeon.handCardSelectScreen.open(TEXT[3], this.amount, false, true, false, false, anyNumber);
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.25F));
        } else {
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                AbstractCard c;
                for (Iterator<AbstractCard> iterator = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator(); iterator.hasNext(); AbstractDungeon.player.hand.addToTop(c)) {
                    c = iterator.next();
                    AbstractDungeon.actionManager.addToTop(new WaitAction(0.1F));
                    AbstractDungeon.actionManager.addToTop(new PlaceActualCardIntoStasis(c, AbstractDungeon.player.hand));
                }

                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            }
        }

        this.tickDuration();
    }
}
