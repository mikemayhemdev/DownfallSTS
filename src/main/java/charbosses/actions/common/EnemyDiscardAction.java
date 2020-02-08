package charbosses.actions.common;

import charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

public class EnemyDiscardAction extends AbstractGameAction {
    public static final String[] TEXT;
    private static final UIStrings uiStrings;
    private static final float DURATION;
    public static int numDiscarded;

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("DiscardAction");
        TEXT = EnemyDiscardAction.uiStrings.TEXT;
        DURATION = Settings.ACTION_DUR_XFAST;
    }

    private AbstractCharBoss p;
    private boolean endTurn;

    public EnemyDiscardAction(final AbstractCreature target, final AbstractCreature source, final int amount) {
        this(target, source, amount, false);
    }

    public EnemyDiscardAction(final AbstractCreature target, final AbstractCreature source, final int amount, final boolean endTurn) {
        this.p = (AbstractCharBoss) target;
        this.setValues(target, source, amount);
        this.actionType = ActionType.DISCARD;
        this.endTurn = endTurn;
        this.duration = EnemyDiscardAction.DURATION;
    }

    @Override
    public void update() {
        if (this.duration == EnemyDiscardAction.DURATION) {
            if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                this.isDone = true;
                return;
            }
            if (this.p.hand.size() <= this.amount) {
                this.amount = this.p.hand.size();
                for (int tmp = this.p.hand.size(), i = 0; i < tmp; ++i) {
                    final AbstractCard c = this.p.hand.getTopCard();
                    this.p.hand.moveToDiscardPile(c);
                    if (!this.endTurn) {
                        c.triggerOnManualDiscard();
                    }
                }
                this.p.hand.applyPowers();
                this.tickDuration();
                return;
            }
            for (int j = 0; j < this.amount; ++j) {
                final AbstractCard c2 = this.p.hand.getRandomCard(AbstractDungeon.cardRandomRng);
                this.p.hand.moveToDiscardPile(c2);
                c2.triggerOnManualDiscard();
            }
        }
        this.tickDuration();
    }
}
