//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package downfall.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SkeletonJson;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.HeartAnimListener;
import com.megacrit.cardcrawl.helpers.ImageMaster;

import java.util.ArrayList;

public class CustomAnimatedNPC {
    public TextureAtlas atlas = null;
    public Skeleton skeleton;
    public AnimationState state;
    public AnimationStateData stateData;

    public boolean customFlipX;
    public Float customRot = 0F;
    public Float customShadowScale = 0F;

    public boolean highlighted = false;


    public boolean portalRender;
    public boolean portalRenderActive = false;

    public static int borderEffectCount = 36;

    private boolean colorSwapped = false;
    private boolean noMesh;

    private ArrayList<PortalBorderEffect> borderEffects = new ArrayList<>();

    private float heartCenterX;
    private float heartCenterY;
    private float heartScale;
    public Texture portalImage;

    private HeartAnimListener animListener = new HeartAnimListener();

    // mask variables

    // I'm not sure what all these parameters are for, but the format, depth, and stencil all match the base game.
    // getWidth and getHeight make the frameBuffer match the player's game camera, which makes the FrameBuffer match the screen size.
    // it is possible to use a size other than the screen, but things will become warped if you don't take additional steps
    // like initializing a new camera and setting variables to use it. I prefer this simpler method.
    private FrameBuffer heartBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false, false);
    // I'm actually not sure why I end up needing multiple FrameBuffers for multiple mask operations, but it ended up
    // fixing my issues when I used multiple, so here we are.
    private FrameBuffer maskBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false, false);

    private static final float PORTAL_GROW_TIME = 2.0f; //how long it takes the portal to grow to full size
    private float maskDuration = 0.0f;

    //I like the render options better when I'm using a TextureRegion.
    private static final TextureRegion MASK_REGION = new TextureRegion(new Texture("downfallResources/images/vfx/HeartMask.png"), 500, 500);

    public CustomAnimatedNPC(float x, float y, String atlasUrl, String skeletonUrl, String trackName, boolean portalRender, int portalType) {
        this(x,y,atlasUrl,skeletonUrl,trackName,portalRender,portalType,false, 1F);
    }

    public CustomAnimatedNPC(float x, float y, String atlasUrl, String skeletonUrl, String trackName, boolean portalRender, int portalType, boolean noMesh, float heartScale) {
        this.noMesh = noMesh;
        this.heartScale = heartScale;

        if (!this.noMesh) {
            this.loadAnimation(atlasUrl, skeletonUrl, 1.0F);
            this.skeleton.setPosition(x, y - 300F * Settings.scale * this.heartScale);
          this.state.setAnimation(0, trackName, true); // anim
           this.state.setTimeScale(1.0F); // anim
        }

        this.portalRender = portalRender;

        this.heartCenterX = x;
        this.heartCenterY = y;

        if (portalType == 0){
            portalImage = ImageMaster.loadImage("downfallResources/images/vfx/beyondPortal.png");
        }
        if (portalType == 1){
            portalImage = ImageMaster.loadImage("downfallResources/images/vfx/cityPortal.png");
        }

        if (this.portalRender) {
            if (!this.noMesh) {
               this.addListener(new HeartAnimListener());// anim
                this.skeleton.getRootBone().setScale(0.8F * this.heartScale);// anim
            }
            for (int i = 1; i <= borderEffectCount; i++) {

                PortalBorderEffect effect = new PortalBorderEffect(this.heartCenterX, this.heartCenterY, i * 100 * (borderEffectCount / 360.0f), this.heartScale);
                borderEffects.add(effect);
                effect.orbitalInterval = borderEffects.get(0).orbitalInterval;
                effect.ELLIPSIS_SCALE = this.heartScale;
                effect.calculateEllipseSize();
            }
        }
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

    public void changeBorderColor(Color color){
        for (PortalBorderEffect pb : borderEffects) {
            pb.borderColor = color;
        }
    }

    public void update(){
        if (this.portalRender){
            if (this.portalRenderActive){
                for (PortalBorderEffect pb : borderEffects){
                    pb.update();
                }
                //mask effect
                if (this.maskDuration < PORTAL_GROW_TIME) {
                    this.maskDuration += Gdx.graphics.getDeltaTime();

                    for (PortalBorderEffect pb : borderEffects){
                        pb.ELLIPSIS_SCALE = (maskDuration / PORTAL_GROW_TIME) * this.heartScale;
                        pb.calculateEllipseSize();
                    }

                    if (this.maskDuration > PORTAL_GROW_TIME) {
                        this.maskDuration = PORTAL_GROW_TIME;
                        for (PortalBorderEffect pb : borderEffects){
                            pb.ELLIPSIS_SCALE = (maskDuration / PORTAL_GROW_TIME) * this.heartScale;
                            pb.calculateEllipseSize();
                        }
                    }
                }
            }
        }

    }

    public void render(SpriteBatch sb){
        this.render(sb, Color.WHITE);
    }

    public void render(SpriteBatch sb, Color color) {
        if (this.portalRender){
        /*
        Masking is done by instructing the SpriteBatch, via blend function, to only keep certain pixels based upon a
        "mask" texture rendered after the thing you want masked is rendered. In a vacuum, all you have to do is set the
        blend function as appropriate, then render the mask. However, when other game elements are being rendered, the
        mask will end up discarding pixels in the background, leading to undesired effects like black boxes.
        To avoid this, we render the things that we want the mask applied to inside of a FrameBuffer, then capture the
        contents of the FrameBuffer as a texture, and then finally render that texture in the regular scene.
        */

                // Since masking only works with a large enough texture and we're going to be shrinking the mask to start, we
                // need to first assemble a mask large enough to cover the unwanted parts of the render, using an additional
                // FrameBuffer.

                // A FrameBuffer is slightly expensive, so we don't want to be initializing one every frame. It's a private variable.
                // To properly use a frame buffer, you must start it while the sprite batch is not running, then start the sprite batch again.

                sb.end();
                maskBuffer.begin();

                // since the FrameBuffer is an entirely new rendering environment and we're using it between frames, some values
                // need to be set and re-set each time
                Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
                Gdx.gl.glColorMask(true,true,true,true);

                sb.begin();
                sb.setColor(Color.WHITE); //to be honest I don't know why we need to set the color again, but none of it works without it.

        /*
         Since a texture captured by a FrameBuffer is the size of the FrameBuffer and our FrameBuffer is the size of the screen,
         the transparency pixels will exist at any size we need by default, and we only need to render a portal mask, scaled to the
         effect duration. (I've put duration management stuff in update()). Since we're not actually yet using it as a mask,
         we're effectively just rendering a black circle for now, so we don't need to use the blend functions yet.

         the render function looks complicated, but it's quite simple. Since rendering starts at the bottom left of the texture,
         we want to find the center of the targeted mid-point, and then subtract half of the width and height of the
         texture from the X and Y coordinates to find the render point. Then, since we're applying a transform, we need to
         declare a center point of the texture, which is the center. This is relative to the X and Y coordinates we passed before,
         so we'll just send half width and height again. The next coordinates should be the width and height of the texture,
         then, apply any relevant transforms. In this case, scale for both width and height. The other transform is rotation,
         but we aren't using it in this case.
        */

                //we'll use a simple scale variable based on the duration. At the start, it's super tiny. At the end, it should equal 1.0.
                float scale = (maskDuration / PORTAL_GROW_TIME) * Settings.scale;


                float w = MASK_REGION.getRegionWidth() * this.heartScale;
                float h = MASK_REGION.getRegionHeight() * this.heartScale;
                sb.draw(MASK_REGION, heartCenterX - w / 2, heartCenterY - h / 2, w / 2, h / 2, w, h, scale, scale, 0.0f);

                // now that the mask is rendered inside the frameBuffer, we want to end the frameBuffer, and capture the texture.
                // Before a frame buffer is ended, the sprite batch must end, or weird operations will happen with the GPU.

                sb.end();
                maskBuffer.end();

                Texture tmpTexture = maskBuffer.getColorBufferTexture(); //this captures the contents of the FrameBuffer as a texture
                TextureRegion tmpMask = new TextureRegion(tmpTexture); //again, I prefer the options provided by TextureRegion class

                // Due to a quirk in how StS runs the libGdx engine, and I don't know the details of this myself, FrameBuffers
                // end up rendering upside-down. This fixes it.
                tmpMask.flip(false, true);

                // now we want to start the second frame buffer and render the npc inside of it, followed by the mask.
                // again, the FrameBuffer has to be started before the SpriteBatch.
                heartBuffer.begin();
                Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
                Gdx.gl.glColorMask(true,true,true,true);
                sb.begin();

                if (this.highlighted){
                    sb.setColor(Color.WHITE);
                } else {
                    sb.setColor(Color.LIGHT_GRAY);
                }


                // render the npc. Since we're using a FrameBuffer that's the same size as the screen, there should be no issues
                // using the regular render function of the npc. Note that if you want any background, you'd render it right here
                // right before this render.

                sb.draw(this.portalImage, this.heartCenterX - (250F * Settings.scale), this.heartCenterY - (250F * Settings.scale), 500 * Settings.scale, 500 * Settings.scale);

                //sb.draw(this.portalImage, this.heartCenterX - (this.portalImage.getWidth() / 2F ) * Settings.scale, this.heartCenterY - (this.portalImage.getHeight() / 2F) * Settings.scale, settings);
                this.standardRender(sb);

        /*
        Now we have the heart rendered inside the frame buffer, and we want to remove any unwanted pixels outside the mask.
        to do so, we need to use blend functions.
        this one discards pixels that are covered by the non-transparent pixels in the mask:
        sb.setBlendFunction(GL20.GL_ZERO, GL20.GL_ONE_MINUS_SRC_ALPHA);
        but since we're using the other kind of mask, we want it to discard pixels covered by transparency:
         */
                sb.setBlendFunction(GL20.GL_ZERO, GL20.GL_SRC_ALPHA);

                // then, we render the mask.
                // since the Framebuffer is the size of the screen, the texture is also therefore the size of the screen. That makes
                // this mask really easy to render.
                sb.draw(tmpMask, 0, 0);

                // IMPORTANT! when you set a blend function, always remember to reset it to StS's default after you're done with it:
                sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

                // again, we end the sprite batch and the frameBuffer, capture the texture, and flip it.
                sb.end();
                heartBuffer.end();
                TextureRegion maskedHeart = new TextureRegion(heartBuffer.getColorBufferTexture());
                maskedHeart.flip(false, true);

                // then, finally, we restart the SpriteBatch and render our final texture.
                sb.begin();
                sb.draw(maskedHeart, 0, 0);

                // now you can render whatever you like overtop it.
            }
         else {
        this.standardRender(sb);
        }
    }

    public void dispose() {
        if (this.atlas != null) this.atlas.dispose();
    }

    public void standardRender(SpriteBatch sb, Color color){
        if (!this.noMesh) {
            this.state.update(Gdx.graphics.getDeltaTime());
            this.state.apply(this.skeleton);
            if (this.skeleton.getRootBone() != null) {
                this.skeleton.getRootBone().setRotation(customRot);
                if (this.skeleton.findBone("shadow") != null) {
                    // //SlimeboundMod.logger.info(this.skeleton.findBone("shadow"));
                    this.skeleton.findBone("shadow").setRotation(-1 * customRot);
                    this.skeleton.findBone("shadow").setScale(customShadowScale);
                }
            }
            this.skeleton.updateWorldTransform();
            this.skeleton.setFlip(customFlipX, false);
            this.skeleton.setColor(color);

            sb.end();
            CardCrawlGame.psb.begin();
            AbstractCreature.sr.draw(CardCrawlGame.psb, this.skeleton);
            CardCrawlGame.psb.end();
            sb.begin();
            sb.setBlendFunction(770, 771);
        }
    }

    public void standardRender(SpriteBatch sb){
        this.standardRender(sb, Color.WHITE);
    }

    public void setTimeScale(float setScale) {
        this.state.setTimeScale(setScale);
    }

    public void addListener(HeartAnimListener listener) {
        this.state.addListener(listener);
    }
}
