//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package evilWithin.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SkeletonJson;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.HeartAnimListener;
import slimebound.SlimeboundMod;

public class CustomAnimatedNPC {
    public TextureAtlas atlas = null;
    public Skeleton skeleton;
    public AnimationState state;
    public AnimationStateData stateData;

    public boolean customFlipX;
    public Float customRot = 0F;
    public Float customShadowScale = 0F;

    public CustomAnimatedNPC(float x, float y, String atlasUrl, String skeletonUrl, String trackName) {
        this.loadAnimation(atlasUrl, skeletonUrl, 1.0F);
        this.skeleton.setPosition(x, y);
        this.state.setAnimation(0, trackName, true);
        this.state.setTimeScale(1.0F);
    }

    public void loadAnimation(String atlasUrl, String skeletonUrl, float scale) {
        this.atlas = new TextureAtlas(Gdx.files.internal(atlasUrl));
        SkeletonJson json = new SkeletonJson(this.atlas);
        json.setScale(Settings.scale / scale);
        SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal(skeletonUrl));
        this.skeleton = new Skeleton(skeletonData);
        this.skeleton.setColor(Color.WHITE);
        this.stateData = new AnimationStateData(skeletonData);
        this.state = new AnimationState(this.stateData);
    }

    public void render(SpriteBatch sb) {
        this.state.update(Gdx.graphics.getDeltaTime());
        this.state.apply(this.skeleton);
        if (this.skeleton.getRootBone() != null) {
            this.skeleton.getRootBone().setRotation(customRot);
            if (this.skeleton.findBone("shadow") != null) {
                SlimeboundMod.logger.info(this.skeleton.findBone("shadow"));
                this.skeleton.findBone("shadow").setRotation(-1 * customRot);
                this.skeleton.findBone("shadow").setScale(customShadowScale);
            }
        }
        this.skeleton.updateWorldTransform();
        this.skeleton.setFlip(customFlipX, false);
        this.skeleton.setColor(Color.WHITE);
        sb.end();
        CardCrawlGame.psb.begin();
        AbstractCreature.sr.draw(CardCrawlGame.psb, this.skeleton);
        CardCrawlGame.psb.end();
        sb.begin();
        sb.setBlendFunction(770, 771);
    }

    public void render(SpriteBatch sb, Color color) {
        this.state.update(Gdx.graphics.getDeltaTime());
        this.state.apply(this.skeleton);
        this.skeleton.updateWorldTransform();
        this.skeleton.setFlip(false, false);
        this.skeleton.setColor(color);
        sb.end();
        CardCrawlGame.psb.begin();
        AbstractCreature.sr.draw(CardCrawlGame.psb, this.skeleton);
        CardCrawlGame.psb.end();
        sb.begin();
        sb.setBlendFunction(770, 771);
    }

    public void dispose() {
        this.atlas.dispose();
    }

    public void setTimeScale(float setScale) {
        this.state.setTimeScale(setScale);
    }

    public void addListener(HeartAnimListener listener) {
        this.state.addListener(listener);
    }
}
