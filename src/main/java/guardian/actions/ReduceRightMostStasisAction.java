package guardian.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.combat.ImpactSparkEffect;
import com.megacrit.cardcrawl.vfx.combat.LightFlareParticleEffect;
import com.megacrit.cardcrawl.vfx.combat.LightRayFlyOutEffect;
import guardian.GuardianMod;
import guardian.orbs.StasisOrb;
import guardian.powers.AutomayhemPower;

public class ReduceRightMostStasisAction extends AbstractGameAction {

    public ReduceRightMostStasisAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = 0.05F;
    }

    @Deprecated
    public ReduceRightMostStasisAction(boolean unnecessary) {
        this();
    }

    public void update() {
        if (this.duration == this.startDuration && AbstractDungeon.player.orbs.size() > 0) {
            GuardianMod.logger.info("ReduceRightMostStasis firing.");
            AbstractOrb theOrb = null;
            for (AbstractOrb o : AbstractDungeon.player.orbs) {
                if (o instanceof StasisOrb) {
                    o.onStartOfTurn();
                    theOrb = o;
                    break;
                }
            }
            if (theOrb != null) {
                for (int i = 0; i < 20; i++) {
                    AbstractDungeon.effectsQueue.add(new LightFlareParticleEffect(theOrb.tX, theOrb.tY, Color.YELLOW));
                }
            }
        }
        this.tickDuration();
    }
}
