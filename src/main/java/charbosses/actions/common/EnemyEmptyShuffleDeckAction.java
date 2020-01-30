package charbosses.actions.common;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.ui.*;

import charbosses.bosses.AbstractCharBoss;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.relics.*;
import java.util.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.*;

public class EnemyEmptyShuffleDeckAction extends AbstractGameAction
{
    private static final TutorialStrings tutorialStrings;
    public static final String[] MSG;
    public static final String[] LABEL;
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
        for (final AbstractRelic r : this.boss.relics) {
            r.onShuffle();
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
        this.isDone = true;
    }
    
    static {
        tutorialStrings = CardCrawlGame.languagePack.getTutorialString("Shuffle Tip");
        MSG = EnemyEmptyShuffleDeckAction.tutorialStrings.TEXT;
        LABEL = EnemyEmptyShuffleDeckAction.tutorialStrings.LABEL;
    }
}
