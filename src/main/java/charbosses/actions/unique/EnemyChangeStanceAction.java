package charbosses.actions.unique;


import charbosses.bosses.AbstractCharBoss;
import charbosses.stances.AbstractEnemyStance;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import java.util.Iterator;

public class EnemyChangeStanceAction extends AbstractGameAction {
    private String id;
    private AbstractEnemyStance newStance;

    public EnemyChangeStanceAction(String stanceId) {
        this.newStance = null;
        this.duration = Settings.ACTION_DUR_FAST;
        this.id = stanceId;
    }

    public EnemyChangeStanceAction(AbstractEnemyStance newStance) {
        this(newStance.ID);
        this.newStance = newStance;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (AbstractCharBoss.boss != null) {
                if (AbstractCharBoss.boss.hasPower("CannotChangeStancePower")) {
                    this.isDone = true;
                    return;

                }

                AbstractEnemyStance oldStance = (AbstractEnemyStance) AbstractCharBoss.boss.stance;
                if (!oldStance.ID.equals(this.id)) {
                    if (this.newStance == null) {
                        this.newStance = AbstractEnemyStance.getStanceFromName(this.id);
                    }

                    Iterator var2 = AbstractCharBoss.boss.powers.iterator();

                    while (var2.hasNext()) {
                        AbstractPower p = (AbstractPower) var2.next();
                        p.onChangeStance(oldStance, this.newStance);
                    }

                    var2 = AbstractCharBoss.boss.relics.iterator();

                    while (var2.hasNext()) {
                        AbstractRelic r = (AbstractRelic) var2.next();
                        r.onChangeStance(oldStance, this.newStance);
                    }

                    oldStance.onExitStance();
                    AbstractCharBoss.boss.stance = this.newStance;
                    this.newStance.onEnterStance();

                /*
                if (AbstractDungeon.actionManager.uniqueStancesThisCombat.containsKey(this.newStance.ID)) {
                    int currentCount = (Integer)AbstractDungeon.actionManager.uniqueStancesThisCombat.get(this.newStance.ID);
                    AbstractDungeon.actionManager.uniqueStancesThisCombat.put(this.newStance.ID, currentCount + 1);
                } else {
                    AbstractDungeon.actionManager.uniqueStancesThisCombat.put(this.newStance.ID, 1);
                }*/

                    AbstractCharBoss.boss.switchedStance();
                /*
                var2 = AbstractCharBoss.boss.discardPile.group.iterator();

                while(var2.hasNext()) {
                    AbstractCard c = (AbstractCard)var2.next();
                    c.triggerExhaustedCardsOnStanceChange(this.newStance);
                }
                */

                    AbstractCharBoss.boss.onStanceChange(this.id);
                }

                AbstractDungeon.onModifyPower();
                if (Settings.FAST_MODE) {
                    this.isDone = true;
                    return;
                }
            }
        }

        this.tickDuration();
    }
}
