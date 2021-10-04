package collector.UI;

import basemod.abstracts.CustomEnergyOrb;
import basemod.animations.SpineAnimation;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.megacrit.cardcrawl.core.Settings;

public class CollectorEnergyOrb extends CustomEnergyOrb
{
    private static final float ORB_IMG_SCALE = 1.15f * Settings.scale;

    private FrameBuffer fbo;
    private String Atlas = "collectorResources/images/char/mainChar/CollectorEnergyOrb.atlas";
    private String JSON = "collectorResources/images/char/mainChar/CollectorEnergyOrb.json";
    private SpineAnimation Animation = new SpineAnimation(Atlas,JSON,ORB_IMG_SCALE);
    private TextureAtlas TextAtlas = new TextureAtlas();
    public CollectorEnergyOrb()
    {
        super(null, null, null);
        fbo = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false, false);
    }

    @Override
    public void updateOrb(int energyCount)
    {

    }

    @Override
    public void renderOrb(SpriteBatch sb, boolean enabled, float current_x, float current_y) {
    }
}
