package slimebound.slimes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import basemod.animations.AbstractAnimation;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import reskinContent.patches.CharacterSelectScreenPatches;
import reskinContent.reskinContent;
import slimebound.orbs.AttackSlime;
import slimebound.orbs.DarklingSlime;
import slimebound.orbs.PoisonSlime;
import slimebound.vfx.SlimeFlareEffect;


public abstract class AbstractSlime {

    public float animX;
    public float animY;
    public String[] descriptions;
    public float scale = 1.15F;
    public float x;
    public float y;
    public Skeleton skeleton;
    public AnimationState state;
    public AbstractPlayer p;
    private TextureAtlas atlas;
    private AnimationStateData stateData;
    public String name;
    public String description;
    public String ID;
    public float tX;
    public float tY;


    private AbstractCreature.CreatureAnimation animation;
    private float animationTimer;
    protected float fontScale;
    private float animationTimerStart;
    private String animString = "idle";
    private float yOffset;
    //TODO - Pretty much everything involved here. This is all just prelim copy paste.
    //TODO - Should have a hitbox which explains the next turn attack like an orb, and also include active enchantments if any are present.
    //TODO - Should have renders for both energy (maybe a smaller version of the Slime Boss energy render), and an orb-like text. Maybe more flavorful to use intent imagery?

    protected int baseAttackAmount = 3;
    public int attackAmount = baseAttackAmount;
    public Hitbox hb;

    public int energy;

    private Set<SlimeEnchantmentType> activeEnchants = new HashSet<>();

    // Decide later if slimes are re-initialized every combat or just the same but using a reset function

    public AbstractSlime(String atlasString, String skeletonString) {

        this.scale = scale * .85F;

        this.atlas = new TextureAtlas(Gdx.files.internal(atlasString));


        //this.renderBehind=true;
        SkeletonJson json = new SkeletonJson(this.atlas);

        json.setScale(Settings.scale * .45F);
        this.yOffset = -27F * Settings.scale;


        SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal(skeletonString));;
//
//            }

        this.skeleton = new Skeleton(skeletonData);
        this.skeleton.setColor(Color.WHITE);
        this.stateData = new AnimationStateData(skeletonData);
        this.state = new AnimationState(this.stateData);

        AnimationState.TrackEntry e = this.state.setAnimation(0, animString, true);
        e.setTime(e.getEndTime() * MathUtils.random());

        this.state.addListener(new SlimeAnimListener());
        this.yOffset = yOffset * Settings.scale;

    }

    // Core functions

    public void render(SpriteBatch sb) {
        // TODO: Render slime itself.

        // logger.info("rendering slime model");
        this.state.update(Gdx.graphics.getDeltaTime());
        this.state.apply(this.skeleton);
        this.skeleton.updateWorldTransform();
        this.x = this.animX;
        this.y = this.animY + this.yOffset;
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

        // TODO: Render amount of energy. (I'd rather not use an actual energy orb...)

        // TODO: Render Strength and enchantments.

        // TODO: Render intent.
    }

    public void update() {
        updateAnimation();
        this.hb.update();

        if (this.hb.hovered) {
            TipHelper.renderGenericTip(this.tX + 96.0F * Settings.scale, this.tY + 64.0F * Settings.scale, this.name, this.description);
        }

        this.fontScale = MathHelper.scaleLerpSnap(this.fontScale, 0.7F);
    }

    public abstract void updateDescription();

    // Gameplay functions

    public void onEndOfTurn() {
        if (energy > 0) {
            activateEffect();
            if (!isEnchanted(SlimeEnchantmentType.NOENEERGYCOST))
                atb(new AbstractGameAction() {
                    @Override
                    public void update() {
                        isDone = true;
                        AbstractSlime.this.SpendEnergy(1);
                    }
                });
        } else {
            atb(new AbstractGameAction() {
                @Override
                public void update() {
                    isDone = true;
                    AbstractSlime.this.GainEnergy(1);
                }
            });
        }
    }

    public void GainEnergy(int amount) {
        energy += amount;
    }

    public void SpendEnergy(int amount) {
        energy -= amount;
    }

    public abstract void activateEffect();

    public boolean isEnchanted(SlimeEnchantmentType enchantToCheck){
        return activeEnchants.contains(enchantToCheck);
    }

    public enum SlimeEnchantmentType {
        NOENEERGYCOST,
        DOUBLECOMMAND,
        CULTIST,
        CHAMP,
        TORCHHEAD,
        TIMEEATER,
        GUARDOIAN,
        HEXAGHOST;
    }

    protected void atb(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

    protected void att(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToTop(action);
    }

    public void updateAnimation() {

        if (this.animationTimer != 0.0F) {
            switch (this.animation) {
                case ATTACK_FAST:
                    this.updateFastAttackAnimation();
                    break;
            }
        }

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

}
