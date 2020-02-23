package charbosses.actions.unique;

import charbosses.actions.vfx.cardmanip.EnemyShowCardAndAddToDrawPileEffect;
import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.EnemyCardGroup;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

public class EnemyDiscardPileToTopOfDeckAction extends AbstractGameAction {
    public static final String[] TEXT;
    private static final UIStrings uiStrings;

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("DiscardPileToTopOfDeckAction");
        TEXT = EnemyDiscardPileToTopOfDeckAction.uiStrings.TEXT;
    }

    private AbstractCharBoss p;

    public EnemyDiscardPileToTopOfDeckAction(final AbstractCharBoss source) {
        this.p = source;
        this.setValues(null, source, this.amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FASTER;
    }

    @Override
    public void update() {
        if (AbstractDungeon.getCurrRoom().isBattleEnding()) {
            this.isDone = true;
            return;
        }
        if (this.duration == Settings.ACTION_DUR_FASTER) {
            if (this.p.discardPile.isEmpty()) {
                this.isDone = true;
                return;
            }
            if (this.p.discardPile.size() == 1) {
                final AbstractCard tmp = this.p.discardPile.getTopCard();
                this.p.discardPile.removeCard(tmp);
                this.p.discardPile.moveToDeck(tmp, false);
                AbstractBossCard tmpB = (AbstractBossCard)tmp;
                tmpB.forceDraw = true;
                this.isDone = true;
            }
            if (this.p.discardPile.group.size() > this.amount) {
                final AbstractCard tmp = ((EnemyCardGroup) (this.p.discardPile)).getHighestValueCard();
                this.p.discardPile.removeCard(tmp);
                //this.p.discardPile.moveToDeck(tmp, false);
                AbstractDungeon.effectList.add(new EnemyShowCardAndAddToDrawPileEffect(tmp, false, false));
                AbstractBossCard tmpB = (AbstractBossCard)tmp;
                tmpB.forceDraw = true;
                this.isDone = true;
                return;
            }
        }
        this.tickDuration();
    }
}
