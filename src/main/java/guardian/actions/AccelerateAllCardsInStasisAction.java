package guardian.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import guardian.GuardianMod;
import guardian.orbs.StasisOrb;

public class AccelerateAllCardsInStasisAction extends AbstractGameAction {

    public AccelerateAllCardsInStasisAction() {
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = 0.05F;
    }

    public void update() {
        if (this.duration == this.startDuration && AbstractDungeon.player.orbs.size() > 0) {
            GuardianMod.logger.info("Accelerating all cards in Stasis.");

            for (AbstractOrb o : AbstractDungeon.player.orbs) {
                if (o instanceof StasisOrb) {
                    o.onStartOfTurn();
                    ((StasisOrb) o).stasisCard.superFlash(Color.GOLDENROD);
                }
            }
        }
        this.tickDuration();
    }
}
