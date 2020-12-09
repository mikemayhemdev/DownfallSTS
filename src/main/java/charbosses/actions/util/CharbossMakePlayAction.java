package charbosses.actions.util;

import charbosses.actions.orb.EnemyTriggerEndOfTurnOrbActions;
import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import charbosses.ui.EnemyEnergyPanel;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import slimebound.SlimeboundMod;

public class CharbossMakePlayAction extends AbstractGameAction {

    @Override
    public void update() {
        if (AbstractCharBoss.boss != null) {
            AbstractCharBoss.boss.makePlay();
        }
        this.isDone = true;
    }

}
