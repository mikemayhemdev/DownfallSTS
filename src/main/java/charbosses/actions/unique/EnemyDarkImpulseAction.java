package charbosses.actions.unique;


import charbosses.bosses.AbstractCharBoss;
import charbosses.orbs.EnemyDark;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Dark;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import java.util.Iterator;

public class EnemyDarkImpulseAction extends AbstractGameAction {
    public EnemyDarkImpulseAction() {
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST && !AbstractCharBoss.boss.orbs.isEmpty()) {
            Iterator var1 = AbstractCharBoss.boss.orbs.iterator();

            while(var1.hasNext()) {
                AbstractOrb o = (AbstractOrb)var1.next();
                if (o instanceof EnemyDark) {
                    o.onStartOfTurn();
                    o.onEndOfTurn();
                }
            }

            if (AbstractCharBoss.boss.hasRelic("Cables") && !(AbstractCharBoss.boss.orbs.get(0) instanceof EmptyOrbSlot) && AbstractCharBoss.boss.orbs.get(0) instanceof EnemyDark) {
                ((AbstractOrb)AbstractCharBoss.boss.orbs.get(0)).onStartOfTurn();
                ((AbstractOrb)AbstractCharBoss.boss.orbs.get(0)).onEndOfTurn();
            }
        }

        this.tickDuration();
    }
}
