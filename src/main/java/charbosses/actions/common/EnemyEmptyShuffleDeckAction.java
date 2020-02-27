package charbosses.actions.common;

import charbosses.bosses.AbstractCharBoss;
import charbosses.relics.AbstractCharbossRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.TipTracker;
import com.megacrit.cardcrawl.localization.TutorialStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.ui.FtueTip;

import java.util.Iterator;

public class EnemyEmptyShuffleDeckAction extends AbstractGameAction {
    public static final String[] MSG;
    public static final String[] LABEL;
    private static final TutorialStrings tutorialStrings;

    static {
        tutorialStrings = CardCrawlGame.languagePack.getTutorialString("Shuffle Tip");
        MSG = EnemyEmptyShuffleDeckAction.tutorialStrings.TEXT;
        LABEL = EnemyEmptyShuffleDeckAction.tutorialStrings.LABEL;
    }

    private boolean shuffled;
    private boolean vfxDone;
    private int count;
    private AbstractCharBoss boss;

    public EnemyEmptyShuffleDeckAction(AbstractCharBoss boss) {
        this.shuffled = false;
        this.vfxDone = false;
        this.boss = boss;
        this.setValues(boss, boss, this.count = 0);
        this.actionType = ActionType.SHUFFLE;
        if (!TipTracker.tips.get("SHUFFLE_TIP")) {
            AbstractDungeon.ftue = new FtueTip(EnemyEmptyShuffleDeckAction.LABEL[0], EnemyEmptyShuffleDeckAction.MSG[0], Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f, FtueTip.TipType.SHUFFLE);
            TipTracker.neverShowAgain("SHUFFLE_TIP");
        }

    }

    @Override
    public void update() {
        if (!this.shuffled) {
            this.shuffled = true;
            this.boss.discardPile.shuffle(AbstractDungeon.shuffleRng);
        }
        if (!this.vfxDone) {
            final Iterator<AbstractCard> c = this.boss.discardPile.group.iterator();
            if (c.hasNext()) {
                ++this.count;
                final AbstractCard e = c.next();
                c.remove();
                this.boss.drawPile.addToRandomSpot(e);
                return;
            }
            this.vfxDone = true;
        }
        for (AbstractCharbossRelic r : this.boss.relics){
            r.onShuffle();
        }
        this.vfxDone = true;
        this.isDone = true;
    }
}
