package downfall.vfx;

import charbosses.bosses.AbstractCharBoss;
import charbosses.relics.AbstractCharbossRelic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.ui.DialogWord;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import com.megacrit.cardcrawl.vfx.SpeechTextEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import com.megacrit.cardcrawl.vfx.combat.SmokeBombEffect;
import downfall.actions.BanditIOUAction;
import downfall.relics.RedIOUUpgrade;
import slimebound.SlimeboundMod;

public class BanditIOUEffect extends AbstractGameEffect {

    public BanditIOUAction action = null;

    private float animRomeoTimer = 1f;
    private float animBearTimer = 0f;
    private float animPointyTimer = 0f;

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

    private float speechOffsetX = 50F * Settings.scale;
    private float speechOffsetY = 180F * Settings.scale;

    private float pointyJumpOutX = 500F * Settings.scale;
    private float pointyJumpOutY = 200F * Settings.scale;

    private float bearJumpOutX = 800F * Settings.scale;
    private float bearJumpOutY = 400F * Settings.scale;

    public float bearStartX;
    private float bearStartY = 1080F * Settings.scale;

    public float bearLandX;
    public float bearLandY;

    private int pointyDamage = 15;
    private int pointyDamageUpgraded = 25;

    private int bearDamage = 25;
    private int bearDamageUpgraded = 35;

    private int bearDexDown = -2;
    private int bearDexDownUpgraded = -3;

    private int romeoDamage = 25;
    private int romeoDamageUpgraded = 35;

    private int romeoWeak = 2;
    private int romeoWeakUpgraded = 3;

    public BanditIOUEffect() {
        this.bear = new CustomAnimatedNPC(-300F * Settings.scale, AbstractDungeon.floorY - 60.0F * Settings.scale, "images/monsters/theCity/bear/skeleton.atlas", "images/monsters/theCity/bear/skeleton.json", "Idle", false, 0);
        this.romeo = new CustomAnimatedNPC(-300F * Settings.scale, AbstractDungeon.floorY - 60.0F * Settings.scale, "images/monsters/theCity/romeo/skeleton.atlas", "images/monsters/theCity/romeo/skeleton.json", "Idle", false, 0);
        this.pointy = new CustomAnimatedNPC(-300F * Settings.scale, AbstractDungeon.floorY - 60.0F * Settings.scale, "images/monsters/theCity/pointy/skeleton.atlas", "images/monsters/theCity/pointy/skeleton.json", "Idle", false, 0);

        this.romeo.setTimeScale(0.6F);

        this.pointy.setTimeScale(0.6F);
        this.bear.setTimeScale(0.5F);

        if (AbstractDungeon.player.hasRelic(RedIOUUpgrade.ID)) upgraded = true;

        RelicStrings dialogStrings = CardCrawlGame.languagePack.getRelicStrings("downfall:RedIOU");

        if (upgraded) {
            IntroDialog = dialogStrings.DESCRIPTIONS[4];
            OutroDialog = dialogStrings.DESCRIPTIONS[5];
            pointyDamage = pointyDamageUpgraded;
            bearDamage = bearDamageUpgraded;
            romeoDamage = romeoDamageUpgraded;
            bearDexDown = bearDexDownUpgraded;
            romeoWeak = romeoWeakUpgraded;
        } else {
            IntroDialog = dialogStrings.DESCRIPTIONS[1];
            OutroDialog = dialogStrings.DESCRIPTIONS[3];
        }
        BearDialog = dialogStrings.DESCRIPTIONS[2];


        this.romeo.customFlipX = true;
        this.pointy.customFlipX = true;

        this.romeo.skeleton.setX(0F);
        this.romeo.skeleton.setY(this.pointyStartY);

        //SlimeboundMod.logger.info(IntroDialog);
       // SlimeboundMod.logger.info(BearDialog);
       // SlimeboundMod.logger.info(OutroDialog);
    }


    public void update() {
        animateRomeo();
        animatePointy();
        animateBear();

        masterTimer();

    }

    public void masterTimer() {
        if (currentBeat == StoryBeats.ROMEOIN && masterTimer < 0F) {
            currentBeat = StoryBeats.ROMEOSPEECH;
            action.shouldPlayTextEffect = true;
            action.textEffect = new SpeechTextEffect(this.romeo.skeleton.getX() + speechOffsetX + 170.0F * Settings.scale, this.romeo.skeleton.getY() + speechOffsetY + 124.0F * Settings.scale, 1.5F, IntroDialog, DialogWord.AppearEffect.BUMP_IN);
            action.shouldPlayEffect = true;
            action.currentEffect = new SpeechBubble(this.romeo.skeleton.getX() + speechOffsetX, this.romeo.skeleton.getY() + speechOffsetY, 1.5F, "", true);

            masterTimer = 1.5F;
        }

        if (currentBeat == StoryBeats.ROMEOSPEECH && masterTimer < 0F) {
            currentBeat = StoryBeats.POINTYIN;

            action.currentEffect = new SmokeBombEffect(this.pointyStartX, this.pointyStartY);
            action.shouldPlayEffect = true;

            pointy.skeleton.setX(this.pointyStartX);
            pointy.skeleton.setY(this.pointyStartY);
            masterTimer = 2.1F;
        }

        if (currentBeat == StoryBeats.POINTYIN && masterTimer < 0F) {
            currentBeat = StoryBeats.POINTYATTACK;


            pointy.state.setAnimation(0, "Attack", false);
            pointy.state.addAnimation(0, "Idle", true, 0.0F);
            masterTimer = .15F;
        }

        if (currentBeat == StoryBeats.POINTYATTACK && masterTimer < 0F) {
            currentBeat = StoryBeats.POINTYHIT;
            action.shouldDamage = true;
            action.currentDamage = pointyDamage;
            action.shouldPlayEffect = true;
            action.currentEffect = new FlashAtkImgEffect(action.m.drawX, action.m.drawY, AbstractGameAction.AttackEffect.SLASH_VERTICAL);

            masterTimer = .1F;
        }

        if (currentBeat == StoryBeats.POINTYHIT && masterTimer < 0F) {
            currentBeat = StoryBeats.POINTYHIT2;
            action.shouldDamage = true;
            action.currentDamage = pointyDamage;
            action.shouldPlayEffect = true;
            action.currentEffect = new FlashAtkImgEffect(action.m.drawX, action.m.drawY, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);

            masterTimer = .85F;
        }
        if (currentBeat == StoryBeats.POINTYHIT2 && masterTimer < 0F) {
            currentBeat = StoryBeats.ROMEOSPEECH2;
            action.currentEffect = new SpeechBubble(this.romeo.skeleton.getX() + speechOffsetX, this.romeo.skeleton.getY() + speechOffsetY, 1.5F, "", true);
            action.shouldPlayEffect = true;
            action.textEffect = new SpeechTextEffect(this.romeo.skeleton.getX() + speechOffsetX + 170.0F * Settings.scale, this.romeo.skeleton.getY() + speechOffsetY + 124.0F * Settings.scale, 1.5F, BearDialog, DialogWord.AppearEffect.BUMP_IN);
            action.shouldPlayTextEffect = true;

            animTypePointy = BanditAnimType.JUMPOUT;
            animPointyTimer = 1F;
            this.pointy.customFlipX = false;
            masterTimer = 1.7F;
        }

        if (currentBeat == StoryBeats.ROMEOSPEECH2 && masterTimer < 0F) {
            currentBeat = StoryBeats.BEARIN;
            animTypeBear = BanditAnimType.ENTER;
            bear.state.setAnimation(0, "Attack", false);
            bear.state.addAnimation(0, "Idle", true, 0.0F);
            masterTimer = .3F;
            animBearTimer = .3F;
        }

        if (currentBeat == StoryBeats.BEARIN && masterTimer < 0F) {
            this.animTypeBear = BanditAnimType.IDLE;
            action.shouldDamage = true;
            action.currentDamage = bearDamage;
            action.shouldPlayEffect = true;
            action.currentEffect = new FlashAtkImgEffect(action.m.drawX, action.m.drawY, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
            if (action.m instanceof AbstractCharBoss) {
                action.currentPower = new DexterityPower(action.m, this.bearDexDown);
            }
            action.screenshake = true;
            masterTimer = .6F;
            currentBeat = StoryBeats.BEARHIT;
            bear.customShadowScale = 1F;
        }
        if (currentBeat == StoryBeats.BEARHIT && masterTimer < 0F) {
            this.animTypeBear = BanditAnimType.JUMPIN;
            animBearTimer = .2F;
            masterTimer = .4F;
            currentBeat = StoryBeats.BEARGETUP;
        }

        if (currentBeat == StoryBeats.BEARGETUP && masterTimer < 0F) {
            currentBeat = StoryBeats.BEARJUMP;
            this.animTypeBear = BanditAnimType.JUMPOUT;
            animBearTimer = 1F;
            masterTimer = .5F;
        }

        if (currentBeat == StoryBeats.BEARJUMP && masterTimer < 0F) {
            currentBeat = StoryBeats.ROMEOJUMPIN;
            masterTimer = .4F;
            animTypeRomeo = BanditAnimType.JUMPIN;
            animRomeoTimer = .5F;
        }

        if (currentBeat == StoryBeats.ROMEOJUMPIN && masterTimer < 0F) {
            currentBeat = StoryBeats.ROMEOATTACK;
            romeo.state.setAnimation(0, "Attack", false);
            romeo.state.addAnimation(0, "Idle", true, 0.0F);
            masterTimer = .3F;
        }

        if (currentBeat == StoryBeats.ROMEOATTACK && masterTimer < 0F) {
            currentBeat = StoryBeats.ROMEOATTACKHIT;
            action.shouldDamage = true;
            action.currentDamage = romeoDamage;
            action.shouldPlayEffect = true;
            action.currentEffect = new FlashAtkImgEffect(action.m.drawX, action.m.drawY, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
            action.currentPower = new WeakPower(action.m, this.romeoWeak, false);
            masterTimer = 1F;
        }

        if (currentBeat == StoryBeats.ROMEOATTACKHIT && masterTimer < 0F) {
            currentBeat = StoryBeats.ROMEOSPEECH3;
            action.currentEffect = new SpeechBubble(this.romeo.skeleton.getX() + speechOffsetX, this.romeo.skeleton.getY() + speechOffsetY, 1.5F, "", true);
            action.textEffect = new SpeechTextEffect(this.romeo.skeleton.getX() + speechOffsetX + 170.0F * Settings.scale, this.romeo.skeleton.getY() + speechOffsetY + 124.0F * Settings.scale, 1.5F, OutroDialog, DialogWord.AppearEffect.BUMP_IN);
            action.shouldPlayTextEffect = true;
            action.shouldPlayEffect = true;
            masterTimer = 1.1F;
        }

        if (currentBeat == StoryBeats.ROMEOSPEECH3 && masterTimer < 0F) {
            currentBeat = StoryBeats.POINTOUT;
            this.animTypePointy = BanditAnimType.EXIT;
            this.pointy.customFlipX = false;
            this.animPointyTimer = 1F;
            masterTimer = .1F;
        }

        if (currentBeat == StoryBeats.POINTOUT && masterTimer < 0F) {
            currentBeat = StoryBeats.BEAROUT;

            this.animTypeBear = BanditAnimType.EXIT;
            this.bear.customFlipX = false;
            this.animBearTimer = 1F;
            masterTimer = .1F;
        }

        if (currentBeat == StoryBeats.BEAROUT && masterTimer < 0F) {
            currentBeat = StoryBeats.ROMEOOUT;
            this.animTypeRomeo = BanditAnimType.EXIT;
            this.animRomeoTimer = 1F;
            this.romeo.customFlipX = false;
            masterTimer = 1F;
        }

        if (currentBeat == StoryBeats.ROMEOOUT && masterTimer < 0F) {
            isDone = true;
        }


        masterTimer -= Gdx.graphics.getDeltaTime();

    }

    public void animateRomeo() {
        romeo.state.update(Gdx.graphics.getDeltaTime());
        romeo.state.apply(romeo.skeleton);
        if (animRomeoTimer > 0F) {
            if (this.animTypeRomeo == BanditAnimType.ENTER) {
                float Y = Interpolation.linear.apply(pointyJumpOutY + 90F * Settings.scale, AbstractDungeon.floorY - 60.0F * Settings.scale, animRomeoTimer / .5F);
                float X = Interpolation.linear.apply(800F * Settings.scale, -100F, animRomeoTimer / 1F);
                this.romeo.skeleton.setX(X);
                this.romeo.skeleton.setY(Y);
                animRomeoTimer -= Gdx.graphics.getDeltaTime();
                if (animRomeoTimer < 0F) {
                    animRomeoTimer = 1F;
                    this.animTypeRomeo = BanditAnimType.IDLE;
                }
            } else if (this.animTypeRomeo == BanditAnimType.JUMPIN) {
                float X = Interpolation.linear.apply(pointyStartX, 800F * Settings.scale, animRomeoTimer / .5F);
                float Y = Interpolation.linear.apply(pointyJumpOutY + 90F * Settings.scale, AbstractDungeon.floorY - 60.0F * Settings.scale, animRomeoTimer / .5F);

                this.romeo.skeleton.setX(X);
                this.romeo.skeleton.setY(Y);
                animRomeoTimer -= Gdx.graphics.getDeltaTime();
            } else if (this.animTypeRomeo == BanditAnimType.EXIT) {
                float X = Interpolation.linear.apply(-100F, pointyStartX, animRomeoTimer / 1F);

                this.romeo.skeleton.setX(X);
                animRomeoTimer -= Gdx.graphics.getDeltaTime();
            }
        }
    }

    public void animatePointy() {
        pointy.state.update(Gdx.graphics.getDeltaTime());
        pointy.state.apply(pointy.skeleton);
        if (animPointyTimer > 0F) {
            if (this.animTypePointy == BanditAnimType.JUMPOUT) {

                float X = Interpolation.linear.apply(this.pointyJumpOutX, this.pointyStartX + 50F * Settings.scale, animPointyTimer / 1F);
                float Y = Interpolation.linear.apply(this.pointyJumpOutY, this.pointyStartY - 50F * Settings.scale, animPointyTimer / 1F);
                this.pointy.skeleton.setX(X);
                this.pointy.skeleton.setY(Y);
                this.animPointyTimer -= Gdx.graphics.getDeltaTime();
                if (animPointyTimer < 0F) {
                    this.animTypePointy = BanditAnimType.IDLE;
                    this.pointy.customFlipX = true;
                }
            } else if (this.animTypePointy == BanditAnimType.EXIT) {
                float X = Interpolation.linear.apply(-100F, this.pointyJumpOutX, animPointyTimer / 1F);

                this.pointy.skeleton.setX(X);
                animPointyTimer -= Gdx.graphics.getDeltaTime();
            }
        }
    }

    public void animateBear() {
        if (animBearTimer > 0F) {
            if (this.animTypeBear == BanditAnimType.ENTER) {

                float X = Interpolation.linear.apply(this.bearLandX, this.bearStartX, animBearTimer / .3F);
                float Y = Interpolation.linear.apply(this.bearLandY, this.bearStartY, animBearTimer / .3F);
                this.bear.skeleton.setX(X);
                this.bear.skeleton.setY(Y);
                this.animBearTimer -= Gdx.graphics.getDeltaTime();

                float Rot = Interpolation.linear.apply(90F, 0F, animBearTimer / .3F);
                this.bear.customRot = Rot;
                if (animBearTimer < 0F){
                    this.bear.customRot = 90F;
                }
            } else if (this.animTypeBear == BanditAnimType.JUMPIN) {
                float Rot = Interpolation.linear.apply(0F, 90F, animBearTimer / .2F);
                this.bear.customRot = Rot;
                float X = Interpolation.linear.apply(this.bearLandX, bear.skeleton.getX(), animBearTimer / 1F);
                float Y = Interpolation.linear.apply(this.bearLandY, bear.skeleton.getY(), animBearTimer / 1F);

                this.bear.skeleton.setX(X);
                this.bear.skeleton.setY(Y);

                animBearTimer -= Gdx.graphics.getDeltaTime();
            }

            else if (this.animTypeBear == BanditAnimType.JUMPOUT) {

                float X = Interpolation.linear.apply(this.bearJumpOutX, this.bearLandX, animBearTimer / 1F);
                float Y = Interpolation.linear.apply(this.bearJumpOutY, this.bearLandY, animBearTimer / 1F);


                this.bear.skeleton.setX(X);
                this.bear.skeleton.setY(Y);


                animBearTimer -= Gdx.graphics.getDeltaTime();

                if (animBearTimer < 0F) {
                    this.animTypeBear = BanditAnimType.IDLE;
                    this.bear.customFlipX = true;
                }
            } else if (this.animTypeBear == BanditAnimType.EXIT) {
                float X = Interpolation.linear.apply(-100F, this.bearJumpOutX, animBearTimer / 1F);

                this.bear.skeleton.setX(X);
                animBearTimer -= Gdx.graphics.getDeltaTime();
            }
        }

        bear.state.update(Gdx.graphics.getDeltaTime());
        bear.state.apply(bear.skeleton);
    }

    public void render(SpriteBatch sb) {

        this.bear.render(sb);
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
        BEARGETUP,
        BEARJUMP,
        BEARHIT,
        ROMEOSPEECH3,
        ROMEOATTACK,
        ROMEOJUMPIN,
        ROMEOATTACKHIT,
        ROMEOOUT,
        BEAROUT,
        POINTOUT;

        StoryBeats() {
        }
    }
}