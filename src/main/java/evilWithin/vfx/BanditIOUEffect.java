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
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AnimatedNpc;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import evilWithin.actions.BanditIOUAction;
import slimebound.SlimeboundMod;

import java.util.ArrayList;

public class BanditIOUEffect extends AbstractGameEffect {

    public BanditIOUAction action = null;

    private float animRomeoTimer = 1f;
    private float animBearTimer = 1f;
    private float animPointyTimer = 1f;

    private float masterTimer = 5f;
    private StoryBeats currentBeat = StoryBeats.ROMEOIN;

    private AnimatedNpc romeo;
    private AnimatedNpc bear;
    private AnimatedNpc pointy;

    private BanditAnimType animTypeRomeo = BanditAnimType.ENTER;
    private BanditAnimType animTypeBear = BanditAnimType.ENTER;
    private BanditAnimType animTypePointy = BanditAnimType.ENTER;

    public boolean doneBlasting = true;

    private ArrayList<Vector2> previousPos = new ArrayList();

    public BanditIOUEffect() {
        this.bear = new AnimatedNpc(0F * Settings.scale, AbstractDungeon.floorY - 60.0F * Settings.scale, "images/monsters/theCity/bear/skeleton.atlas", "images/monsters/theCity/bear/skeleton.json", "Idle");
        this.romeo = new AnimatedNpc(0F * Settings.scale, AbstractDungeon.floorY - 60.0F * Settings.scale, "images/monsters/theCity/romeo/skeleton.atlas", "images/monsters/theCity/romeo/skeleton.json", "Idle");
        this.pointy = new AnimatedNpc(0F * Settings.scale, AbstractDungeon.floorY - 60.0F * Settings.scale, "images/monsters/theCity/pointy/skeleton.atlas", "images/monsters/theCity/pointy/skeleton.json", "Idle");

        animRomeoTimer = 1F;
    }

    public void update() {
        animateRomeo();


        masterTimer();

    }

    public void masterTimer(){
        masterTimer -= Gdx.graphics.getDeltaTime();
        if (currentBeat == StoryBeats.ROMEOIN && masterTimer < 4.7F){
            currentBeat = StoryBeats.ROMEOSPEECH;
        }

        if (currentBeat == StoryBeats.ROMEOSPEECH && masterTimer < 4.3F){
            currentBeat = StoryBeats.POINTYIN;
        }

        if (currentBeat == StoryBeats.POINTYIN && masterTimer < 4F){
            currentBeat = StoryBeats.POINTYATTACK;
        }

        if (currentBeat == StoryBeats.POINTYATTACK && masterTimer < 3.7F){
            currentBeat = StoryBeats.ROMEOSPEECH2;
        }

        if (currentBeat == StoryBeats.ROMEOSPEECH2 && masterTimer < 3.4F){
            currentBeat = StoryBeats.BEARIN;
        }

        if (currentBeat == StoryBeats.BEARIN && masterTimer < 3.1F){
            currentBeat = StoryBeats.BEARJUMP;
        }

        if (currentBeat == StoryBeats.BEARJUMP && masterTimer < 2.8F){
            currentBeat = StoryBeats.ROMEOSPEECH3;
        }

        if (currentBeat == StoryBeats.ROMEOSPEECH3 && masterTimer < 2.5F){
            currentBeat = StoryBeats.ROMEOATTACK;
        }

        if (currentBeat == StoryBeats.ROMEOATTACK && masterTimer < 2.4F){
            currentBeat = StoryBeats.POINTOUT;
        }

        if (currentBeat == StoryBeats.POINTOUT && masterTimer < 2.3F){
            currentBeat = StoryBeats.BEAROUT;
        }

        if (currentBeat == StoryBeats.BEAROUT && masterTimer < 2.2F){
            currentBeat = StoryBeats.ROMEOOUT;
        }

        if (currentBeat == StoryBeats.ROMEOOUT && masterTimer < 1.8F){
            masterTimer = -1F;
        }


    }

    public void animateRomeo(){
        if (animRomeoTimer > 0F){
            if (this.animTypeRomeo == BanditAnimType.ENTER){
                if (!this.romeo.skeleton.getFlipX()) this.romeo.skeleton.setFlipX(true);

                float X = Interpolation.linear.apply(Settings.WIDTH * 0.5F, -100F,animRomeoTimer/1F);
                this.romeo.skeleton.setX(X);
                animRomeoTimer -= Gdx.graphics.getDeltaTime();
                if (animRomeoTimer < 0F) {
                    animRomeoTimer = 1F;
                    this.animTypeRomeo = BanditAnimType.IDLE;
                    SlimeboundMod.logger.info(action + " instructions sent");
                    action.shouldDamage = true;
                    action.currentDamage = 10;
                    action.shouldPlayEffect = true;
                    action.currentEffect = new FlashAtkImgEffect(action.m.drawX,action.m.drawY, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
                }
            } else {
                if (this.romeo.skeleton.getFlipX()) this.romeo.skeleton.setFlipX(false);
                float X = Interpolation.linear.apply(-100F,Settings.WIDTH * 0.5F,animRomeoTimer/1F);

                this.romeo.skeleton.setX(X);
                animRomeoTimer -= Gdx.graphics.getDeltaTime();
            }
        }
    }

    public void render(SpriteBatch sb) {

        this.romeo.render(sb);

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
        ROMEOSPEECH2,
        BEARIN,
        BEARJUMP,
        ROMEOSPEECH3,
        ROMEOATTACK,
        ROMEOOUT,
        BEAROUT,
        POINTOUT;

        StoryBeats() {
        }
    }
}
