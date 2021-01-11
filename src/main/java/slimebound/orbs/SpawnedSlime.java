package slimebound.orbs;

import basemod.animations.AbstractAnimation;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.*;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.SlimeAnimListener;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.beyond.Darkling;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import slimebound.SlimeboundMod;
import slimebound.cards.SplitGreed;
import slimebound.cards.SplitScrap;
import slimebound.powers.*;
import slimebound.vfx.*;
import reskinContent.reskinContent;

import java.util.HashMap;
import java.util.Map;


public abstract class SpawnedSlime
        extends AbstractOrb {

    public static final Logger logger = LogManager.getLogger(SlimeboundMod.class.getName());
    private static final float ORB_WAVY_DIST = 0.04F;
    private static final float PI_4 = 12.566371F;
    public static String orbID = "";
    public static SkeletonMeshRenderer sr;
    private static int W;
    public float NUM_X_OFFSET = 1.0F * Settings.scale;
    public float NUM_Y_OFFSET = -35.0F * Settings.scale;
    public AbstractCard lockedCard;
    public boolean upgraded = false;
    public boolean showPassive = true;
    public boolean activatedThisTurn = false;
    public int UniqueFocus;
    public boolean noEvokeSound = false;
    public float animX;
    public float animY;
    public int slimeBonus;
    public boolean movesToAttack;
    public int upgradedInitialBoost;
    public String originalRelic = "";
    public String[] descriptions;
    public com.badlogic.gdx.graphics.Texture intentImage;
    public boolean noEvokeBonus;
    public float scale = 1.15F;
    public float x;
    public float y;
    public Skeleton skeleton;
    public AnimationState state;
    public AbstractPlayer p;
    public boolean noRender;
    public String customDescription;
    public int debuffBonusAmount;
    public int debuffAmount;
    public Color extraFontColor = null;
    public boolean topSpawnVFX = false;
    public boolean beingAbsorbed = false;
    protected boolean showChannelValue = true;
    private float vfxTimer = 1.0F;
    private float vfxIntervalMin = 0.2F;
    private float vfxIntervalMax = 0.7F;
    private SlimeFlareEffect.OrbFlareColor OrbVFXColor;
    private Color deathColor;
    private Color modelColor;
    private Texture img;
    private AbstractCreature.CreatureAnimation animation;
    private float animationTimer;
    private float animationTimerStart;
    private TextureAtlas atlas;
    private AnimationStateData stateData;
    private AbstractAnimation animationA;
    private Color projectileColor;
    private float delayTime;
    private boolean hasSplashed;
    private boolean deathVFXplayed;
    private String animString = "idle";
    private float yOffset;

    private static Map<String, String> skeletonMap;
    private static Map<String, Color> modelColorMap;



    static
    {
        skeletonMap = new HashMap<>();
        modelColorMap = new HashMap<>();

        skeletonMap.put(AttackSlime.ID,                  reskinContent.assetPath("img/Slimebound/orbs/Slime_spike_S"));
        skeletonMap.put(BronzeSlime.ID,                  reskinContent.assetPath("img/Slimebound/orbs/Slime_spike_M"));
        skeletonMap.put(CultistSlime.ID,                 reskinContent.assetPath("img/Slimebound/orbs/Slime_CultistSlime"));
        skeletonMap.put(GreedOozeSlime.ID,               reskinContent.assetPath("img/Slimebound/orbs/Slime_acid_S"));
        skeletonMap.put(HexSlime.ID,                     reskinContent.assetPath("img/Slimebound/orbs/Slime_spike_M"));
        skeletonMap.put(PoisonSlime.ID,                  reskinContent.assetPath("img/Slimebound/orbs/Slime_acid_S"));
        skeletonMap.put(ScrapOozeSlime.ID,               reskinContent.assetPath("img/Slimebound/orbs/Slime_spike_S"));
        skeletonMap.put(ShieldSlime.ID,                  reskinContent.assetPath("img/Slimebound/orbs/Slime_ShieldSlime"));
        skeletonMap.put(SlimingSlime.ID,                 reskinContent.assetPath("img/Slimebound/orbs/Slime_spike_S"));
        skeletonMap.put(TorchHeadSlime.ID,               reskinContent.assetPath("img/Slimebound/orbs/Slime_spike_M"));

        skeletonMap.put(ChampSlime.ID,                   reskinContent.assetPath("img/Slimebound/orbs/ChampSlime"));
        skeletonMap.put(DarklingSlime.ID,                "images/monsters/theForest/darkling/skeleton");
        skeletonMap.put(DrawingSlime.ID,                 reskinContent.assetPath("img/Slimebound/orbs/Slime_spike_M"));
        skeletonMap.put(ProtectorSlime.ID,               reskinContent.assetPath("img/Slimebound/orbs/Slime_spike_M"));
        skeletonMap.put(SlowingSlime.ID,                 reskinContent.assetPath("img/Slimebound/orbs/TimeSlime"));


        modelColorMap.put(AttackSlime.ID,             new Color(0.8F,0.25F,0.25F,2F));
        modelColorMap.put(BronzeSlime.ID,             new Color(1F,150F/255F,0F,2F));
        modelColorMap.put(CultistSlime.ID,            Color.WHITE);//变更
        modelColorMap.put(GreedOozeSlime.ID,          new Color(1F,1F,30F/255F,2F));
        modelColorMap.put(HexSlime.ID,                new Color(240F/255F,236/255F,150F/255F,2F));
        modelColorMap.put(PoisonSlime.ID,             new Color(0.6F,.9F,.6F,2F));
        modelColorMap.put(ScrapOozeSlime.ID,          new Color(0.8F,0.4F,0.4F,2F));
        modelColorMap.put(ShieldSlime.ID,             Color.WHITE);
        modelColorMap.put(SlimingSlime.ID,            new Color(224F/255F,113F/255F,224F/255F,2F));
        modelColorMap.put(TorchHeadSlime.ID,          new Color(0.75F,0.75F,0.75F,2F));

        modelColorMap.put(ChampSlime.ID,          Color.WHITE);//变更
        modelColorMap.put(DarklingSlime.ID,          Color.WHITE);//变更
        modelColorMap.put(DrawingSlime.ID,          Color.WHITE);//变更
        modelColorMap.put(ProtectorSlime.ID,          Color.WHITE);//变更
        modelColorMap.put(SlowingSlime.ID,          Color.WHITE);//变更

    }

    public SpawnedSlime(String ID, Color projectileColor, String atlasString, String skeletonString, boolean medScale, boolean alt, int passive, int initialBoost, boolean movesToAttack, Color deathColor, SlimeFlareEffect.OrbFlareColor OrbFlareColor, Texture intentImage) {

        this.scale = scale * .85F;
        if(!reskinContent.slimeReskinAnimation){
            this.modelColor = modelColor;

            this.atlas = new TextureAtlas(Gdx.files.internal(atlasString));
        }else {
            this.modelColor = modelColorMap.get(ID);

            this.atlas = new TextureAtlas(Gdx.files.internal(skeletonMap.get(ID)+".atlas"));
        }


        //this.renderBehind=true;
        SkeletonJson json = new SkeletonJson(this.atlas);

        if (this instanceof DarklingSlime){
            json.setScale(Settings.scale * .45F);

        } else {
            if (medScale) {
                json.setScale(Settings.scale / .85F * .7F);
                if (alt) {
                    this.yOffset = -7F * Settings.scale;
                } else {
                    this.yOffset = -27F * Settings.scale;
                }
            } else {
                json.setScale(Settings.scale / .5F * .7F);
                if (alt) {
                    this.yOffset = -17F * Settings.scale;
                } else {
                    this.yOffset = -32F * Settings.scale;
                }
            }
        }



        SkeletonData skeletonData ;
        if(!reskinContent.slimeReskinAnimation){
            skeletonData = json.readSkeletonData(Gdx.files.internal(skeletonString));
        }else {
            skeletonData = json.readSkeletonData(Gdx.files.internal(skeletonMap.get(ID)+".json"));
        }

        this.skeleton = new Skeleton(skeletonData);
        this.skeleton.setColor(Color.WHITE);
        this.stateData = new AnimationStateData(skeletonData);
        this.state = new AnimationState(this.stateData);
        if (this instanceof DarklingSlime){
            AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
            e.setTime(e.getEndTime() * MathUtils.random());
        } else {
            AnimationState.TrackEntry e = this.state.setAnimation(0, animString, true);
            e.setTime(e.getEndTime() * MathUtils.random());
        }
        this.state.addListener(new SlimeAnimListener());
        this.delayTime = 0.27F;
        this.yOffset = yOffset * Settings.scale;

        this.ID = ID;


        this.basePassiveAmount = passive;
        this.movesToAttack = movesToAttack;

        this.deathColor = projectileColor;

        this.evokeAmount = 1;

        this.passiveAmount = this.basePassiveAmount;
        this.OrbVFXColor = OrbFlareColor;
        this.intentImage = intentImage;
        this.upgradedInitialBoost = 0;


        this.channelAnimTimer = 0.5F;

        this.projectileColor = projectileColor;
        this.descriptions = CardCrawlGame.languagePack.getOrbString(this.ID).DESCRIPTION;

        this.name = CardCrawlGame.languagePack.getOrbString(this.ID).NAME;
        SlimeboundMod.mostRecentSlime = this;


        //AbstractDungeon.actionManager.addToBottom(new VFXAction(new SlimeFlareEffect(this, OrbVFXColor), .1F));
        this.applyFocus();

        updateDescription();


    }

    public void spawnVFX() {
        if (AbstractDungeon.player.maxOrbs > 0) {

            if (this.topSpawnVFX) {
                AbstractDungeon.actionManager.addToTop(new VFXAction(new SlimeSpawnProjectile(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, this, 1.4F, projectileColor)));

            } else {
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new SlimeSpawnProjectile(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, this, 1.4F, projectileColor)));
            }
        }
    }


    public void onEndOfTurn() {

        this.activatedThisTurn = false;

    }


    public void onStartOfTurn() {


        activateEffect();

    }



    /*
    public void update() {

    }
    */

    public void applyFocus() {
        super.applyFocus();
        AbstractPower power = AbstractDungeon.player.getPower(PotencyPower.POWER_ID);
        int bonus = 0;
        if (this instanceof TorchHeadSlime && AbstractDungeon.player.hasPower(StrengthPower.POWER_ID))
            bonus = AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount;


        if (power != null) {

            this.passiveAmount = this.basePassiveAmount + power.amount + this.UniqueFocus + bonus;

        } else {
            this.passiveAmount = this.basePassiveAmount + this.UniqueFocus + bonus;

        }
        updateDescription();
    }

    public void applyUniqueFocus(int StrAmount) {

        this.UniqueFocus = this.UniqueFocus + StrAmount;
        this.passiveAmount = this.passiveAmount + StrAmount;
        updateDescription();
        //AbstractDungeon.effectsQueue.add(new FireBurstParticleEffect(this.cX, this.cY));
    }



    public void onEvoke() {
        if (!noEvokeBonus) {
            if (this instanceof ScrapOozeSlime) {
              //  AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ScrapRespawnPower(AbstractDungeon.player, AbstractDungeon.player, 1), 1));
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new SplitScrap()));
            } else if (this instanceof GreedOozeSlime) {
                //AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new GreedRespawnPower(AbstractDungeon.player, AbstractDungeon.player, 1), 1));
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new SplitGreed()));

            }
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, 1), 1));

        triggerEvokeAnimation();
    }


    public void triggerEvokeAnimation() {

        if (!this.deathVFXplayed) {
            if (!this.noEvokeSound) {
                CardCrawlGame.sound.playA("SLIME_SPLIT", 0.2f);
                //SlimeboundMod.logger.info("playing death sound");
            }

            AbstractDungeon.effectsQueue.add(new SlimeSpawnProjectileDeath(this.hb.cX, this.hb.cY, null, AbstractDungeon.player, 1.4F, this.projectileColor, !this.noEvokeSound));

            AbstractDungeon.effectsQueue.add(new SlimeDeathParticleEffect(this.cX, this.cY, this.deathColor));

            this.deathVFXplayed = true;
        }

    }


    public void activateEffect() {


        if (SlimeboundMod.slimeDelay == true) {
            AbstractDungeon.actionManager.addToTop(new WaitAction(1.4F));
            SlimeboundMod.slimeDelay = false;
        }

        if (!beingAbsorbed) {
            activateEffectUnique();
        }

    }

    public void activateEffectUnique() {
    }


    public void playChannelSFX() {

        CardCrawlGame.sound.play("MONSTER_SLIME_ATTACK", 0.1F);

    }

    public void useFastAttackAnimation() {
        this.animationTimer = 0.4F;
        this.animationTimerStart = this.animationTimer;
        this.animation = AbstractCreature.CreatureAnimation.ATTACK_FAST;
    }

    @Override
    public void updateAnimation() {

        if (this.animationTimer != 0.0F) {
            switch (this.animation) {
                case ATTACK_FAST:
                    this.updateFastAttackAnimation();
                    break;
            }
        }

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


    public void postSpawnEffects() {
    }

    protected void updateFastAttackAnimation() {
        this.animationTimer -= Gdx.graphics.getDeltaTime();
        float targetPos = 50.0F * Settings.scale;


        if (this.animationTimer > (this.animationTimerStart / 2)) {
            this.animX = Interpolation.exp5Out.apply(0.0F, targetPos, ((this.animationTimerStart / 2) - (this.animationTimer - (this.animationTimerStart / 2))) / (this.animationTimerStart / 2));
            //logger.info("pow2Out " + ((this.animationTimerStart / 2) - (this.animationTimer - (this.animationTimerStart / 2))) / (this.animationTimerStart / 2));

        } else if (this.animationTimer < 0.0F) {
            this.animationTimer = 0.0F;
            this.animX = 0.0F;
        } else {
            //logger.info("fade " + this.animationTimer /(this.animationTimerStart / 2));
            this.animX = Interpolation.fade.apply(0.0F, targetPos, (this.animationTimer / (this.animationTimerStart / 2)));
        }

    }

    public void render(SpriteBatch sb) {

        if (!noRender) {
            if (this.delayTime > 0F) {
                delayTime = delayTime - Gdx.graphics.getDeltaTime();
            } else if (this.atlas == null) {
                // logger.info("rendering null");
                sb.setColor(new Color(1F, 1F, 1F, 2F));

                sb.draw(this.img, this.cX - (float) this.img.getWidth() + this.animX * Settings.scale / 2.0F, this.cY + this.animY, (float) this.img.getWidth() * Settings.scale, (float) this.img.getHeight() * Settings.scale, 0, 0, this.img.getWidth(), this.img.getHeight(), false, false);
            } else {
                if (!hasSplashed) {
                    AbstractDungeon.effectsQueue.add(new FakeFlashAtkImgEffect(this.cX, this.cY, projectileColor, 0.75F, true, 0.3F));
                    this.hasSplashed = true;
                    postSpawnEffects();
                } else {

                    // logger.info("rendering slime model");
                    this.state.update(Gdx.graphics.getDeltaTime());
                    this.state.apply(this.skeleton);
                    this.skeleton.updateWorldTransform();
                    this.x = this.cX + this.animX;
                    this.y = this.cY + this.animY + this.yOffset;
                    this.skeleton.setPosition(this.x, this.y);
                    //logger.info("x = " + this.cX + " y = " + (this.cY + AbstractDungeon.sceneOffsetY));

                    //this.skeleton.setColor(modelColor);
                    this.skeleton.setFlip(true, false);
                    sb.end();
                    CardCrawlGame.psb.begin();
                    AbstractMonster.sr.draw(CardCrawlGame.psb, this.skeleton);
                    CardCrawlGame.psb.end();
                    sb.begin();
                    sb.setBlendFunction(770, 771);

                }
                renderText(sb);

            }
            //this.hb.render(sb);
        }
    }


    public void renderText(SpriteBatch sb) {
        if (this.extraFontColor != null) {


            float fontOffset = 26 * Settings.scale;
            if (this.passiveAmount > 9) fontOffset = fontOffset + (6 * Settings.scale);
            FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, this.passiveAmount + "/", this.cX + this.NUM_X_OFFSET, this.cY + this.NUM_Y_OFFSET, this.c, this.fontScale);


            FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.debuffAmount + this.slimeBonus), this.cX + this.NUM_X_OFFSET + fontOffset, this.cY + this.NUM_Y_OFFSET + 1F, this.extraFontColor, this.fontScale);

        } else if (this instanceof PoisonSlime) {

            FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, this.passiveAmount + " All", this.cX + this.NUM_X_OFFSET - (Settings.scale * 0.01F), this.cY + this.NUM_Y_OFFSET, this.c, this.fontScale);
        } else {
            FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.passiveAmount), this.cX + this.NUM_X_OFFSET, this.cY + this.NUM_Y_OFFSET, this.c, this.fontScale);

        }
    }

}




