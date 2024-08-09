package collector.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.city.TorchHead;
import com.megacrit.cardcrawl.vfx.TorchHeadFireEffect;

public class RenderOnlyTorchHead extends TorchHead {
    public RenderOnlyTorchHead() {
        super(0, 0);
        flipHorizontal = true;
    }

    private float fireTimer = 0.0F;

    @Override
    public void renderHealth(SpriteBatch sb) {

    }

    @Override
    public void renderPowerTips(SpriteBatch sb) {

    }

    @Override
    public void update() {
        if (!this.isDying) {
            this.fireTimer -= Gdx.graphics.getDeltaTime();
            if (this.fireTimer < 0.0F) {
                this.fireTimer = 0.04F;
                AbstractDungeon.effectList.add(new TorchHeadFireEffect(this.skeleton.getX() + this.skeleton.findBone("fireslot").getX() , this.skeleton.getY() + this.skeleton.findBone("fireslot").getY() + 110.0F * Settings.scale));
            }
        }
    }

    //TODO: More render hijinx


}
