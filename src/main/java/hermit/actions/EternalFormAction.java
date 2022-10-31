package hermit.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Iterator;

public class EternalFormAction extends AbstractGameAction {
    private AbstractPlayer p;

    public EternalFormAction(int amount) {
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
        this.amount = amount;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            Iterator var1 = AbstractDungeon.player.hand.group.iterator();

            while(var1.hasNext()) {
                AbstractCard c = (AbstractCard)var1.next();
                if (c.costForTurn > 0) {
                    c.costForTurn = Math.max(c.costForTurn - this.amount,0);
                    c.isCostModifiedForTurn = true;
                    c.superFlash(Color.GOLD.cpy());
                }
            }
        }

        this.tickDuration();
    }
}