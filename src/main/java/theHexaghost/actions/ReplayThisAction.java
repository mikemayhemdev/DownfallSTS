package theHexaghost.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ReplayThisAction extends AbstractGameAction {
    private AbstractCard funCard;

    public ReplayThisAction(AbstractCreature target, AbstractCard card) {
        this.duration = Settings.ACTION_DUR_FAST;// 17
        this.actionType = ActionType.WAIT;// 18
        this.source = AbstractDungeon.player;// 19
        this.target = target;// 20
        this.funCard = card;
    }// 22

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {// 26
            AbstractMonster m = null;// 50
            if (target != null) {// 52
                m = (AbstractMonster) target;// 53
            }

            AbstractCard tmp = funCard.makeSameInstanceOf();// 56
            AbstractDungeon.player.limbo.addToBottom(tmp);// 57
            tmp.current_x = funCard.current_x;// 58
            tmp.current_y = funCard.current_y;// 59
            tmp.target_x = (float) Settings.WIDTH / 2.0F - 300.0F * Settings.scale;// 60
            tmp.target_y = (float) Settings.HEIGHT / 2.0F;// 61
            tmp.applyPowers();// 68
            if (tmp.cost > 0) {// 63
                tmp.freeToPlayOnce = true;// 64
            }

            if (m != null) {// 67
                tmp.calculateCardDamage(m);// 68
            }

            tmp.purgeOnUse = true;// 71
            AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(tmp, m, funCard.energyOnUse, true));
        }

        this.isDone = true;// 79
    }

}// 81
