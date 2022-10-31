//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

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
    private final boolean fromAutomayhem;

    public ReduceRightMostStasisAction(boolean fromAutomayhem) {
        this.actionType = ActionType.DAMAGE;
        this.attackEffect = AttackEffect.SLASH_HORIZONTAL;
        this.duration = this.startDuration = 0.05F;
        this.fromAutomayhem = false;  //no longer actually needs to accept the input with new Time Sifter/Stasis/accelerate design from Downfall.

    }

    public void update() {
        if (this.duration == this.startDuration && AbstractDungeon.player.orbs.size() > 0) {
            GuardianMod.logger.info("ReduceRightMostStasis firing.");
            AbstractOrb theOrb = null;
            for (AbstractOrb o : AbstractDungeon.player.orbs) {
                if (o instanceof StasisOrb) {
                    if (!this.fromAutomayhem || ((StasisOrb) o).passiveAmount > 1) {
                        o.onStartOfTurn();
                        if (this.fromAutomayhem && AbstractDungeon.player.hasPower(AutomayhemPower.POWER_ID)) AbstractDungeon.player.getPower(AutomayhemPower.POWER_ID).flash();
                        theOrb = o;
                        break;
                    }
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
