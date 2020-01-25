//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package guardian.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import guardian.GuardianMod;

import java.util.Iterator;

public class PlaceCardsInHandIntoStasisAction extends AbstractGameAction {
    public static final String[] TEXT;

    public PlaceCardsInHandIntoStasisAction(AbstractCreature source, int amount) {
        this.setValues(AbstractDungeon.player, source, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    public void update() {
        if (this.duration == 0.5F) {
            AbstractDungeon.handCardSelectScreen.open(TEXT[3], this.amount, false, true, false, false, true);
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.25F));
            this.tickDuration();
        } else {
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                AbstractCard c;
                for(Iterator var1 = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator(); var1.hasNext(); AbstractDungeon.player.hand.addToTop(c)) {
                    c = (AbstractCard)var1.next();
                    if (GuardianMod.canSpawnStasisOrb()) {

                        c.retain = true;
                        AbstractDungeon.actionManager.addToBottom(new PlaceActualCardIntoStasis(c));
                        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1F));
                    }

                }

                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            }

            this.tickDuration();
        }
    }

    static {
        TEXT = CardCrawlGame.languagePack.getUIString("Guardian:UIOptions").TEXT;
    }
}
