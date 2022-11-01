package reskinContent.vfx;

import com.megacrit.cardcrawl.vfx.GhostlyWeakFireEffect;


public class PortraitGhostlyWeakFireEffect extends GhostlyWeakFireEffect {
    public PortraitGhostlyWeakFireEffect(float x, float y, float scale) {
        super(x, y);
        this.scale *= scale;
    }
}


