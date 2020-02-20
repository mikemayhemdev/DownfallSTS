package evilWithin.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class LoseRelicAction extends AbstractGameAction {
    public String relicID;

    public LoseRelicAction(String relicID) {
        this.relicID = relicID;
    }

    public void update() {

        AbstractDungeon.player.loseRelic(this.relicID);
        this.isDone = true;
    }

}



