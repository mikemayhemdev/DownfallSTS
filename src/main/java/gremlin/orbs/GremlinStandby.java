package gremlin.orbs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.*;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import gremlin.GremlinMod;
import gremlin.powers.GremlinPower;

public abstract class GremlinStandby extends AbstractOrb {
    public String[] descriptions;

    private TextureAtlas atlas;
    public Skeleton skeleton;
    public AnimationState state;
    private AnimationStateData stateData;

    public int hp;
    private int yOffset;

    public String assetFolder;
    public String animationName;

    public GremlinStandby(int hp, String ID, String assetFolder, String animationName, int yOffset){
        this.ID = ID;
        this.scale = 1f;
        this.yOffset = -1*yOffset;

        this.assetFolder = assetFolder;
        this.animationName = animationName;
        String atlasString = GremlinMod.getResourcePath("char/" + assetFolder + "/skeleton.atlas");
        String jsonString = GremlinMod.getResourcePath("char/" + assetFolder + "/skeleton.json");

        this.atlas = new TextureAtlas(Gdx.files.internal(atlasString));
        SkeletonJson json = new SkeletonJson(this.atlas);
        json.setScale(Settings.scale / scale);
        SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal(jsonString));
        this.skeleton = new Skeleton(skeletonData);
        this.skeleton.setColor(Color.WHITE);
        this.stateData = new AnimationStateData(skeletonData);
        this.state = new AnimationState(this.stateData);
        AnimationState.TrackEntry e = this.state.setAnimation(0, animationName, true);
        e.setTime(e.getEndTime() * MathUtils.random());

        this.hp = hp;

        this.name = CardCrawlGame.languagePack.getOrbString(this.ID).NAME;
        this.descriptions = CardCrawlGame.languagePack.getOrbString(this.ID).DESCRIPTION;
        updateDescription();
    }

    @Override
    public void updateAnimation() {
        this.cX = MathHelper.orbLerpSnap(this.cX, this.tX);
        this.cY = MathHelper.orbLerpSnap(this.cY, this.tY);

        if (this.channelAnimTimer != 0.0F) {
            this.channelAnimTimer -= Gdx.graphics.getDeltaTime();
            if (this.channelAnimTimer < 0.0F) {
                this.channelAnimTimer = 0.0F;
            }
        }

        this.c.a = Interpolation.pow2In.apply(1.0F, 0.01F, this.channelAnimTimer / 0.5F);
        this.scale = Interpolation.swingIn.apply(Settings.scale, 0.01F, this.channelAnimTimer / 0.5F);
    }

    @Override
    public void render(SpriteBatch sb) {

        this.state.update(Gdx.graphics.getDeltaTime());
        this.state.apply(this.skeleton);
        this.skeleton.updateWorldTransform();
        this.skeleton.setPosition(this.cX, this.cY + this.yOffset);
        this.skeleton.setFlip(false, false);
        sb.end();
        CardCrawlGame.psb.begin();
        AbstractMonster.sr.draw(CardCrawlGame.psb, this.skeleton);
        CardCrawlGame.psb.end();
        sb.begin();
        sb.setBlendFunction(770, 771);

        renderText(sb);
    }

    protected void renderText(final SpriteBatch sb) {
        FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.hp), this.cX + AbstractOrb.NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0f + AbstractOrb.NUM_Y_OFFSET, this.c, this.fontScale);
    }

    public void update() {
        this.hb.update();
        if (this.hb.hovered) {
            TipHelper.renderGenericTip(this.tX + 96.0f * Settings.scale, this.tY + 64.0f * Settings.scale, this.name, this.description);
        }
        this.fontScale = MathHelper.scaleLerpSnap(this.fontScale, 0.7f);
    }

    @Override
    public void onEvoke() {
        AbstractDungeon.actionManager.addToBottom(new ChannelAction(this));
    }

    public abstract GremlinPower getPower();
}
