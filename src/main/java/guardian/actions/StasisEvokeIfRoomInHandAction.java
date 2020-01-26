package guardian.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import guardian.orbs.StasisOrb;


public class StasisEvokeIfRoomInHandAction extends AbstractGameAction {
    private StasisOrb orb;

    public StasisEvokeIfRoomInHandAction(StasisOrb orb) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.orb = orb;
        this.actionType = ActionType.DAMAGE;
    }

    public void update() {
        if (AbstractDungeon.player.hand.size() < BaseMod.MAX_HAND_SIZE) {
            AbstractDungeon.player.orbs.remove(this.orb);
            AbstractDungeon.player.orbs.add(0, this.orb);
            AbstractDungeon.player.evokeOrb();
        }

        this.isDone = true;
    }
}
