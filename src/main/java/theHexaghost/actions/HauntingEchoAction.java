package theHexaghost.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theHexaghost.GhostflameHelper;
import theHexaghost.HexaMod;
import theHexaghost.ghostflames.AbstractGhostflame;

public class HauntingEchoAction extends AbstractGameAction {

    public HauntingEchoAction() { ;
    }

    public void update() {
        if (GhostflameHelper.activeGhostFlame.charged) {
            AbstractDungeon.actionManager.addToBottom(new ExtinguishCurrentFlameAction());
            AbstractDungeon.actionManager.addToBottom(new ChargeCurrentFlameAction());
        }
        this.isDone = true;
    }
}
