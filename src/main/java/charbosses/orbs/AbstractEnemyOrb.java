package charbosses.orbs;

import charbosses.bosses.AbstractCharBoss;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.orbs.*;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;

public abstract class AbstractEnemyOrb extends AbstractOrb {

    public static AbstractOrb getRandomOrb(boolean useCardRng) {
        ArrayList<AbstractOrb> orbs = new ArrayList();
        orbs.add(new EnemyDark());
        orbs.add(new EnemyFrost());
        orbs.add(new EnemyLightning());
        orbs.add(new EnemyPlasma());
        return useCardRng ? (AbstractOrb)orbs.get(AbstractDungeon.cardRandomRng.random(orbs.size() - 1)) : (AbstractOrb)orbs.get(MathUtils.random(orbs.size() - 1));
    }

    public void setSlot(int slotNum, int maxOrbs) {
        float dist = 160.0F * Settings.scale + (float)maxOrbs * 10.0F * Settings.scale;
        float angle = 100.0F + (float)maxOrbs * 12.0F;
        float offsetAngle = angle / 2.0F;
        angle *= (float)slotNum / ((float)maxOrbs - 1.0F);
        angle += 90.0F - offsetAngle;
        this.tX = dist * MathUtils.cosDeg(angle) + AbstractCharBoss.boss.drawX;
        this.tY = (-80F * Settings.scale) + dist * MathUtils.sinDeg(angle) + AbstractCharBoss.boss.drawY + AbstractCharBoss.boss.hb_h / 2.0F;
        if (maxOrbs == 1) {
            this.tX = AbstractCharBoss.boss.drawX;
            this.tY = 160.0F * Settings.scale + AbstractCharBoss.boss.drawY + AbstractCharBoss.boss.hb_h / 2.0F;
        }

        this.hb.move(this.tX, this.tY);
    }

    public void updateAnimation() {
        this.bobEffect.update();
        if (AbstractCharBoss.boss != null) {
            this.cX = MathHelper.orbLerpSnap(this.cX, AbstractCharBoss.boss.animX + this.tX);
            this.cY = MathHelper.orbLerpSnap(this.cY, AbstractCharBoss.boss.animY + this.tY);
            if (this.channelAnimTimer != 0.0F) {
                this.channelAnimTimer -= Gdx.graphics.getDeltaTime();
                if (this.channelAnimTimer < 0.0F) {
                    this.channelAnimTimer = 0.0F;
                }
            }

            this.c.a = Interpolation.pow2In.apply(1.0F, 0.01F, this.channelAnimTimer / 0.5F);
            this.scale = Interpolation.swingIn.apply(Settings.scale, 0.01F, this.channelAnimTimer / 0.5F);
        }
    }

    public void applyFocus() {
        AbstractPower power = AbstractCharBoss.boss.getPower("Focus");
        if (power != null && !this.ID.equals("Plasma")) {
            this.passiveAmount = Math.max(0, this.basePassiveAmount + power.amount);
            this.evokeAmount = Math.max(0, this.baseEvokeAmount + power.amount);
        } else {
            this.passiveAmount = this.basePassiveAmount;
            this.evokeAmount = this.baseEvokeAmount;
        }

    }

}
