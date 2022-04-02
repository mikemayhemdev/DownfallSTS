package timeeater.suspend;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

import static timeeater.util.Wiz.atb;

public class SuspendHelper {
    public static ArrayList<AbstractCard> suspendGroup = new ArrayList<>();
    public static final int SUSPEND_DRAW_PER_TURN = 3;

    public static void atCombatStart() {
        suspendGroup.clear();
    }

    public static void atStartOfTurnPostDraw() {
        int suspendDrawAmt = SUSPEND_DRAW_PER_TURN;
        suspendDrawAmt = Math.min(suspendDrawAmt, suspendGroup.size());
        for (int i = 0; i < suspendDrawAmt; i++) {
            atb(new AbstractGameAction() {
                @Override
                public void update() {
                    isDone = true;
                    AbstractCard q = suspendGroup.remove(0);
                    AbstractDungeon.player.drawPile.addToTop(q);
                    AbstractDungeon.actionManager.addToTop(new DrawCardAction(1));
                }
            });
        }
    }

    public static void suspend(AbstractCard q) {
        suspendGroup.add(q);
    }

    public static void update() {

    }

    public static void render(SpriteBatch sb) {

    }
}
