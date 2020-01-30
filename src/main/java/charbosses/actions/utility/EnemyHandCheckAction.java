package charbosses.actions.utility;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;

import charbosses.bosses.AbstractCharBoss;

public class EnemyHandCheckAction extends AbstractGameAction
{
    private AbstractCharBoss player;
    
    public EnemyHandCheckAction() {
        this.player = AbstractCharBoss.boss;
    }
    
    @Override
    public void update() {
        this.player.hand.applyPowers();
        this.player.hand.glowCheck();
        this.isDone = true;
    }
}
