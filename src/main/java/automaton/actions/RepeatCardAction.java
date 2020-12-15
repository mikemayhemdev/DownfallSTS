package automaton.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class RepeatCardAction extends AbstractGameAction { // TODO: Make sure this is good
    private AbstractCard funCard;

    public RepeatCardAction(AbstractCreature target, AbstractCard card) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.WAIT;
        this.source = AbstractDungeon.player;
        this.target = target;
        this.funCard = card;
    }

    public RepeatCardAction(AbstractCard card) {
        this(null, card);
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            AbstractMonster m = null;
            if (target != null) {
                m = (AbstractMonster) target;
            }
            else {
                m = AbstractDungeon.getRandomMonster();
            }

            AbstractCard tmp = funCard.makeSameInstanceOf();
            AbstractDungeon.player.limbo.addToBottom(tmp);
            tmp.current_x = funCard.current_x;
            tmp.current_y = funCard.current_y;
            tmp.target_x = (float) Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
            tmp.target_y = (float) Settings.HEIGHT / 2.0F;
            tmp.applyPowers();
            if (tmp.cost > 0) {
                tmp.freeToPlayOnce = true;
            }

            if (m != null) {
                tmp.calculateCardDamage(m);
            }

            tmp.purgeOnUse = true;
            AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(tmp, m, funCard.energyOnUse, true));
        }

        this.isDone = true;
    }

}