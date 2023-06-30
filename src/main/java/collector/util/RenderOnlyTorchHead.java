package collector.util;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.monsters.city.TorchHead;

public class RenderOnlyTorchHead extends TorchHead {
    public RenderOnlyTorchHead() {
        super(0, 0);
        flipHorizontal = true;
    }

    @Override
    public void renderHealth(SpriteBatch sb) {

    }

    @Override
    public void renderPowerTips(SpriteBatch sb) {

    }


    //TODO: More render hijinx


}
