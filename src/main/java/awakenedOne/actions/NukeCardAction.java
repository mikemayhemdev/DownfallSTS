package awakenedOne.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import guardian.orbs.StasisOrb;


public class NukeCardAction extends AbstractGameAction {
    public AbstractCard card;


    public NukeCardAction(AbstractCard card) {
        this.card = card;
        this.duration = this.startDuration = Settings.ACTION_DUR_XFAST;
    }

    public void update() {
        this.isDone = true;
        for (int i = 0; i < AbstractDungeon.player.drawPile.size(); ) {
            AbstractCard c = AbstractDungeon.player.drawPile.group.get(i);
            if (c.uuid == card.uuid) {
                addToBot((AbstractGameAction)new ExhaustSpecificCardAction(c, AbstractDungeon.player.drawPile, true));
            }
        }

        for (int i = 0; i < AbstractDungeon.player.hand.size(); ) {
            AbstractCard c = AbstractDungeon.player.hand.group.get(i);
            if (c.uuid == card.uuid) {
                addToBot((AbstractGameAction)new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand, true));
            }
        }

        for (int i = 0; i < AbstractDungeon.player.discardPile.size(); ) {
            AbstractCard c = AbstractDungeon.player.discardPile.group.get(i);
            if (c.uuid == card.uuid) {
                addToBot((AbstractGameAction)new ExhaustSpecificCardAction(c, AbstractDungeon.player.discardPile, true));
            }
        }

        for (int i = 0; i < AbstractDungeon.player.limbo.size(); ) {
            AbstractCard c = AbstractDungeon.player.limbo.group.get(i);
            if (c.uuid == card.uuid) {
                addToBot((AbstractGameAction)new ExhaustSpecificCardAction(c, AbstractDungeon.player.limbo, true));
            }
        }

        //AAAAAAHHHHHH!!!!!!!!!
        for (AbstractOrb o : AbstractDungeon.player.orbs) {
            if (o instanceof StasisOrb) {
                if (((StasisOrb) o).stasisCard.uuid == card.uuid) {
                    AbstractDungeon.player.orbs.remove(o);
                }
            }
        }
        }
    }