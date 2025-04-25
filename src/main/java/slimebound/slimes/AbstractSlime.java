package slimebound.slimes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.megacrit.cardcrawl.helpers.*;
import reskinContent.patches.CharacterSelectScreenPatches;
import reskinContent.reskinContent;
import slimebound.SlimeboundMod;
import slimebound.orbs.*;



public abstract class AbstractSlime {
    public String name;
    public String description;
    public String ID;
    protected ArrayList<PowerTip> tips = new ArrayList<>();
    public int attackAmount = 0;
    protected int baseAttackAmount = 3;
    public float cX = 0.0F;
    public float cY = 0.0F;
    public float tX;
    public float tY;
    protected Color c;
    protected static final int W = 96;
    public Hitbox hb;
    protected Texture img;


    private float delayTime;
    protected static final float NUM_X_OFFSET;
    protected static final float NUM_Y_OFFSET;
    protected float angle;
    protected float scale;
    protected float fontScale;
    protected boolean showEvokeValue;
    protected float channelAnimTimer;

    public String[] descriptions;
    private static Map<String, String> skeletonMap;
    private static Map<String, Color> modelColorMap;

    public float x;
    public float y;
    public Skeleton skeleton;
    public AnimationState state;
    public AbstractPlayer p;
    private Color modelColor;

    public int currentEnergy;

    private AbstractCreature.CreatureAnimation animation;
    private float animationTimer;
    private float animationTimerStart;
    private TextureAtlas atlas;
    private AnimationStateData stateData;

    public boolean alt = false;

    private float yOffset;

    private String animString = "idle";


    static {
        skeletonMap = new HashMap<>();
        modelColorMap = new HashMap<>();

        skeletonMap.put(AttackSlime.ID, reskinContent.assetPath("img/Slimebound/Slaifu/orbs/Slime_spike_S"));
        skeletonMap.put(BronzeSlime.ID, reskinContent.assetPath("img/Slimebound/Slaifu/orbs/Slime_spike_M"));
        skeletonMap.put(CultistSlime.ID, reskinContent.assetPath("img/Slimebound/Slaifu/orbs/Slime_CultistSlime"));
        skeletonMap.put(GreedOozeSlime.ID, reskinContent.assetPath("img/Slimebound/Slaifu/orbs/Slime_acid_S"));
        skeletonMap.put(HexSlime.ID, reskinContent.assetPath("img/Slimebound/Slaifu/orbs/Slime_spike_M"));
        skeletonMap.put(PoisonSlime.ID, reskinContent.assetPath("img/Slimebound/Slaifu/orbs/Slime_acid_S"));
        skeletonMap.put(ScrapOozeSlime.ID, reskinContent.assetPath("img/Slimebound/Slaifu/orbs/Slime_spike_S"));
        skeletonMap.put(ShieldSlime.ID, reskinContent.assetPath("img/Slimebound/Slaifu/orbs/Slime_ShieldSlime"));
        skeletonMap.put(SlimingSlime.ID, reskinContent.assetPath("img/Slimebound/Slaifu/orbs/Slime_spike_S"));
        skeletonMap.put(TorchHeadSlime.ID, reskinContent.assetPath("img/Slimebound/Slaifu/orbs/Slime_spike_M"));

        skeletonMap.put(ChampSlime.ID, reskinContent.assetPath("img/Slimebound/Slaifu/orbs/ChampSlime"));
        skeletonMap.put(DarklingSlime.ID, "images/monsters/theForest/darkling/skeleton");
        skeletonMap.put(DrawingSlime.ID, reskinContent.assetPath("img/Slimebound/Slaifu/orbs/Slime_spike_M"));
        skeletonMap.put(ProtectorSlime.ID, reskinContent.assetPath("img/Slimebound/Slaifu/orbs/Slime_spike_M"));
        skeletonMap.put(SlowingSlime.ID, reskinContent.assetPath("img/Slimebound/Slaifu/orbs/TimeSlime"));


        modelColorMap.put(AttackSlime.ID, new Color(0.8F, 0.25F, 0.25F, 2F));
        modelColorMap.put(BronzeSlime.ID, new Color(1F, 150F / 255F, 0F, 2F));
        modelColorMap.put(CultistSlime.ID, Color.WHITE);//变更
        modelColorMap.put(GreedOozeSlime.ID, new Color(1F, 1F, 30F / 255F, 2F));
        modelColorMap.put(HexSlime.ID, new Color(240F / 255F, 236 / 255F, 150F / 255F, 2F));
        modelColorMap.put(PoisonSlime.ID, new Color(0.6F, .9F, .6F, 2F));
        modelColorMap.put(ScrapOozeSlime.ID, new Color(0.8F, 0.4F, 0.4F, 2F));
        modelColorMap.put(ShieldSlime.ID, Color.WHITE);
        modelColorMap.put(SlimingSlime.ID, new Color(224F / 255F, 113F / 255F, 224F / 255F, 2F));
        modelColorMap.put(TorchHeadSlime.ID, new Color(0.75F, 0.75F, 0.75F, 2F));

        modelColorMap.put(ChampSlime.ID, Color.WHITE);//变更
        modelColorMap.put(DarklingSlime.ID, Color.WHITE);//变更
        modelColorMap.put(DrawingSlime.ID, Color.WHITE);//变更
        modelColorMap.put(ProtectorSlime.ID, Color.WHITE);//变更
        modelColorMap.put(SlowingSlime.ID, Color.WHITE);//变更

    }

    public AbstractSlime(String atlasString, String skeletonString) {
        this.c = Settings.CREAM_COLOR.cpy();
        this.hb = new Hitbox(96.0F * Settings.scale, 96.0F * Settings.scale);
        this.img = null;
        this.fontScale = 0.7F;
        this.showEvokeValue = false;
        this.channelAnimTimer = 0.5F;

        //TODO - Review, reskin unlock stuff might have to die.
        //Decide what models to use - small or medium sized, and original colors or "Classic Boss" colors for Bruiser Slime and Leeching Slime

        if (CharacterSelectScreenPatches.characters[1].reskinCount == 1 && CharacterSelectScreenPatches.characters[1].reskinUnlock) {
            this.modelColor = modelColorMap.get(ID);
            this.atlas = new TextureAtlas(Gdx.files.internal(skeletonMap.get(ID) + ".atlas"));
        } else {
            this.modelColor = modelColor;
            this.atlas = new TextureAtlas(Gdx.files.internal(atlasString));
        }


        SkeletonJson json = new SkeletonJson(this.atlas);

        json.setScale(Settings.scale / .85F * .7F);
        if (alt) {
            this.yOffset = -7F * Settings.scale;
        } else {
            this.yOffset = -27F * Settings.scale;
        }


        SkeletonData skeletonData;
//        if(CharacterSelectScreenPatches.characters[1].isOriginal()){
        if (CharacterSelectScreenPatches.characters[1].reskinCount == 1 && CharacterSelectScreenPatches.characters[1].reskinUnlock) {
            skeletonData = json.readSkeletonData(Gdx.files.internal(skeletonMap.get(ID) + ".json"));

        } else {
            skeletonData = json.readSkeletonData(Gdx.files.internal(skeletonString));
        }

        this.skeleton = new Skeleton(skeletonData);
        this.skeleton.setColor(Color.WHITE);
        this.stateData = new AnimationStateData(skeletonData);
        this.state = new AnimationState(this.stateData);

        AnimationState.TrackEntry e = this.state.setAnimation(0, animString, true);
        e.setTime(e.getEndTime() * MathUtils.random());

        this.state.addListener(new SlimeAnimListener());
        this.delayTime = 0.27F;
        this.yOffset = yOffset * Settings.scale;

        this.ID = ID;


        this.baseAttackAmount = attackAmount;

        this.channelAnimTimer = 0.5F;

        this.descriptions = CardCrawlGame.languagePack.getOrbString(this.ID).DESCRIPTION;

        this.name = CardCrawlGame.languagePack.getOrbString(this.ID).NAME;

        updateDescription();

    }

    public abstract void updateDescription();


    public void addEnchantment(){
        //TODO enchantment visuals
        //Looks like most of them used attachments or VFX, should be easy to transfer over.
        //Maybe use Enum?
        //Maybe steal Greed ooze's shine visuals for the "no energy to attack" enchantment appearance
        //Find another visuals for "commands trigger twice" enchantment

    }


    public void onEndOfTurn() {
    }

    public void increaseStrength(int amount) {
        this.attackAmount += amount;

    }

    public void update() {
        this.hb.update();
        if (this.hb.hovered) {
            TipHelper.renderGenericTip(this.tX + 96.0F * Settings.scale, this.tY + 64.0F * Settings.scale, this.name, this.description);
        }

        this.fontScale = MathHelper.scaleLerpSnap(this.fontScale, 0.7F);
    }

    public void updateAnimation() {
        this.cX = MathHelper.orbLerpSnap(this.cX, AbstractDungeon.player.animX + this.tX);
        this.cY = MathHelper.orbLerpSnap(this.cY, AbstractDungeon.player.animY + this.tY);
        if (this.channelAnimTimer != 0.0F) {
            this.channelAnimTimer -= Gdx.graphics.getDeltaTime();
            if (this.channelAnimTimer < 0.0F) {
                this.channelAnimTimer = 0.0F;
            }
        }

        this.c.a = Interpolation.pow2In.apply(1.0F, 0.01F, this.channelAnimTimer / 0.5F);
        this.scale = Interpolation.swingIn.apply(Settings.scale, 0.01F, this.channelAnimTimer / 0.5F);
    }

    protected void renderText(SpriteBatch sb) {
        FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.attackAmount), this.cX + NUM_X_OFFSET, this.cY + NUM_Y_OFFSET, new Color(0.2F, 1.0F, 1.0F, this.c.a), this.fontScale);
//TODO render stuff

    }


    static {
        NUM_X_OFFSET = 20.0F * Settings.scale;
        NUM_Y_OFFSET = -12.0F * Settings.scale;
    }
}
