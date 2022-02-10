package hermit.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Iterator;

public class EternalAction extends AbstractGameAction {
    private AbstractPlayer p;

    public EternalAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            Iterator var1 = this.p.hand.group.iterator();

            while(var1.hasNext()) {
                AbstractCard c = (AbstractCard)var1.next();
                if (c.costForTurn > 0) {
                    c.costForTurn = c.costForTurn-1;
                    c.isCostModifiedForTurn = true;
                }
            }

            Iterator var2 = this.p.drawPile.group.iterator();

            while(var1.hasNext()) {
                AbstractCard c = (AbstractCard)var1.next();
                if (c.costForTurn > 0) {
                    c.costForTurn = c.costForTurn-1;
                    c.isCostModifiedForTurn = true;
                }
            }

            Iterator var3 = this.p.discardPile.group.iterator();

            while(var1.hasNext()) {
                AbstractCard c = (AbstractCard)var1.next();
                if (c.costForTurn > 0) {
                    c.costForTurn = c.costForTurn-1;
                    c.isCostModifiedForTurn = true;
                }
            }
        }

        this.tickDuration();
    }
}