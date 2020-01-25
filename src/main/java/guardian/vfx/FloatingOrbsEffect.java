package guardian.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import guardian.GuardianMod;

public class FloatingOrbsEffect extends AbstractGameEffect {
    public AbstractCreature p;

    protected TextureAtlas atlas;
    protected Skeleton skeleton;
    public AnimationState state;
    protected AnimationStateData stateData;
    public static SkeletonMeshRenderer sr;

    private float x;
    private float y;

    private float time;

    public FloatingOrbsEffect(AbstractCreature p, float x, float y) {
        this.duration = 0F;
        this.p = p;
        this.renderBehind = false;
        this.x = x;
        this.y = y;

        sr = new SkeletonMeshRenderer();
        sr.setPremultipliedAlpha(true);

        loadAnimation(GuardianMod.getResourcePath("monsters/sphericGuardianOrbs/skeleton.atlas"), GuardianMod.getResourcePath("monsters/sphericGuardianOrbs/skeleton.json"), 1.0F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.state.setTimeScale(0.8F);
    }

    public void update() {
        if (this.duration < 1F) this.duration += Gdx.graphics.getDeltaTime(); else this.duration = 1F;

    }

    public void attackAnim(){

        this.state.setAnimation(0, "Attack", false);
        this.state.setTimeScale(0.8F);
        this.state.addAnimation(0, "Idle", true, 0.0F);
    }

    public void finish(){
        this.isDone = true;

    }
    public void dispose() {
        this.atlas.dispose();
        this.isDone = true;
    }

    public void render(SpriteBatch sb) {

            this.state.update(Gdx.graphics.getDeltaTime());
            this.state.apply(this.skeleton);
            this.skeleton.updateWorldTransform();
            this.skeleton.setPosition(this.x, this.y);
            this.skeleton.setColor(new Color(1,1,1, this.duration));
            this.skeleton.setFlip(true, false);
            sb.end();
            CardCrawlGame.psb.begin();
            sr.draw(CardCrawlGame.psb, this.skeleton);
            CardCrawlGame.psb.end();
            sb.begin();
            sb.setBlendFunction(770, 771);

    }

    protected void loadAnimation(String atlasUrl, String skeletonUrl, float scale) {
        this.atlas = new TextureAtlas(Gdx.files.internal(atlasUrl));
        SkeletonJson json = new SkeletonJson(this.atlas);
        json.setScale(Settings.scale / scale);
        SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal(skeletonUrl));
        this.skeleton = new Skeleton(skeletonData);
        this.skeleton.setColor(Color.WHITE);
        this.stateData = new AnimationStateData(skeletonData);
        this.state = new AnimationState(this.stateData);
    }
}
