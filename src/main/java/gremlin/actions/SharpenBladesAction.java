package gremlin.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class SharpenBladesAction
        extends AbstractGameAction
{
    public SharpenBladesAction()
    {
        this.duration = Settings.ACTION_DUR_XFAST;
    }

    public void update()
    {
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if(c.type == AbstractCard.CardType.ATTACK) {
                c.setCostForTurn(-9);
            }
        }
        this.isDone = true;
    }
}
