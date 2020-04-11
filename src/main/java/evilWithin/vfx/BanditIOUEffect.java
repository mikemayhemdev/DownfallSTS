//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package evilWithin.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.particles.influencers.RegionInfluencer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.SkeletonData;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AnimatedNpc;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import com.megacrit.cardcrawl.vfx.combat.SmokeBombEffect;
import evilWithin.actions.BanditIOUAction;
import evilWithin.relics.RedIOUUpgrade;
import slimebound.SlimeboundMod;

import java.util.ArrayList;

public class BanditIOUEffect extends AbstractGameEffect {

    public BanditIOUAction action = null;

    private float animRomeoTimer = 1f;
    private float animBearTimer = 1f;
    private float animPointyTimer = 1f;

    private float masterTimer = 1.1f;
    private StoryBeats currentBeat = StoryBeats.ROMEOIN;

    private CustomAnimatedNPC romeo;
    private CustomAnimatedNPC bear;
    private CustomAnimatedNPC pointy;

    private BanditAnimType animTypeRomeo = BanditAnimType.ENTER;
    private BanditAnimType animTypeBear = BanditAnimType.ENTER;
    private BanditAnimType animTypePointy = BanditAnimType.ENTER;

    public boolean doneBlasting = true;

    public boolean upgraded = false;

    private String IntroDialog;
    private String OutroDialog;
    private String BearDialog;

    public float pointyStartX;
    public float pointyStartY;

    private float pointyJumpOutX = 500F * Settings.scale;
    private float pointyJumpOutY = 200F * Settings.scale;

    public BanditIOUEffect() {
        this.bear = new CustomAnimatedNPC(-300F * Settings.scale, AbstractDungeon.floorY - 60.0F * Settings.scale, "images/monsters/theCity/bear/skeleton.atlas", "images/monsters/theCity/bear/skeleton.json", "Idle");
        this.romeo = new CustomAnimatedNPC(-300F * Settings.scale, AbstractDungeon.floorY - 60.0F * Settings.scale, "images/monsters/theCity/romeo/skeleton.atlas", "images/monsters/theCity/romeo/skeleton.json", "Idle");
        this.pointy = new CustomAnimatedNPC(-300F * Settings.scale, AbstractDungeon.floorY - 60.0F * Settings.scale, "images/monsters/theCity/pointy/skeleton.atlas", "images/monsters/theCity/pointy/skeleton.json", "Idle");

        this.romeo.setTimeScale(0.8F);

        this.pointy.setTimeScale(0.7F);
        this.bear.setTimeScale(0.8F);

        if (AbstractDungeon.player.hasRelic(RedIOUUpgrade.ID)) upgraded = true;

        RelicStrings dialogStrings = CardCrawlGame.languagePack.getRelicStrings("evil-within:RedIOU");

        if (upgraded){
            IntroDialog = dialogStrings.DESCRIPTIONS[4];
        } else{
            IntroDialog = dialogStrings.DESCRIPTIONS[1];
        }
        BearDialog = dialogStrings.DESCRIPTIONS[2];

        if (upgraded){
            OutroDialog = dialogStrings.DESCRIPTIONS[5];
        } else{
            OutroDialog = dialogStrings.DESCRIPTIONS[2];
        }

        this.romeo.customFlipX = true;
        this.pointy.customFlipX = true;
    }



    public void update() {
        animateRomeo();
        animatePointy();

        masterTimer();

    }

    public void masterTimer(){
        masterTimer -= Gdx.graphics.getDeltaTime();
        if (currentBeat == StoryBeats.ROMEOIN && masterTimer < 0F){
            currentBeat = StoryBeats.ROMEOSPEECH;
            action.currentEffect = new SpeechBubble(this.romeo.skeleton.getX(), this.romeo.skeleton.getY(), 1.5F, IntroDialog, true);
            action.shouldPlayEffect = true;
            masterTimer = .75F;
        }

        if (currentBeat == StoryBeats.ROMEOSPEECH && masterTimer < 0F){
            currentBeat = StoryBeats.POINTYIN;

            action.currentEffect = new SmokeBombEffect(this.pointyStartX,this.pointyStartY);
            action.shouldPlayEffect = true;

            pointy.skeleton.setX(this.pointyStartX);
            masterTimer = 1.2F;
        }

        if (currentBeat == StoryBeats.POINTYIN && masterTimer < 0F){
            currentBeat = StoryBeats.POINTYATTACK;


            pointy.state.setAnimation(0, "Attack", false);
            pointy.state.addAnimation(0, "Idle", true, 0.0F);
            masterTimer = .15F;
        }

        if (currentBeat == StoryBeats.POINTYATTACK && masterTimer < 0F){
            currentBeat = StoryBeats.POINTYHIT;
            action.shouldDamage = true;
            action.currentDamage = 10;
            action.shouldPlayEffect = true;
            action.currentEffect = new FlashAtkImgEffect(action.m.drawX,action.m.drawY, AbstractGameAction.AttackEffect.SLASH_VERTICAL);

            masterTimer = .1F;
        }

        if (currentBeat == StoryBeats.POINTYHIT && masterTimer < 0F){
            currentBeat = StoryBeats.POINTYHIT2;
            action.shouldDamage = true;
            action.currentDamage = 10;
            action.shouldPlayEffect = true;
            action.currentEffect = new FlashAtkImgEffect(action.m.drawX,action.m.drawY, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);

            masterTimer = .85F;
        }
        if (currentBeat == StoryBeats.POINTYHIT2 && masterTimer < 0F){
            currentBeat = StoryBeats.ROMEOSPEECH2;
            action.currentEffect = new SpeechBubble(romeo.skeleton.getX(), romeo.skeleton.getY(), 1.5F, BearDialog, true);
            animTypePointy = BanditAnimType.JUMPOUT;
            animPointyTimer = 0.75F;
            this.pointy.customFlipX = false;
            masterTimer = .9F;
        }

        if (currentBeat == StoryBeats.ROMEOSPEECH2 && masterTimer < 0F){
            currentBeat = StoryBeats.BEARIN;
            masterTimer = .7F;
        }

        if (currentBeat == StoryBeats.BEARIN && masterTimer < 0F){
            currentBeat = StoryBeats.BEARJUMP;
            masterTimer = 1.3F;
        }

        if (currentBeat == StoryBeats.BEARJUMP && masterTimer < 0F){
            currentBeat = StoryBeats.ROMEOSPEECH3;
            action.currentEffect = new SpeechBubble(romeo.skeleton.getX(), romeo.skeleton.getY(), 1.5F, OutroDialog, true);
            masterTimer = .5F;
        }

        if (currentBeat == StoryBeats.ROMEOSPEECH3 && masterTimer < 0F){
            currentBeat = StoryBeats.ROMEOATTACK;
            romeo.state.setAnimation(0, "Attack", false);
            romeo.state.addAnimation(0, "Idle", true, 0.0F);
            masterTimer = .4F;
        }

        if (currentBeat == StoryBeats.ROMEOATTACK && masterTimer < 0F){
            currentBeat = StoryBeats.ROMEOATTACKHIT;
            action.shouldDamage = true;
            action.currentDamage = 10;
            action.shouldPlayEffect = true;
            action.currentEffect = new FlashAtkImgEffect(action.m.drawX,action.m.drawY, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);

            masterTimer = .5F;
        }

        if (currentBeat == StoryBeats.ROMEOATTACKHIT && masterTimer < 0F){
            currentBeat = StoryBeats.POINTOUT;
            this.animTypePointy = BanditAnimType.EXIT;
            this.pointy.customFlipX = false;
            this.animPointyTimer = 1F;
            masterTimer = .1F;
        }

        if (currentBeat == StoryBeats.POINTOUT && masterTimer < 0F){
            currentBeat = StoryBeats.BEAROUT;

            masterTimer = .1F;
        }

        if (currentBeat == StoryBeats.BEAROUT && masterTimer < 0F){
            currentBeat = StoryBeats.ROMEOOUT;
            this.animTypeRomeo = BanditAnimType.EXIT;
            this.romeo.customFlipX = false;
            masterTimer = .1F;
        }

        if (currentBeat == StoryBeats.ROMEOOUT && masterTimer < 0F){
            masterTimer = 1F;
        }

        

    }

    public void animateRomeo(){
        romeo.state.update(Gdx.graphics.getDeltaTime());
        romeo.state.apply(romeo.skeleton);
        if (animRomeoTimer > 0F){
            if (this.animTypeRomeo == BanditAnimType.ENTER){

                float X = Interpolation.linear.apply(800F * Settings.scale, -100F,animRomeoTimer/1F);
                this.romeo.skeleton.setX(X);
                animRomeoTimer -= Gdx.graphics.getDeltaTime();
                if (animRomeoTimer < 0F) {
                    animRomeoTimer = 1F;
                    this.animTypeRomeo = BanditAnimType.IDLE;
                  }
            }
            else if (this.animTypeRomeo == BanditAnimType.EXIT) {
                float X = Interpolation.linear.apply(-100F,800F * Settings.scale,animRomeoTimer/1F);

                this.romeo.skeleton.setX(X);
                animRomeoTimer -= Gdx.graphics.getDeltaTime();
            }
        }
    }

    public void animatePointy(){
        pointy.state.update(Gdx.graphics.getDeltaTime());
        pointy.state.apply(pointy.skeleton);
        if (animPointyTimer > 0F){
            if (this.animTypePointy == BanditAnimType.JUMPOUT){

                float X = Interpolation.linear.apply(this.pointyJumpOutX, this.pointyStartX, animPointyTimer/1F);
                float Y = Interpolation.linear.apply(this.pointyJumpOutY, this.pointyStartY, animPointyTimer/1F);
                this.pointy.skeleton.setX(X);
                this.pointy.skeleton.setY(Y);
                this.animPointyTimer -= Gdx.graphics.getDeltaTime();
                if (animPointyTimer < 0F) {
                    this.animTypePointy = BanditAnimType.IDLE;
                    this.pointy.customFlipX = true;
                }
            }
            else if (this.animTypePointy == BanditAnimType.EXIT) {
                float X = Interpolation.linear.apply(100F, this.pointyJumpOutX,animPointyTimer/1F);

                this.pointy.skeleton.setX(X);
                animPointyTimer -= Gdx.graphics.getDeltaTime();
            }
        }
    }

    public void render(SpriteBatch sb) {

        this.romeo.render(sb);
        this.pointy.render(sb);

    }

    public void dispose() {
        this.romeo.dispose();
        this.bear.dispose();
        this.pointy.dispose();
        this.isDone = true;
    }

    public enum BanditAnimType {
        ENTER,
        EXIT,
        JUMPIN,
        JUMPOUT,
        IDLE;

        BanditAnimType() {
        }
    }

    public enum StoryBeats {
        ROMEOIN,
        ROMEOSPEECH,
        POINTYIN,
        POINTYATTACK,
        POINTYHIT,
        POINTYHIT2,
        ROMEOSPEECH2,
        BEARIN,
        BEARJUMP,
        BEARHIT,
        ROMEOSPEECH3,
        ROMEOATTACK,
        ROMEOATTACKHIT,
        ROMEOOUT,
        BEAROUT,
        POINTOUT;

        StoryBeats() {
        }
    }
}
