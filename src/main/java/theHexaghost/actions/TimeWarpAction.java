package theHexaghost.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import theHexaghost.GhostflameHelper;

public class TimeWarpAction extends AbstractGameAction {

    private DamageInfo infoTime;

    public TimeWarpAction(DamageInfo info) {
        infoTime = info;
    }

    public void update() {
        isDone = true;
        if (GhostflameHelper.activeGhostFlame != GhostflameHelper.hexaGhostFlames.get(0)) {
            addToTop(new TimeWarpAction(infoTime));
            addToTop(new RetractAction());
        }
        addToTop(new DamageRandomEnemyAction(infoTime, AttackEffect.FIRE));
    }

}
