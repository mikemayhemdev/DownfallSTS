package downfall.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import downfall.monsters.NeowBoss;
import downfall.monsters.NeowBossFinal;

public class NeowReturnAction extends AbstractGameAction {
    private NeowBoss owner;

    public NeowReturnAction(NeowBoss owner) {
        this.owner = owner;
    }

    @Override
    public void update() {
        NeowBoss.neowboss.escape();
        NeowBoss.neowboss = null;
        /*
        AbstractDungeon.bossList.clear();
        AbstractDungeon.bossList.add(NeowBossFinal.ID);
        AbstractDungeon.bossList.add(NeowBossFinal.ID);
        AbstractDungeon.bossList.add(NeowBossFinal.ID);
        AbstractDungeon.bossKey = NeowBossFinal.ID;
        MapRoomNode node = new MapRoomNode(-1, 10);
        node.room = new MonsterRoomBoss();
        AbstractDungeon.nextRoom = node;
        AbstractDungeon.closeCurrentScreen();
        AbstractDungeon.nextRoomTransitionStart();
        AbstractDungeon.fadeIn();
        //AbstractDungeon.getCurrRoom().cannotLose = false;
        */
        this.isDone = true;
    }
}