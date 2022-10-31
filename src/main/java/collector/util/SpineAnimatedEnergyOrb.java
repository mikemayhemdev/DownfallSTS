package collector.util;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.Skeleton;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbInterface;

public class SpineAnimatedEnergyOrb implements EnergyOrbInterface {
    public AnimationState state;
    public Skeleton skeleton;
    public AnimationStateData stateData;
    public TextureAtlas Atlas;
    public SpineAnimatedEnergyOrb(Skeleton skeletonfile, TextureAtlas atlassFile) {
        super();
        skeleton = skeletonfile;
        Atlas = atlassFile;
    }


    @Override
    public void renderOrb(SpriteBatch spriteBatch, boolean b, float v, float v1) {

    }

    @Override
    public void updateOrb(int i) {

    }
}
