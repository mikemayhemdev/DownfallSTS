package theTodo.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class RepeatCardAction extends AbstractGameAction {
    private AbstractCard funCard;

    public RepeatCardAction(AbstractMonster target, AbstractCard card) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.WAIT;
        this.source = AbstractDungeon.player;
        this.target = (AbstractCreature) target;
        this.funCard = card;
    }

    public RepeatCardAction(AbstractCard card) {
        this(null, card);
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            isDone = true;
            if (target == null) {
                target = AbstractDungeon.getRandomMonster();
            }
            GameActionManager.queueExtraCard(funCard, (AbstractMonster)target);
        }

        this.isDone = true;
    }

}