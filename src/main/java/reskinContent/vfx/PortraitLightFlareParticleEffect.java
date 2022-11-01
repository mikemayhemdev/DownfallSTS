package reskinContent.vfx;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.vfx.combat.LightFlareParticleEffect;


public class PortraitLightFlareParticleEffect extends LightFlareParticleEffect {

    public PortraitLightFlareParticleEffect(float x, float y, float scale, Color color) {
        super(x, y, color);
        this.scale *= scale;
    }
}


